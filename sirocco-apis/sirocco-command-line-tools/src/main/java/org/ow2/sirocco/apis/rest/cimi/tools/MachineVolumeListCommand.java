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
package org.ow2.sirocco.apis.rest.cimi.tools;

import java.util.List;

import org.nocrala.tools.texttablefmt.Table;
import org.ow2.sirocco.apis.rest.cimi.sdk.CimiClient;
import org.ow2.sirocco.apis.rest.cimi.sdk.CimiException;
import org.ow2.sirocco.apis.rest.cimi.sdk.MachineVolume;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "list volumes attached to machine")
public class MachineVolumeListCommand implements Command {
    public static String COMMAND_NAME = "machinevolume-list";

    @Parameter(names = "-machine", description = "id of the machine", required = true)
    private String machineId;

    @Parameter(names = "-first", description = "First index of entity to return")
    private Integer first = -1;

    @Parameter(names = "-last", description = "Last index of entity to return")
    private Integer last = -1;

    @Parameter(names = "-filter", description = "Filter expression")
    private String filter;

    @Override
    public String getName() {
        return MachineVolumeListCommand.COMMAND_NAME;
    }

    @Override
    public void execute(final CimiClient cimiClient) throws CimiException {
        List<MachineVolume> machineVolumes = MachineVolume.getMachineVolumes(cimiClient, this.machineId,
            CommandHelper.buildQueryParams(this.first, this.last, this.filter, null));

        Table table = new Table(5);
        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Description");
        table.addCell("Initial location");
        table.addCell("Volume");

        for (MachineVolume machineVolume : machineVolumes) {
            table.addCell(machineVolume.getId());
            table.addCell(machineVolume.getName());
            table.addCell(machineVolume.getDescription());
            table.addCell(machineVolume.getInitialLocation());
            table.addCell(machineVolume.getVolume().getId());
        }
        System.out.println(table.render());
    }
}
