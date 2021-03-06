package org.cloudburstmc.server.blockentity;

import com.nukkitx.math.vector.Vector3i;
import org.cloudburstmc.api.block.BlockTypes;
import org.cloudburstmc.api.blockentity.BlockEntityType;
import org.cloudburstmc.api.blockentity.EnderChest;
import org.cloudburstmc.api.level.chunk.Chunk;

public class EnderChestBlockEntity extends ChestBlockEntity implements EnderChest {

    public EnderChestBlockEntity(BlockEntityType<?> type, Chunk chunk, Vector3i position) {
        super(type, chunk, position);
    }

    @Override
    public boolean isValid() {
        return this.getBlockState().getType() == BlockTypes.ENDER_CHEST;
    }

    @Override
    public boolean isSpawnable() {
        return true;
    }
}
