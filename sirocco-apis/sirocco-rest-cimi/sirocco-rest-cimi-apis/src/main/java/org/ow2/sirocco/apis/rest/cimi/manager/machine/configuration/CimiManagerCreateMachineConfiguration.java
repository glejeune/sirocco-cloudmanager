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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * $Id$
 *
 */
package org.ow2.sirocco.apis.rest.cimi.manager.machine.configuration;

import javax.ws.rs.core.Response;

import org.ow2.sirocco.apis.rest.cimi.domain.CimiEntityType;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineConfiguration;
import org.ow2.sirocco.apis.rest.cimi.manager.CimiManagerCreateAbstract;
import org.ow2.sirocco.apis.rest.cimi.manager.MergeReferenceHelper;
import org.ow2.sirocco.apis.rest.cimi.request.CimiRequest;
import org.ow2.sirocco.apis.rest.cimi.request.CimiResponse;
import org.ow2.sirocco.apis.rest.cimi.utils.Constants;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Manage CREATE request of Machine Configuration.
 */
@Component("CimiManagerCreateMachineConfiguration")
public class CimiManagerCreateMachineConfiguration extends CimiManagerCreateAbstract {

    @Autowired
    @Qualifier("IMachineManager")
    private IMachineManager manager;

    @Autowired
    @Qualifier("MergeReferenceHelper")
    private MergeReferenceHelper mergeReference;

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.apis.rest.cimi.manager.CimiManagerAbstract#callService(org.ow2.sirocco.apis.rest.cimi.request.CimiRequest,
     *      org.ow2.sirocco.apis.rest.cimi.request.CimiResponse,
     *      java.lang.Object)
     */
    @Override
    protected Object callService(final CimiRequest request, final CimiResponse response, final Object dataService)
        throws Exception {
        return this.manager.createMachineConfiguration((MachineConfiguration) dataService);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.apis.rest.cimi.manager.CimiManagerAbstract#convertToDataService(org.ow2.sirocco.apis.rest.cimi.request.CimiRequest,
     *      org.ow2.sirocco.apis.rest.cimi.request.CimiResponse)
     */
    @Override
    protected Object convertToDataService(final CimiRequest request, final CimiResponse response) throws Exception {
        return request.getContext().getRootConverter(CimiEntityType.MachineConfiguration)
            .toService(request.getContext(), request.getCimiData());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.apis.rest.cimi.manager.CimiManagerAbstract#convertToResponse(org.ow2.sirocco.apis.rest.cimi.request.CimiRequest,
     *      org.ow2.sirocco.apis.rest.cimi.request.CimiResponse,
     *      java.lang.Object)
     */
    @Override
    protected void convertToResponse(final CimiRequest request, final CimiResponse response, final Object dataService)
        throws Exception {
        CimiMachineConfiguration cimi = (CimiMachineConfiguration) request.getContext()
            .getRootConverter(CimiEntityType.MachineConfiguration).toCimi(request.getContext(), dataService);
        response.setCimiData(cimi);
        response.putHeader(Constants.HEADER_LOCATION, cimi.getId());
        response.setStatus(Response.Status.CREATED);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.apis.rest.cimi.manager.CimiManagerAbstract#beforeConvertToDataService(org.ow2.sirocco.apis.rest.cimi.request.CimiRequest,
     *      org.ow2.sirocco.apis.rest.cimi.request.CimiResponse)
     */
    @Override
    protected void beforeConvertToDataService(final CimiRequest request, final CimiResponse response) throws Exception {
        this.mergeReference.merge(request.getContext(), (CimiMachineConfiguration) request.getCimiData());
    }
}
