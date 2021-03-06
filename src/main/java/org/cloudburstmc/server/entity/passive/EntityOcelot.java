package org.cloudburstmc.server.entity.passive;

import org.cloudburstmc.api.entity.EntityType;
import org.cloudburstmc.api.entity.passive.Ocelot;
import org.cloudburstmc.api.item.ItemStack;
import org.cloudburstmc.api.item.ItemTypes;
import org.cloudburstmc.api.level.Location;

/**
 * Author: BeYkeRYkt Nukkit Project
 */
public class EntityOcelot extends Animal implements Ocelot {

    public EntityOcelot(EntityType<Ocelot> type, Location location) {
        super(type, location);
    }

    @Override
    public float getWidth() {
        if (this.isBaby()) {
            return 0.3f;
        }
        return 0.6f;
    }

    @Override
    public float getHeight() {
        if (this.isBaby()) {
            return 0.35f;
        }
        return 0.7f;
    }

    @Override
    public String getName() {
        return "Ocelot";
    }

    @Override
    public void initEntity() {
        super.initEntity();
        setMaxHealth(10);
    }

    @Override
    public boolean isBreedingItem(ItemStack item) {
        return item.getType() == ItemTypes.FISH;
    }
}
