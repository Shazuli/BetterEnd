package org.betterx.betterend.blocks;

import org.betterx.betterend.blocks.basis.PottableFeatureSapling;
import org.betterx.betterend.registry.EndBlocks;
import org.betterx.betterend.registry.EndFeatures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DragonTreeSaplingBlock extends PottableFeatureSapling {
    public DragonTreeSaplingBlock() {
        super((state) -> EndFeatures.DRAGON_TREE);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.below()).is(EndBlocks.SHADOW_GRASS);
    }

    @Override
    public boolean canPlantOn(Block block) {
        return block == EndBlocks.SHADOW_GRASS;
    }
}
