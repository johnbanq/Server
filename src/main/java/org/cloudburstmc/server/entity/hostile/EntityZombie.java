package org.cloudburstmc.server.entity.hostile;

import org.cloudburstmc.api.entity.EntityType;
import org.cloudburstmc.api.entity.Smiteable;
import org.cloudburstmc.api.entity.hostile.Zombie;
import org.cloudburstmc.api.level.Location;

/**
 * Created by Dr. Nick Doran on 4/23/2017.
 */
public class EntityZombie extends EntityHostile implements Zombie, Smiteable {

    public EntityZombie(EntityType<Zombie> type, Location location) {
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
        return 1.95f;
    }

    @Override
    public String getName() {
        return "Zombie";
    }

    @Override
    public boolean isUndead() {
        return true;
    }

}
