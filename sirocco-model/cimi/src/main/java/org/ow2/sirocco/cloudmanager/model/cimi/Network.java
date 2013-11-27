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

package org.ow2.sirocco.cloudmanager.model.cimi;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.ow2.sirocco.cloudmanager.model.cimi.extension.CloudProviderAccount;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.CloudProviderLocation;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.ICloudProviderResource;

/**
 * L2 Network
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Network.findByProviderAssignedId", query = "SELECT n FROM Network n WHERE n.providerAssignedId=:providerAssignedId"),
    @NamedQuery(name = "Network.findByUuid", query = "SELECT n from Network n WHERE n.uuid=:uuid")})
public class Network extends CloudResource implements Serializable, ICloudProviderResource {
    private static final long serialVersionUID = 1L;

    public static enum State {
        CREATING, STARTING, STARTED, STOPPING, STOPPED, DELETING, DELETED, ERROR
    }

    public static enum Type {
        PRIVATE, PUBLIC
    }

    private CloudProviderAccount cloudProviderAccount;

    private CloudProviderLocation location;

    private State state;

    private Type networkType;

    private Integer mtu;

    private String classOfService;

    private List<NetworkNetworkPort> networkPorts;

    private ForwardingGroup forwardingGroup;

    private List<Subnet> subnets;

    @Enumerated(EnumType.STRING)
    public State getState() {
        return this.state;
    }

    public void setState(final State state) {
        this.state = state;
    }

    @ManyToOne
    public CloudProviderAccount getCloudProviderAccount() {
        return this.cloudProviderAccount;
    }

    public void setCloudProviderAccount(final CloudProviderAccount cloudProviderAccount) {
        this.cloudProviderAccount = cloudProviderAccount;
    }

    @ManyToOne
    public CloudProviderLocation getLocation() {
        return this.location;
    }

    public void setLocation(final CloudProviderLocation location) {
        this.location = location;
    }

    @Enumerated(EnumType.STRING)
    public Type getNetworkType() {
        return this.networkType;
    }

    public void setNetworkType(final Type networkType) {
        this.networkType = networkType;
    }

    public Integer getMtu() {
        return this.mtu;
    }

    public void setMtu(final Integer mtu) {
        this.mtu = mtu;
    }

    public String getClassOfService() {
        return this.classOfService;
    }

    public void setClassOfService(final String classOfService) {
        this.classOfService = classOfService;
    }

    @OneToMany(fetch = FetchType.EAGER)
    public List<NetworkNetworkPort> getNetworkPorts() {
        return this.networkPorts;
    }

    public void setNetworkPorts(final List<NetworkNetworkPort> networkPorts) {
        this.networkPorts = networkPorts;
    }

    @ManyToOne
    public ForwardingGroup getForwardingGroup() {
        return this.forwardingGroup;
    }

    public void setForwardingGroup(final ForwardingGroup forwardingGroup) {
        this.forwardingGroup = forwardingGroup;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    public List<Subnet> getSubnets() {
        return this.subnets;
    }

    public void setSubnets(final List<Subnet> subnets) {
        this.subnets = subnets;
    }

}
