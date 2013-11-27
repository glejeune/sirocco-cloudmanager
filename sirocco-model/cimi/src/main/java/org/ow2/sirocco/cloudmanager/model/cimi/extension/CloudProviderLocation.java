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
 *  $Id: CloudProviderLocation.java 897 2012-02-15 10:13:25Z ycas7461 $
 *
 */

package org.ow2.sirocco.cloudmanager.model.cimi.extension;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;

/**
 * Geographical location where cloud resources are running
 */
@Entity
@NamedQueries({@NamedQuery(name = "CloudProviderLocation.findByUuid", query = "SELECT c from CloudProviderLocation c WHERE c.uuid=:uuid")})
public class CloudProviderLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String uuid;

    private String providerAssignedId;

    /**
     * Iso3166-1 alpha-2 code (country code) -example: FR for France
     */
    private String iso3166_1;

    /**
     * Iso3166-2 code (subdivision code/province/departement/district, etc.) -
     * example:for France, FR-M (Lorraine), but we could also store FR-55
     * (Meuse) which is more precise
     */
    private String iso3166_2;

    /**
     * country name matching iso 3166-1
     */
    private String countryName;

    /**
     * state name matching iso 3166-2
     */
    private String stateName;

    /**
     * city name (for informational purposes only)
     */
    private String cityName;

    private Set<CloudProvider> cloudProviders;

    public CloudProviderLocation() {
    }

    public CloudProviderLocation(final String iso3166_1, final String iso3166_2, final String countryName,
        final String stateName) {
        this.iso3166_1 = iso3166_1;
        this.iso3166_2 = iso3166_2;
        this.countryName = countryName;
        this.stateName = stateName;
    }

    @Column(name = "iso3166_1")
    public String getIso3166_1() {
        return this.iso3166_1;
    }

    public void setIso3166_1(final String iso3166_1) {
        this.iso3166_1 = iso3166_1;
    }

    @Column(name = "iso3166_2")
    public String getIso3166_2() {
        return this.iso3166_2;
    }

    public void setIso3166_2(final String iso3166_2) {
        this.iso3166_2 = iso3166_2;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(final String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(final String countryName) {
        this.countryName = countryName;
    }

    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(final String stateName) {
        this.stateName = stateName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer Id) {
        this.id = Id;
    }

    public String getProviderAssignedId() {
        return this.providerAssignedId;
    }

    public void setProviderAssignedId(final String providerAssignedId) {
        this.providerAssignedId = providerAssignedId;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<CloudProvider> getCloudProviders() {
        return this.cloudProviders;
    }

    public void setCloudProviders(final Set<CloudProvider> cloudProviders) {
        this.cloudProviders = cloudProviders;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.iso3166_1 == null) ? 0 : this.iso3166_1.hashCode());
        result = prime * result + ((this.iso3166_2 == null) ? 0 : this.iso3166_2.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        CloudProviderLocation other = (CloudProviderLocation) obj;
        if (this.iso3166_1 == null) {
            if (other.iso3166_1 != null) {
                return false;
            }
        } else if (!this.iso3166_1.equals(other.iso3166_1)) {
            return false;
        }
        if (this.iso3166_2 == null) {
            if (other.iso3166_2 != null) {
                return false;
            }
        } else if (!this.iso3166_2.equals(other.iso3166_2)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CloudProviderLocation [" + (this.iso3166_1 != null ? "Iso3166_1=" + this.iso3166_1 + ", " : "")
            + (this.iso3166_2 != null ? "Iso3166_2=" + this.iso3166_2 + ", " : "")
            + (this.countryName != null ? "countryName=" + this.countryName + ", " : "")
            + (this.stateName != null ? "stateName=" + this.stateName : "") + "]";
    }

}
