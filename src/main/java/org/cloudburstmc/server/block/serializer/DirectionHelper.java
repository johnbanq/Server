package org.cloudburstmc.server.block.serializer;

import com.nukkitx.nbt.NbtMapBuilder;
import lombok.experimental.UtilityClass;
import org.cloudburstmc.server.block.BlockState;
import org.cloudburstmc.server.block.BlockTraits;
import org.cloudburstmc.server.math.BlockFace;
import org.cloudburstmc.server.math.NukkitMath;
import org.cloudburstmc.server.utils.Identifier;

import javax.annotation.Nonnull;
import java.util.*;

import static org.cloudburstmc.server.block.BlockTypes.*;
import static org.cloudburstmc.server.block.serializer.DirectionHelper.SeqType.*;

@UtilityClass
public class DirectionHelper {

    private final Map<SeqType, List<BlockFace>> faceObjTranslators = new EnumMap<>(SeqType.class);
    private final Map<SeqType, Map<BlockFace, Byte>> faceMetaTranslators = new EnumMap<>(SeqType.class);

    private final Map<Identifier, SeqType> mapping = new HashMap<>();

    public void init() {
        register(TYPE_1, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST);
        register(TYPE_2, BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST);
        register(TYPE_3, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH);
        register(TYPE_4, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH);
        register(TYPE_5, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST);
        register(TYPE_6, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);

        register(TYPE_7, BlockFace.DOWN, BlockFace.UP, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST);
        register(TYPE_8, BlockFace.DOWN, BlockFace.UP, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST);
        register(TYPE_9, BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.UP);

        registerDefaultMappings();
    }

    private void registerDefaultMappings() {
        register(TYPE_1,
                END_PORTAL_FRAME,
                WALL_BANNER,
                WALL_SIGN
        );

        register(TYPE_2,
                ANVIL,
                BED,
                FENCE_GATE,
                ACACIA_FENCE_GATE,
                NETHER_BRICK_FENCE,
                BIRCH_FENCE_GATE,
                DARK_OAK_FENCE_GATE,
                JUNGLE_FENCE_GATE,
                POWERED_REPEATER,
                UNPOWERED_REPEATER,
                PUMPKIN,
                CARVED_PUMPKIN,
                LIT_PUMPKIN,
                TRIPWIRE_HOOK
        );

        register(TYPE_3,
                ACACIA_DOOR,
                BIRCH_DOOR,
                DARK_OAK_DOOR,
                IRON_DOOR,
                JUNGLE_DOOR,
                SPRUCE_DOOR,
                WOODEN_DOOR
        );

        register(TYPE_4,
                FRAME,
                JUNGLE_STAIRS,
                DARK_OAK_STAIRS,
                BIRCH_STAIRS,
                SPRUCE_STAIRS,
                ACACIA_STAIRS,
                OAK_STAIRS,
                SANDSTONE_STAIRS,
                SMOOTH_SANDSTONE_STAIRS,
                STONE_BRICK_STAIRS,
                BRICK_STAIRS,
                MOSSY_COBBLESTONE_STAIRS,
                PRISMARINE_STAIRS,
                PRISMARINE_BRICKS_STAIRS,
                PURPUR_STAIRS,
                QUARTZ_STAIRS,
                SMOOTH_QUARTZ_STAIRS,
                RED_SANDSTONE_STAIRS,
                SMOOTH_RED_SANDSTONE_STAIRS,
                DIORITE_STAIRS,
                END_BRICK_STAIRS,
                GRANITE_STAIRS,
                MOSSY_STONE_BRICK_STAIRS,
                NETHER_BRICK_STAIRS,
                NORMAL_STONE_STAIRS,
                POLISHED_ANDESITE_STAIRS,
                POLISHED_DIORITE_STAIRS,
                POLISHED_GRANITE_STAIRS,
                RED_NETHER_BRICK_STAIRS,
                STONE_STAIRS,
                SMOOTH_STONE,
                DARK_PRISMARINE_STAIRS
        );

        register(TYPE_5,
                TRAPDOOR,
                ACACIA_TRAPDOOR,
                BIRCH_TRAPDOOR,
                DARK_OAK_TRAPDOOR,
                IRON_TRAPDOOR,
                JUNGLE_TRAPDOOR,
                SPRUCE_TRAPDOOR
        );

        register(TYPE_6,
                COCOA,
                POWERED_COMPARATOR,
                UNPOWERED_COMPARATOR
        );

        register(TYPE_7,
                HOPPER,
                DROPPER,
                DISPENSER,
                END_ROD
        );

        register(TYPE_8,
                OBSERVER
        );

        register(TYPE_9,
                SPRUCE_BUTTON,
                JUNGLE_BUTTON,
                DARK_OAK_BUTTON,
                ACACIA_BUTTON,
                BIRCH_BUTTON,
                WOODEN_BUTTON,
                STONE_BUTTON
        );
    }

    public void register(SeqType type, Identifier... identifiers) {
        for (Identifier identifier : identifiers) {
            mapping.put(identifier, type);
        }
    }

    private void register(SeqType type, BlockFace... seq) {
        EnumMap<BlockFace, Byte> map = new EnumMap<>(BlockFace.class);

        for (byte i = 0; i < seq.length; i++) {
            map.put(seq[i], i);
        }

        faceObjTranslators.put(type, new ArrayList<>(Arrays.asList(seq)));
        faceMetaTranslators.put(type, map);
    }

    public BlockFace fromMeta(int meta, SeqType type) {
        List<BlockFace> list = faceObjTranslators.get(type);

        meta = NukkitMath.clamp(meta, 0, list.size() - 1);

        return list.get(meta);
    }

    public short toMeta(@Nonnull BlockFace direction, SeqType type) {
        return faceMetaTranslators.get(type).get(direction);
    }

    @SuppressWarnings("ConstantConditions")
    public short serialize(@Nonnull NbtMapBuilder builder, @Nonnull BlockState state) {
        SeqType type = mapping.getOrDefault(state.getType(), TYPE_2); //2 is the most common
        return toMeta(state.getTrait(BlockTraits.DIRECTION), type);
    }

    @SuppressWarnings("ConstantConditions")
    public void serialize(@Nonnull NbtMapBuilder builder, @Nonnull BlockState state, SeqType type) {
        builder.putInt("direction", toMeta(state.getTrait(BlockTraits.DIRECTION), type));
    }

    public enum SeqType {
        //horizontal
        TYPE_1,
        TYPE_2,
        TYPE_3,
        TYPE_4,
        TYPE_5,
        TYPE_6, //2 reversed

        //omnidirectional
        TYPE_7,
        TYPE_8,
        TYPE_9
    }
}