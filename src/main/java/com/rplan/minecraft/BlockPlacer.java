package com.rplan.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

/**
 * Created by rheinicke on 28/07/15.
 */
public class BlockPlacer {
    private World world;
    private BlockPos offset;
    private IBlockState poState;

    public BlockPlacer(World world, BlockPos offset) {
        this.world = world;
        this.offset = offset;
        poState = Block.getBlockById(133).getDefaultState();
    }

    public void render(PlanningObject po) {
        int y = po.lineNumber * 5;
        int x = po.offsetDays * 5;
        int width = po.duration * 5;
        int height = 3;

        System.out.println("Placing " + po.name);
        System.out.println("LineNumber: " + po.lineNumber);
        System.out.println("OffsetDays: " + po.offsetDays);
        System.out.println("Duration:" + po.duration);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                BlockPos pos = offset.add(x + i, 0, y + j);
                world.setBlockState(pos, poState);
            }
        }
    }
}
