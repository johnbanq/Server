package org.cloudburstmc.server.block.behavior;

import org.cloudburstmc.api.block.Block;
import org.cloudburstmc.api.block.BlockTraits;
import org.cloudburstmc.api.item.ItemStack;
import org.cloudburstmc.api.item.ItemTypes;
import org.cloudburstmc.server.registry.CloudItemRegistry;

public class BlockBehaviorWheat extends BlockBehaviorCrops {

    @Override
    public ItemStack toItem(Block block) {
        return CloudItemRegistry.get().getItem(ItemTypes.WHEAT);
    }

    @Override
    public ItemStack[] getDrops(Block block, ItemStack hand) {
        if (block.getState().ensureTrait(BlockTraits.GROWTH) >= 0x07) {
            return new ItemStack[]{
                    CloudItemRegistry.get().getItem(ItemTypes.WHEAT),
                    CloudItemRegistry.get().getItem(ItemTypes.WHEAT, (int) (4f * Math.random()))
            };
        } else {
            return new ItemStack[]{
                    CloudItemRegistry.get().getItem(ItemTypes.WHEAT)
            };
        }
    }
}
