package org.cloudburstmc.server.entity.hostile;

import org.cloudburstmc.api.entity.EntityType;
import org.cloudburstmc.api.entity.Smiteable;
import org.cloudburstmc.api.entity.hostile.Stray;
import org.cloudburstmc.api.item.ItemStack;
import org.cloudburstmc.api.item.ItemTypes;
import org.cloudburstmc.api.level.Location;
import org.cloudburstmc.server.registry.CloudItemRegistry;

/**
 * @author PikyCZ
 */
public class EntityStray extends EntityHostile implements Stray, Smiteable {

    public EntityStray(EntityType<Stray> type, Location location) {
        super(type, location);
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        this.setMaxHealth(20);
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.99f;
    }

    @Override
    public String getName() {
        return "Stray";
    }

    @Override
    public ItemStack[] getDrops() {
        return new ItemStack[]{CloudItemRegistry.get().getItem(ItemTypes.BONE), CloudItemRegistry.get().getItem(ItemTypes.ARROW)};
    }

    @Override
    public boolean isUndead() {
        return true;
    }

}
