package org.cloudburstmc.server.event.entity;

import org.cloudburstmc.api.event.Cancellable;
import org.cloudburstmc.api.event.entity.EntityEvent;
import org.cloudburstmc.server.entity.misc.DroppedItem;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class ItemSpawnEvent extends EntityEvent implements Cancellable {

    public ItemSpawnEvent(DroppedItem item) {
        this.entity = item;
    }

    @Override
    public DroppedItem getEntity() {
        return (DroppedItem) this.entity;
    }
}
