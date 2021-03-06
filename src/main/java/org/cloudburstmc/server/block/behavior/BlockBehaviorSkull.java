package org.cloudburstmc.server.block.behavior;

import com.nukkitx.math.vector.Vector3f;
import org.cloudburstmc.api.block.Block;
import org.cloudburstmc.api.block.BlockTraits;
import org.cloudburstmc.api.block.BlockTypes;
import org.cloudburstmc.api.blockentity.BlockEntityTypes;
import org.cloudburstmc.api.blockentity.Skull;
import org.cloudburstmc.api.item.ItemStack;
import org.cloudburstmc.api.item.ItemTypes;
import org.cloudburstmc.api.player.Player;
import org.cloudburstmc.api.util.Direction;
import org.cloudburstmc.api.util.data.BlockColor;
import org.cloudburstmc.server.blockentity.SkullBlockEntity;
import org.cloudburstmc.server.item.CloudItemStack;
import org.cloudburstmc.server.registry.BlockEntityRegistry;
import org.cloudburstmc.server.registry.CloudBlockRegistry;
import org.cloudburstmc.server.registry.CloudItemRegistry;

public class BlockBehaviorSkull extends BlockBehaviorTransparent {


    @Override
    public boolean place(ItemStack item, Block block, Block target, Direction face, Vector3f clickPos, Player player) {
        if (face == Direction.DOWN) {
            return false;
        }

        placeBlock(block, CloudBlockRegistry.get().getBlock(BlockTypes.SKULL).withTrait(BlockTraits.FACING_DIRECTION, face));

        SkullBlockEntity skull = (SkullBlockEntity) BlockEntityRegistry.get().newEntity(BlockEntityTypes.SKULL, block);
        skull.loadAdditionalData(((CloudItemStack) item).getDataTag());
//        skull.setSkullType(item.getMeta()); //TODO: skull type
        skull.setRotation((player.getYaw() * 16 / 360) + 0.5f);

        // TODO: 2016/2/3 SPAWN WITHER

        return true;
    }

    @Override
    public ItemStack toItem(Block block) {
        var be = block.getLevel().getBlockEntity(block.getPosition());

        int meta = 0;
        if (be instanceof Skull) {
            meta = ((Skull) be).getSkullType();
        }

        return CloudItemRegistry.get().getItem(ItemTypes.SKULL); //TODO: skull type
    }


    @Override
    public BlockColor getColor(Block block) {
        return BlockColor.AIR_BLOCK_COLOR;
    }


}
