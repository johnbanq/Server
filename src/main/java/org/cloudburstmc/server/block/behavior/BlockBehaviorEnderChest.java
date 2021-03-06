package org.cloudburstmc.server.block.behavior;

import com.nukkitx.math.vector.Vector3f;
import org.cloudburstmc.api.block.Block;
import org.cloudburstmc.api.block.BlockCategory;
import org.cloudburstmc.api.block.BlockState;
import org.cloudburstmc.api.block.BlockTraits;
import org.cloudburstmc.api.blockentity.BlockEntity;
import org.cloudburstmc.api.blockentity.BlockEntityTypes;
import org.cloudburstmc.api.blockentity.EnderChest;
import org.cloudburstmc.api.item.ItemStack;
import org.cloudburstmc.api.player.Player;
import org.cloudburstmc.api.util.Direction;
import org.cloudburstmc.api.util.data.BlockColor;
import org.cloudburstmc.server.blockentity.EnderChestBlockEntity;
import org.cloudburstmc.server.item.CloudItemStack;
import org.cloudburstmc.server.level.chunk.CloudChunk;
import org.cloudburstmc.server.player.CloudPlayer;
import org.cloudburstmc.server.registry.BlockEntityRegistry;
import org.cloudburstmc.server.registry.CloudItemRegistry;

import static org.cloudburstmc.api.block.BlockTypes.OBSIDIAN;

public class BlockBehaviorEnderChest extends BlockBehaviorTransparent {

    @Override
    public boolean canBeActivated(Block block) {
        return true;
    }


//    @Override
//    public float getMinX() {
//        return this.getX() + 0.0625f;
//    }
//
//    @Override
//    public float getMinZ() {
//        return this.getZ() + 0.0625f;
//    }
//
//    @Override
//    public float getMaxX() {
//        return this.getX() + 0.9375f;
//    }
//
//    @Override
//    public float getMaxY() {
//        return this.getY() + 0.9475f;
//    }
//
//    @Override
//    public float getMaxZ() {
//        return this.getZ() + 0.9375f;
//    }

    @Override
    public boolean place(ItemStack item, Block block, Block target, Direction face, Vector3f clickPos, Player player) {
        int[] faces = {2, 5, 3, 4};

        placeBlock(block, item.getBehavior().getBlock(item).withTrait(BlockTraits.FACING_DIRECTION, player != null ? player.getHorizontalDirection() : Direction.NORTH));

        EnderChestBlockEntity enderChest = (EnderChestBlockEntity) BlockEntityRegistry.get().newEntity(BlockEntityTypes.ENDER_CHEST, block);
        enderChest.loadAdditionalData(((CloudItemStack) item).getDataTag());
        if (item.hasName()) {
            enderChest.setCustomName(item.getName());
        }
        return true;
    }

    @Override
    public boolean onActivate(Block block, ItemStack item, Player player) {
        if (player != null) {
            BlockState top = block.up().getState();
            if (!top.inCategory(BlockCategory.TRANSPARENT)) {
                return true;
            }

            BlockEntity blockEntity = block.getLevel().getBlockEntity(block.getPosition());
            if (!(blockEntity instanceof EnderChest)) {
                blockEntity = BlockEntityRegistry.get().newEntity(BlockEntityTypes.ENDER_CHEST, (CloudChunk) block.getChunk(), block.getPosition());
            }

            ((CloudPlayer) player).setViewingEnderChest((EnderChest) blockEntity);
            player.addWindow(((CloudPlayer) player).getEnderChestInventory());
        }

        return true;
    }

    @Override
    public ItemStack[] getDrops(Block block, ItemStack hand) {
        if (checkTool(block.getState(), hand)) {
            return new ItemStack[]{
                    CloudItemRegistry.get().getItem(OBSIDIAN, 8)
            };
        } else {
            return new ItemStack[0];
        }
    }

    @Override
    public BlockColor getColor(Block block) {
        return BlockColor.OBSIDIAN_BLOCK_COLOR;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }


    @Override
    public ItemStack toItem(Block block) {
        return CloudItemRegistry.get().getItem(block.getState().getType().getDefaultState());
    }


}
