package org.cloudburstmc.server.entity.projectile;

import org.cloudburstmc.api.entity.EntityType;
import org.cloudburstmc.api.entity.projectile.DragonFireball;
import org.cloudburstmc.api.level.Location;

public class EntityDragonFireball extends EntityProjectile implements DragonFireball {
    public EntityDragonFireball(EntityType<?> type, Location location) {
        super(type, location);
    }
}
