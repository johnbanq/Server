package org.cloudburstmc.server.block.behavior;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.api.block.Block;
import org.cloudburstmc.api.block.BlockTraits;
import org.cloudburstmc.api.item.ItemStack;
import org.cloudburstmc.api.player.Player;
import org.cloudburstmc.api.util.data.BlockColor;
import org.cloudburstmc.server.level.CloudLevel;
import org.cloudburstmc.server.registry.CloudItemRegistry;

import static org.cloudburstmc.api.item.ItemTypes.ENDER_EYE;

public class BlockBehaviorEndPortalFrame extends BlockBehaviorTransparent {

//    @Override
//    public float getMaxY() {
//        return this.getY() + ((this.getMeta() & 0x04) > 0 ? 1 : 0.8125f);
//    }

    @Override
    public boolean canBePushed() {
        return false;
    }



    public int getComparatorInputOverride(Block block) {
        return block.getState().ensureTrait(BlockTraits.HAS_END_PORTAL_EYE) ? 15 : 0;
    }

    @Override
    public boolean canBeActivated(Block block) {
        return true;
    }

    @Override
    public boolean onActivate(Block block, ItemStack item, Player player) {
        if (!block.getState().ensureTrait(BlockTraits.HAS_END_PORTAL_EYE) && player != null && item.getType() == ENDER_EYE) {
            block.set(block.getState().withTrait(BlockTraits.HAS_END_PORTAL_EYE, true), true);

            ((CloudLevel) block.getLevel()).addLevelSoundEvent(block.getPosition(), SoundEvent.BLOCK_END_PORTAL_FRAME_FILL);
            //TODO: create portal
            return true;
        }
        return false;
    }


    @Override
    public ItemStack toItem(Block block) {
        return CloudItemRegistry.get().getItem(block.getState().getType().getDefaultState());
    }

    @Override
    public BlockColor getColor(Block state) {
        return BlockColor.GREEN_BLOCK_COLOR;
    }


}
