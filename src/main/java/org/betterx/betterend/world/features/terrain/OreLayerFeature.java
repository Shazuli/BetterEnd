package org.betterx.betterend.world.features.terrain;

import org.betterx.bclib.api.v2.levelgen.features.features.DefaultFeature;
import org.betterx.bclib.sdf.SDF;
import org.betterx.bclib.sdf.operator.SDFCoordModify;
import org.betterx.bclib.sdf.operator.SDFScale3D;
import org.betterx.bclib.sdf.primitive.SDFSphere;
import org.betterx.bclib.util.MHelper;
import org.betterx.betterend.noise.OpenSimplexNoise;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class OreLayerFeature extends DefaultFeature {
    private static final SDFSphere SPHERE;
    private static final SDFCoordModify NOISE;
    private static final SDF FUNCTION;

    private final BlockState state;
    private final float radius;
    private final int minY;
    private final int maxY;
    private OpenSimplexNoise noise;

    public OreLayerFeature(BlockState state, float radius, int minY, int maxY) {
        this.state = state;
        this.radius = radius;
        this.minY = minY;
        this.maxY = maxY;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featureConfig) {
        final RandomSource random = featureConfig.random();
        final BlockPos pos = featureConfig.origin();
        final WorldGenLevel world = featureConfig.level();
        float radius = this.radius * 0.5F;
        int r = MHelper.floor(radius + 1);
        int posX = MHelper.randRange(Math.max(r - 16, 0), Math.min(31 - r, 15), random) + pos.getX();
        int posZ = MHelper.randRange(Math.max(r - 16, 0), Math.min(31 - r, 15), random) + pos.getZ();
        int posY = MHelper.randRange(minY, maxY, random);

        if (noise == null) {
            noise = new OpenSimplexNoise(world.getSeed());
        }

        SPHERE.setRadius(radius).setBlock(state);
        NOISE.setFunction((vec) -> {
            double x = (vec.x() + pos.getX()) * 0.1;
            double z = (vec.z() + pos.getZ()) * 0.1;
            double offset = noise.eval(x, z);
            vec.set(vec.x(), vec.y() + (float) offset * 8, vec.z());
        });
        FUNCTION.fillRecursive(world, new BlockPos(posX, posY, posZ));
        return true;
    }

    static {
        SPHERE = new SDFSphere();
        NOISE = new SDFCoordModify();

        SDF body = SPHERE;
        body = new SDFScale3D().setScale(1, 0.2F, 1).setSource(body);
        body = NOISE.setSource(body);
        body.setReplaceFunction((state) -> {
            return state.is(Blocks.END_STONE);
        });

        FUNCTION = body;
    }
}
