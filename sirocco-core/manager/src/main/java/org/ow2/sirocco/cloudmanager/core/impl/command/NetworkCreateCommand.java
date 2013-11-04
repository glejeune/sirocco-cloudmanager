/**
 *
 * SIROCCO
 * Copyright (C) 2013 Orange
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
 */
package org.ow2.sirocco.cloudmanager.core.impl.command;

import org.ow2.sirocco.cloudmanager.model.cimi.NetworkCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.CloudProviderAccount;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.CloudProviderLocation;

public class NetworkCreateCommand extends ResourceCommand {
    private static final long serialVersionUID = 1L;

    public static final String NETWORK_CREATE = "networkCreate";

    private final NetworkCreate NetworkCreate;

    private CloudProviderAccount account;

    private CloudProviderLocation location;

    public NetworkCreateCommand(final NetworkCreate volumeCreate) {
        super(NetworkCreateCommand.NETWORK_CREATE);
        this.NetworkCreate = volumeCreate;
    }

    public NetworkCreate getNetworkCreate() {
        return this.NetworkCreate;
    }

    public CloudProviderAccount getAccount() {
        return this.account;
    }

    public NetworkCreateCommand setAccount(final CloudProviderAccount account) {
        this.account = account;
        return this;
    }

    public CloudProviderLocation getLocation() {
        return this.location;
    }

    public NetworkCreateCommand setLocation(final CloudProviderLocation location) {
        this.location = location;
        return this;
    }

}
