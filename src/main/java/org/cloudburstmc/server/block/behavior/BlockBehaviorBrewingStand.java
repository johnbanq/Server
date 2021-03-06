package org.cloudburstmc.server.block.behavior;


import com.nukkitx.math.vector.Vector3f;
import org.cloudburstmc.api.block.Block;
import org.cloudburstmc.api.block.BlockState;
import org.cloudburstmc.api.block.BlockTypes;
import org.cloudburstmc.api.blockentity.BlockEntity;
import org.cloudburstmc.api.blockentity.BrewingStand;
import org.cloudburstmc.api.item.ItemStack;
import org.cloudburstmc.api.item.ItemTypes;
import org.cloudburstmc.api.item.TierTypes;
import org.cloudburstmc.api.player.Player;
import org.cloudburstmc.api.util.Direction;
import org.cloudburstmc.api.util.data.BlockColor;
import org.cloudburstmc.server.blockentity.BrewingStandBlockEntity;
import org.cloudburstmc.server.inventory.CloudContainer;
import org.cloudburstmc.server.item.CloudItemStack;
import org.cloudburstmc.server.level.chunk.CloudChunk;
import org.cloudburstmc.server.registry.BlockEntityRegistry;
import org.cloudburstmc.server.registry.CloudBlockRegistry;
import org.cloudburstmc.server.registry.CloudItemRegistry;

import static org.cloudburstmc.api.blockentity.BlockEntityTypes.BREWING_STAND;

public class BlockBehaviorBrewingStand extends BlockBehaviorSolid {

    @Override
    public boolean canBeActivated(Block block) {
        return true;
    }

    @Override
    public boolean place(ItemStack item, Block block, Block target, Direction face, Vector3f clickPos, Player player) {
        BlockState state = target.getState();

        if (!state.getBehavior().isTransparent(state)) {
            placeBlock(block, CloudBlockRegistry.get().getBlock(BlockTypes.BREWING_STAND));

            BrewingStandBlockEntity brewingStand = (BrewingStandBlockEntity) BlockEntityRegistry.get().newEntity(BREWING_STAND, (CloudChunk) block.getChunk(), block.getPosition());
            brewingStand.loadAdditionalData(((CloudItemStack) item).getDataTag());
            if (item.hasName()) {
                brewingStand.setCustomName(item.getName());
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean onActivate(Block block, ItemStack item, Player player) {
        if (player != null) {
            BlockEntity blockEntity = block.getLevel().getBlockEntity(block.getPosition());
            BrewingStand brewing;
            if (blockEntity instanceof BrewingStand) {
                brewing = (BrewingStand) blockEntity;
            } else {
                if (blockEntity != null) {
                    blockEntity.close();
                }

                brewing = BlockEntityRegistry.get().newEntity(BREWING_STAND, (CloudChunk) block.getChunk(), block.getPosition());
            }

            player.addWindow(brewing.getInventory());
        }

        return true;
    }

    @Override
    public ItemStack toItem(Block block) {
        return CloudItemRegistry.get().getItem(ItemTypes.BREWING_STAND);
    }

    @Override
    public ItemStack[] getDrops(Block block, ItemStack hand) {
        if (hand.getBehavior().isPickaxe() && hand.getBehavior().getTier(hand).compareTo(TierTypes.WOOD) >= 0) {
            return new ItemStack[]{
                    toItem(block)
            };
        } else {
            return new ItemStack[0];
        }
    }

    @Override
    public BlockColor getColor(Block block) {
        return BlockColor.IRON_BLOCK_COLOR;
    }


    @Override
    public int getComparatorInputOverride(Block block) {
        BlockEntity blockEntity = block.getLevel().getBlockEntity(block.getPosition());

        if (blockEntity instanceof BrewingStand) {
            return CloudContainer.calculateRedstone(((BrewingStand) blockEntity).getInventory());
        }

        return super.getComparatorInputOverride(block);
    }


}
