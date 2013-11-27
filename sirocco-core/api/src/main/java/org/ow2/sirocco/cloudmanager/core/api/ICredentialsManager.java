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

package org.ow2.sirocco.cloudmanager.core.api;

import java.util.List;
import java.util.Map;

import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.InvalidRequestException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.Credentials;
import org.ow2.sirocco.cloudmanager.model.cimi.CredentialsCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.CredentialsTemplate;

/**
 * Credentials management operations
 */
public interface ICredentialsManager {

    static final String EJB_JNDI_NAME = "java:global/sirocco/sirocco-core/CredentialsManager!org.ow2.sirocco.cloudmanager.core.api.IRemoteCredentialsManager";

    Credentials createCredentials(CredentialsCreate credentialsCreate) throws CloudProviderException;

    void updateCredentials(Credentials credentials) throws CloudProviderException;

    Credentials getCredentialsById(int credentialsId) throws CloudProviderException;

    Credentials getCredentialsByUuid(String credentialsUuid) throws CloudProviderException;

    Credentials getCredentialsAttributes(final String credentialsId, List<String> attributes) throws ResourceNotFoundException,
        CloudProviderException;

    void deleteCredentials(String credentialsId) throws ResourceNotFoundException, InvalidRequestException,
        CloudProviderException;

    void updateCredentialsAttributes(String credentialsId, Map<String, Object> attributes) throws ResourceNotFoundException,
        InvalidRequestException, CloudProviderException;

    QueryResult<Credentials> getCredentials(int first, int last, List<String> filters, List<String> attributes)
        throws InvalidRequestException, CloudProviderException;

    CredentialsTemplate createCredentialsTemplate(CredentialsTemplate credentialsTemplate) throws CloudProviderException;

    void updateCredentialsTemplate(CredentialsTemplate credentialsTemplate) throws CloudProviderException;

    CredentialsTemplate getCredentialsTemplateById(int credentialsTemplateId) throws CloudProviderException;

    CredentialsTemplate getCredentialsTemplateByUuid(String credentialsTemplateUuid) throws CloudProviderException;

    CredentialsTemplate getCredentialsTemplateAttributes(final String credentialsTemplateId, List<String> attributes)
        throws ResourceNotFoundException, CloudProviderException;

    void deleteCredentialsTemplate(String credentialsTemplateId) throws ResourceNotFoundException, InvalidRequestException,
        CloudProviderException;

    void updateCredentialsTemplateAttributes(String credentialsTemplateId, Map<String, Object> attributes)
        throws ResourceNotFoundException, InvalidRequestException, CloudProviderException;

    QueryResult<CredentialsTemplate> getCredentialsTemplates(int first, int last, List<String> filters, List<String> attributes)
        throws InvalidRequestException, CloudProviderException;

    List<Credentials> getCredentials() throws CloudProviderException;

    List<CredentialsTemplate> getCredentialsTemplates() throws CloudProviderException;
}
