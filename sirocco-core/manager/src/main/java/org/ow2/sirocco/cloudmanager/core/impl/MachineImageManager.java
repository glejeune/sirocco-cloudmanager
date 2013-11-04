/**
 *
 * SIROCCO
 * Copyright (C) 2011 France Telecom
 * Contact: sirocco@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 *  $Id$
 *
 */

package org.ow2.sirocco.cloudmanager.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.ow2.sirocco.cloudmanager.core.api.ICloudProviderManager;
import org.ow2.sirocco.cloudmanager.core.api.IJobManager;
import org.ow2.sirocco.cloudmanager.core.api.IMachineImageManager;
import org.ow2.sirocco.cloudmanager.core.api.ITenantManager;
import org.ow2.sirocco.cloudmanager.core.api.IdentityContext;
import org.ow2.sirocco.cloudmanager.core.api.QueryResult;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.InvalidRequestException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.core.api.remote.IRemoteMachineImageManager;
import org.ow2.sirocco.cloudmanager.core.utils.QueryHelper;
import org.ow2.sirocco.cloudmanager.core.utils.UtilsForManagers;
import org.ow2.sirocco.cloudmanager.model.cimi.CloudResource;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage.State;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineTemplate;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.CloudProviderAccount;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.ProviderMapping;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Remote(IRemoteMachineImageManager.class)
@Local(IMachineImageManager.class)
@SuppressWarnings("unused")
public class MachineImageManager implements IMachineImageManager {

    private static Logger logger = LoggerFactory.getLogger(MachineImageManager.class.getName());

    @PersistenceContext(unitName = "siroccoPersistenceUnit", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    @EJB
    private ITenantManager tenantManager;

    @EJB
    private IJobManager jobManager;

    @EJB
    private ICloudProviderManager cloudProviderManager;

    @Inject
    private IdentityContext identityContext;

    private Tenant getTenant() throws CloudProviderException {
        return this.tenantManager.getTenant(this.identityContext);
    }

    public Job createMachineImage(final MachineImage mi) throws CloudProviderException {
        // TODO : check whether imageLocation points to a Machine
        mi.setType(MachineImage.Type.IMAGE);

        mi.setTenant(this.getTenant());
        mi.setCreated(new Date());
        mi.setState(MachineImage.State.AVAILABLE);

        if (mi.getProperties() != null) {
            String providerAccountId = mi.getProperties().get("providerAccountId");
            String providerAssignedId = mi.getProperties().get("providerAssignedId");
            if (providerAccountId != null && providerAssignedId != null) {
                CloudProviderAccount account = this.cloudProviderManager.getCloudProviderAccountById(providerAccountId);
                if (account == null) {
                    throw new CloudProviderException("Invalid provider account id: " + providerAccountId);
                }
                ProviderMapping providerMapping = new ProviderMapping();
                providerMapping.setProviderAssignedId(providerAssignedId);
                providerMapping.setProviderAccount(account);
                providerMapping.setProviderLocation(account.getCloudProvider().getCloudProviderLocations().iterator().next());
                mi.setProviderMappings(Collections.singletonList(providerMapping));
            }
        }

        this.em.persist(mi);
        this.em.flush();

        Job j = new Job();
        j.setCreated(new Date());
        j.setDescription("MachineImage creation");
        j.setTargetResource(mi);
        List<CloudResource> affectedResources = new ArrayList<CloudResource>();
        affectedResources.add(mi);
        j.setAffectedResources(affectedResources);
        j.setState(Job.Status.SUCCESS);
        j.setAction("add");
        j.setParentJob(null);
        j.setNestedJobs(null);
        j.setReturnCode(0);
        j.setTenant(this.getTenant());
        this.em.persist(j);
        this.em.flush();
        return j;
    }

    @Override
    public List<MachineImage> getMachineImages() throws CloudProviderException {
        return QueryHelper.getEntityList("MachineImage", this.em, this.getTenant().getId(), MachineImage.State.DELETED, true);
    }

    public MachineImage getMachineImageById(final String imageId) throws CloudProviderException {
        MachineImage image = null;
        image = this.em.find(MachineImage.class, Integer.valueOf(new String(imageId)));
        if (image == null || image.getState() == State.DELETED) {
            throw new ResourceNotFoundException();
        }
        return image;
    }

    @Override
    public MachineImage getMachineImageAttributes(final String imageId, final List<String> attributes)
        throws ResourceNotFoundException, CloudProviderException {
        MachineImage machineImage = this.getMachineImageById(imageId);
        return UtilsForManagers.fillResourceAttributes(machineImage, attributes);
    }

    public void deleteMachineImage(final String imageId) throws CloudProviderException, ResourceNotFoundException {
        MachineImage image = null;
        try {
            image = this.em.find(MachineImage.class, Integer.valueOf(imageId));

        } catch (Exception e) {
            throw new CloudProviderException("MachineImage of identity " + imageId + " cannot be found ");
        }

        /** if related image is not null then do not permit deletion */
        if (image.getRelatedImage() != null) {
            throw new CloudProviderException("Related images exist for this image" + imageId);
        }
        /** if a machine template referernces the image do not permit deletion */
        List<MachineTemplate> templates = null;
        try {
            templates = this.em.createQuery("SELECT  t FROM MachineTemplate t WHERE t.machineImage.id=:mid")
                .setParameter("mid", Integer.valueOf(imageId)).getResultList();
        } catch (Exception e) {
            throw new CloudProviderException("Internal query error" + e.getMessage());
        }
        if (templates != null && !templates.isEmpty()) {
            throw new CloudProviderException("Machine templates refer to this image " + imageId);
        }

        image.setState(MachineImage.State.DELETED);
        this.em.flush();

    }

    @Override
    public QueryResult<MachineImage> getMachineImages(final int first, final int last, final List<String> filters,
        final List<String> attributes) throws InvalidRequestException, CloudProviderException {
        QueryHelper.QueryParamsBuilder params = QueryHelper.QueryParamsBuilder.builder("MachineImage", MachineImage.class);
        return QueryHelper.getEntityList(this.em,
            params.tenantId(this.getTenant().getId()).first(first).last(last).filter(filters).attributes(attributes)
                .stateToIgnore(MachineImage.State.DELETED).returnPublicEntities());
    }

    @Override
    public void updateMachineImage(final MachineImage machineImage) throws ResourceNotFoundException, InvalidRequestException,
        CloudProviderException {
        // TODO Auto-generated method stub

    }

    public void updateMachineImageAttributes(final String imageId, final Map<String, Object> attributes)
        throws ResourceNotFoundException, InvalidRequestException, CloudProviderException {

        MachineImage image = this.em.find(MachineImage.class, Integer.valueOf(imageId));
        if (image == null) {
            throw new ResourceNotFoundException("MachineImage of identity " + imageId + " cannot be found ");
        }
        if (attributes.containsKey("name")) {
            image.setName((String) attributes.get("name"));
        }
        if (attributes.containsKey("description")) {
            image.setDescription((String) attributes.get("description"));
        }
        if (attributes.containsKey("properties")) {
            image.setProperties((Map<String, String>) attributes.get("properties"));
        }
        if (attributes.containsKey("imageLocation")) {
            image.setImageLocation((String) attributes.get("imageLocation"));
        }
        image.setUpdated(new Date());
        this.em.merge(image);
        this.em.flush();
    }

}
