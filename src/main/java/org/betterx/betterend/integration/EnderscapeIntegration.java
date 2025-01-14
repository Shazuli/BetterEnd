package org.betterx.betterend.integration;

import org.betterx.bclib.api.v2.levelgen.biomes.BiomeAPI;
import org.betterx.bclib.api.v2.levelgen.features.BCLFeature;
import org.betterx.bclib.api.v2.tag.CommonBlockTags;
import org.betterx.bclib.api.v2.tag.TagAPI;
import org.betterx.bclib.integration.ModIntegration;

import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class EnderscapeIntegration extends ModIntegration {
    public EnderscapeIntegration() {
        super("enderscape");
    }

    @Override
    public void init() {
        Class<?> enderscape = getClass("net.enderscape.Enderscape");
        Class<?> enderscapeIslandsBiome = getClass("net.enderscape.world.biomes.EnderscapeIslandsBiome");
        MappedRegistry<?> biomes = getStaticFieldValue(enderscape, "ENDERSCAPE_BIOME");
        biomes.entrySet().forEach(entry -> {
            ResourceKey key = entry.getKey();
            Holder<Biome> biome = getBiome(key.location().getPath());
            if (enderscapeIslandsBiome.isInstance(entry.getValue())) {
                BiomeAPI.registerEndVoidBiome(biome);
            } else {
                BiomeAPI.registerEndLandBiome(biome);
            }
        });

        BCLFeature scatteredShadowQuartzOre = getFeature(
                "scattered_shadow_quartz_ore",
                Decoration.UNDERGROUND_DECORATION
        );
        BCLFeature voidNebuliteOre = getFeature("void_nebulite_ore", Decoration.UNDERGROUND_DECORATION);
        BCLFeature nebuliteOre = getFeature("nebulite_ore", Decoration.UNDERGROUND_DECORATION);

        BiomeAPI.registerEndBiomeModification((biomeID, biome) -> {
            if (!biomeID.getNamespace().equals("enderscape")) {
                BiomeAPI.addBiomeFeature(biome, scatteredShadowQuartzOre);
                BiomeAPI.addBiomeFeature(biome, voidNebuliteOre);
                BiomeAPI.addBiomeFeature(biome, nebuliteOre);
            }
        });

        TagAPI.addBlockTag(CommonBlockTags.END_STONES, getBlock("nebulite_ore"));
        TagAPI.addBlockTag(CommonBlockTags.END_STONES, getBlock("shadow_quartz_ore"));
    }
}
