package org.cloudburstmc.server.blockentity;

import com.nukkitx.math.vector.Vector3i;
import org.cloudburstmc.api.block.BlockTraits;
import org.cloudburstmc.api.block.BlockTypes;
import org.cloudburstmc.api.blockentity.BlastFurnace;
import org.cloudburstmc.api.blockentity.BlockEntityType;
import org.cloudburstmc.api.inventory.InventoryType;
import org.cloudburstmc.api.level.chunk.Chunk;

public class BlastFurnaceBlockEntity extends FurnaceBlockEntity implements BlastFurnace {

    public BlastFurnaceBlockEntity(BlockEntityType<?> type, Chunk chunk, Vector3i position) {
        super(type, chunk, position, InventoryType.BLAST_FURNACE);
    }

    @Override
    public boolean isValid() {
        return getBlockState().getType() == BlockTypes.BLAST_FURNACE;
    }

    @Override
    public float getBurnRate() {
        return 2.0f;
    }

    @Override
    protected void extinguishFurnace() {
        this.getLevel().setBlockState(this.getPosition(), getBlockState().withTrait(BlockTraits.IS_EXTINGUISHED, true), true);
    }

    @Override
    protected void lightFurnace() {
        this.getLevel().setBlockState(this.getPosition(), getBlockState().withTrait(BlockTraits.IS_EXTINGUISHED, false), true);
    }
}
