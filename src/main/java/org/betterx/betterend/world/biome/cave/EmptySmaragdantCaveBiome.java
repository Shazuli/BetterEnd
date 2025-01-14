package org.betterx.betterend.world.biome.cave;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.betterend.registry.EndFeatures;
import org.betterx.betterend.registry.EndParticles;
import org.betterx.betterend.world.biome.EndBiome;

import net.minecraft.resources.ResourceLocation;

public class EmptySmaragdantCaveBiome extends EndCaveBiome.Config {
    public static class Biome extends EndCaveBiome {
        public Biome(ResourceLocation biomeID, net.minecraft.world.level.biome.Biome biome, BCLBiomeSettings settings) {
            super(biomeID, biome, settings);

            this.addFloorFeature(EndFeatures.SMARAGDANT_CRYSTAL, 1);
            this.addFloorFeature(EndFeatures.SMARAGDANT_CRYSTAL_SHARD, 20);

            this.addCeilFeature(EndFeatures.END_STONE_STALACTITE, 1);
        }

        @Override
        public float getFloorDensity() {
            return 0.1F;
        }

        @Override
        public float getCeilDensity() {
            return 0.1F;
        }
    }

    public EmptySmaragdantCaveBiome() {
        super("empty_smaragdant_cave");
    }

    @Override
    protected void addCustomBuildData(BCLBiomeBuilder builder) {
        super.addCustomBuildData(builder);
        builder.fogColor(0, 253, 182)
               .fogDensity(2.0F)
               .plantsColor(0, 131, 145)
               .waterAndFogColor(31, 167, 212)
               .particles(EndParticles.SMARAGDANT, 0.001F);
    }

    @Override
    public BiomeSupplier<EndBiome> getSupplier() {
        return EmptySmaragdantCaveBiome.Biome::new;
    }
}
