package org.cloudburstmc.server.block.behavior;

import com.nukkitx.math.vector.Vector3f;
import net.daporkchop.lib.random.impl.ThreadLocalPRandom;
import org.cloudburstmc.api.block.Block;
import org.cloudburstmc.api.block.BlockCategory;
import org.cloudburstmc.api.block.BlockStates;
import org.cloudburstmc.api.block.BlockTypes;
import org.cloudburstmc.api.item.ItemStack;
import org.cloudburstmc.api.item.ItemTypes;
import org.cloudburstmc.api.player.Player;
import org.cloudburstmc.api.util.Direction;
import org.cloudburstmc.api.util.data.BlockColor;
import org.cloudburstmc.api.util.data.DyeColor;
import org.cloudburstmc.server.item.CloudItemStack;
import org.cloudburstmc.server.level.CloudLevel;
import org.cloudburstmc.server.level.feature.WorldFeature;
import org.cloudburstmc.server.level.feature.tree.GenerationTreeSpecies;
import org.cloudburstmc.server.level.particle.BoneMealParticle;
import org.cloudburstmc.server.registry.CloudItemRegistry;

import java.util.concurrent.ThreadLocalRandom;

public abstract class BlockBehaviorMushroom extends FloodableBlockBehavior {

    @Override
    public int onUpdate(Block block, int type) {
        if (type == CloudLevel.BLOCK_UPDATE_NORMAL) {
            if (!canStay(block)) {
                block.getLevel().useBreakOn(block.getPosition());
                return CloudLevel.BLOCK_UPDATE_NORMAL;
            }
        }
        return 0;
    }

    @Override
    public boolean place(ItemStack item, Block block, Block target, Direction face, Vector3f clickPos, Player player) {
        if (canStay(block)) {
            placeBlock(block, item);
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeActivated(Block block) {
        return true;
    }

    @Override
    public boolean onActivate(Block block, ItemStack item, Player player) {
        if (item.getType() == ItemTypes.DYE && item.getMetadata(DyeColor.class) == DyeColor.WHITE) {
            if (player != null && player.getGamemode().isSurvival()) {
                player.getInventory().decrementHandCount();
            }

            if (ThreadLocalRandom.current().nextFloat() < 0.4) {
                this.grow(block);
            }

            ((CloudLevel) block.getLevel()).addParticle(new BoneMealParticle(block.getPosition()));
            return true;
        }
        return false;
    }

    public boolean grow(Block block) {
        block.set(BlockStates.AIR, true, false);

        var item = (CloudItemStack) CloudItemRegistry.get().getItem(block.getState());
        WorldFeature feature = GenerationTreeSpecies.fromItem(item.getId(), item.getNetworkData().getDamage()).getDefaultGenerator();

        if (feature.place(block.getLevel(), ThreadLocalPRandom.current(), block.getX(), block.getY(), block.getZ())) {
            return true;
        } else {
            block.set(block.getState(), true, false);
            return false;
        }
    }

    public boolean canStay(Block block) {
        var state = block.down().getState();
        return state.getType() == BlockTypes.MYCELIUM || state.getType() == BlockTypes.PODZOL ||
                (!state.inCategory(BlockCategory.TRANSPARENT) && block.getLevel().getFullLight(block.getPosition()) < 13);
    }

    @Override
    public BlockColor getColor(Block block) {
        return BlockColor.FOLIAGE_BLOCK_COLOR;
    }


    protected abstract int getType();
}
