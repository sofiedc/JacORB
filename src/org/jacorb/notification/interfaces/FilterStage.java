package org.jacorb.notification.interfaces;

/*
 *        JacORB - a free Java ORB
 *
 *   Copyright (C) 1999-2003 Gerald Brose
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Library General Public
 *   License as published by the Free Software Foundation; either
 *   version 2 of the License, or (at your option) any later version.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *   Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this library; if not, write to the Free
 *   Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */

import java.util.List;

import org.omg.CosNotifyFilter.MappingFilter;

/**
 * Abstraction of a ProxyConsumer, SupplierAdmin, ConsumerAdmin,
 * ProxySupplier. This Interface provides uniform access to use
 * these Classes during processing of an event.
 *
 * @author Alphonse Bendt
 * @version $Id$
 */

public interface FilterStage
{

    /**
     * check if this FilterStage has been disposed.
     */
    boolean isDisposed();

    /**
     * get FilterStages following this Node.
     */
    List getSubsequentFilterStages();

    /**
     * get Filter associated to this FilterStage.
     */
    List getFilters();

    /**
     * check if this FilterStage has a EventConsumer associcated.
     */
    boolean hasEventConsumer();

    /**
     * check if this DistributorNode has OR Semantic enabled.
     */
    boolean hasOrSemantic();

    /**
     * get the associated DeliverTarget or null.
     */
    EventConsumer getEventConsumer();

    /**
     * check if this FilterStage has a LifetimeFilter attached
     */
    boolean hasLifetimeFilter();

    /**
     * check if this FilterStage has a PriorityFilter attached
     */
    boolean hasPriorityFilter();

    /**
     * access the LifetimeFilter attached to this FilterStage
     * @return a LifetimeFilter or null if no Filter is attached
     */
    MappingFilter getLifetimeFilter();

    /**
     * access the PriorityFilter attached to this FilterStage
     * @return a PriorityFilter or null if no Filter is attached
     */
    MappingFilter getPriorityFilter();

}
