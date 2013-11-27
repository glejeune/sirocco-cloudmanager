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
 *  $Id: JobManager.java 913 2012-02-20 09:34:20Z dangtran $
 *
 */
package org.ow2.sirocco.cloudmanager.core.api;

import java.util.List;

import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.InvalidRequestException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.CloudResource;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;

/**
 * Job management operations
 */
public interface IJobManager {

    static final String EJB_JNDI_NAME = "java:global/sirocco/sirocco-core/JobManager!org.ow2.sirocco.cloudmanager.core.api.IRemoteJobManager";

    Job createJob(CloudResource targetEntity, String action, Integer parentJob) throws CloudProviderException;

    Job getJobById(int id) throws ResourceNotFoundException, CloudProviderException;

    Job getJobByUuid(String uuid) throws ResourceNotFoundException, CloudProviderException;

    QueryResult<Job> getJobs(QueryParams... queryParams) throws CloudProviderException;

    Job getJobAttributes(final String id, List<String> attributes) throws ResourceNotFoundException, CloudProviderException;

    QueryResult<Job> getJobs(int first, int last, List<String> filters, List<String> attributes)
        throws InvalidRequestException, CloudProviderException;

    void deleteJob(String jobId) throws CloudProviderException;

}
