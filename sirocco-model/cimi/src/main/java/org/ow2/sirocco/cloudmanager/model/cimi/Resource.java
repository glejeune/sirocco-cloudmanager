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

import java.util.Date;
import java.util.Map;

/**
 * Represents a CIMI resource
 */
public interface Resource extends Identifiable {

    void setId(Integer id);

    Integer getId();

    String getDescription();

    void setDescription(final String description);

    Date getCreated();

    String getName();

    void setName(final String name);

    void setCreated(final Date created);

    void setProperties(final Map<String, String> properties);

    Map<String, String> getProperties();

    void setDeleted(final Date deleted);

    Date getDeleted();

    void setUpdated(final Date updated);

    Date getUpdated();

}
