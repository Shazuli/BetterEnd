package org.betterx.betterend.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WallPlantOnLogFeature extends WallPlantFeature {
    public WallPlantOnLogFeature(Block block, int radius) {
        super(block, radius);
    }

    @Override
    public boolean canGenerate(WorldGenLevel world, RandomSource random, BlockPos pos, Direction dir) {
        BlockPos blockPos = pos.relative(dir.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.is(BlockTags.LOGS);
    }
}
