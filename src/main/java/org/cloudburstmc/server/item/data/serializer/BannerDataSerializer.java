package org.cloudburstmc.server.item.data.serializer;

import com.google.common.collect.ImmutableList;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtMapBuilder;
import com.nukkitx.nbt.NbtType;
import org.cloudburstmc.api.item.ItemStack;
import org.cloudburstmc.api.item.data.BannerData;
import org.cloudburstmc.api.util.Identifier;
import org.cloudburstmc.api.util.data.BannerPattern;
import org.cloudburstmc.api.util.data.DyeColor;

import java.util.ArrayList;
import java.util.List;

public class BannerDataSerializer implements ItemDataSerializer<BannerData> {

    @Override
    public void serialize(ItemStack item, NbtMapBuilder rootTag, NbtMapBuilder dataTag, BannerData value) {
        dataTag.putInt("Base", value.getBase().getDyeData());
        dataTag.putInt("Type", value.getType());

        if (!value.getPatterns().isEmpty()) {
            List<NbtMap> patternsTag = new ArrayList<>();
            for (BannerPattern pattern : value.getPatterns()) {
                patternsTag.add(NbtMap.builder().
                        putInt("Color", pattern.getColor().getDyeData() & 0x0f).
                        putString("Pattern", pattern.getType().getName())
                        .build());
            }
            dataTag.putList("Patterns", NbtType.COMPOUND, patternsTag);
        }
    }

    @Override
    public BannerData deserialize(Identifier id, NbtMap rootTag, NbtMap dataTag) {
        var base = DyeColor.getByDyeData(dataTag.getInt("Base", 0));
        var bannerType = dataTag.getInt("Type", 0);

        var patternTags = dataTag.getList("Patterns", NbtType.COMPOUND);
        List<BannerPattern> patterns;

        if (patternTags != null) {
            patterns = new ArrayList<>(patternTags.size());

            for (NbtMap patternTag : patternTags) {
                String pattern = patternTag.getString("Pattern");
                DyeColor color = DyeColor.getByDyeData(patternTag.getInt("Color"));
                patterns.add(new BannerPattern(BannerPattern.Type.getByName(pattern), color));
            }
        } else {
            patterns = ImmutableList.of();
        }

        return BannerData.of(bannerType, base, patterns);
    }
}
