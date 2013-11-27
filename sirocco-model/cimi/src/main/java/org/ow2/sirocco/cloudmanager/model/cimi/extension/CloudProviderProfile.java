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
 *  $Id: CloudProvider.java 788 2011-12-21 11:49:55Z dangtran $
 *
 */
package org.ow2.sirocco.cloudmanager.model.cimi.extension;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;

@Entity
@NamedQueries({@NamedQuery(name = "CloudProviderProfile.findByUuid", query = "SELECT c from CloudProviderProfile c WHERE c.uuid=:uuid")})
public class CloudProviderProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String PROVIDER_ENDPOINT = "endpoint";

    public static final String PROVIDER_ACCOUNT_LOGIN = "login";

    public static final String PROVIDER_ACCOUNT_PASSWORD = "password";

    private Integer id;

    private String uuid;

    private String description = "";

    private String type;

    private String connectorClass;

    private List<AccountParameter> accountParameters;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getConnectorClass() {
        return this.connectorClass;
    }

    public void setConnectorClass(final String connectorClass) {
        this.connectorClass = connectorClass;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    public List<AccountParameter> getAccountParameters() {
        return this.accountParameters;
    }

    public void setAccountParameters(final List<AccountParameter> accountParameters) {
        this.accountParameters = accountParameters;
    }

    public AccountParameter findAccountParameter(final String name) {
        if (this.accountParameters != null) {
            for (AccountParameter param : this.accountParameters) {
                if (name.equals(param.getName()) || name.equals(param.getAlias())) {
                    return param;
                }
            }
        }
        return null;
    }

    @Embeddable
    public static class AccountParameter implements Serializable {
        private static final long serialVersionUID = 1L;

        private String name;

        private String alias = "";

        private String description = "";

        private String type = "String";

        private boolean required = true;

        public String getName() {
            return this.name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getAlias() {
            return this.alias;
        }

        public void setAlias(final String alias) {
            this.alias = alias;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(final String description) {
            this.description = description;
        }

        public String getType() {
            return this.type;
        }

        public void setType(final String type) {
            this.type = type;
        }

        public boolean isRequired() {
            return this.required;
        }

        public void setRequired(final boolean required) {
            this.required = required;
        }
    }

}
