package org.betterx.betterend.world.features;

import org.betterx.bclib.api.v2.levelgen.features.features.DefaultFeature;
import org.betterx.bclib.util.BlocksHelper;
import org.betterx.bclib.util.MHelper;
import org.betterx.betterend.util.GlobalState;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public abstract class InvertedScatterFeature extends DefaultFeature {
    private final int radius;

    public InvertedScatterFeature(int radius) {
        this.radius = radius;
    }

    public abstract boolean canGenerate(
            WorldGenLevel world,
            RandomSource random,
            BlockPos center,
            BlockPos blockPos,
            float radius
    );

    public abstract void generate(WorldGenLevel world, RandomSource random, BlockPos blockPos);

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featureConfig) {
        final MutableBlockPos POS = GlobalState.stateForThread().POS;
        final RandomSource random = featureConfig.random();
        final BlockPos center = featureConfig.origin();
        final WorldGenLevel world = featureConfig.level();
        int maxY = world.getHeight(Heightmap.Types.WORLD_SURFACE, center.getX(), center.getZ());
        int minY = BlocksHelper.upRay(world, new BlockPos(center.getX(), 0, center.getZ()), maxY);
        for (int y = maxY; y > minY; y--) {
            POS.set(center.getX(), y, center.getZ());
            if (world.getBlockState(POS).isAir() && !world.getBlockState(POS.above()).isAir()) {
                float r = MHelper.randRange(radius * 0.5F, radius, random);
                int count = MHelper.floor(r * r * MHelper.randRange(0.5F, 1.5F, random));
                for (int i = 0; i < count; i++) {
                    float pr = r * (float) Math.sqrt(random.nextFloat());
                    float theta = random.nextFloat() * MHelper.PI2;
                    float x = pr * (float) Math.cos(theta);
                    float z = pr * (float) Math.sin(theta);

                    POS.set(center.getX() + x, center.getY() - 7, center.getZ() + z);
                    int up = BlocksHelper.upRay(world, POS, 16);
                    if (up > 14) continue;
                    POS.setY(POS.getY() + up);

                    if (canGenerate(world, random, center, POS, r)) {
                        generate(world, random, POS);
                    }
                }
            }
        }
        return true;
    }
}
