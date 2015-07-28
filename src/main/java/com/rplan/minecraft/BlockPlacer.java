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

    private static final int PO_HEIGHT = 6;
    private static final int PO_WIDTH = 6;
    private World world;
    private BlockPos offset;
    private IBlockState poState;
    private IBlockState summaryState;

    public BlockPlacer(World world, BlockPos offset) {
        this.world = world;
        this.offset = offset;
        poState = Block.getBlockById(133).getDefaultState();
        summaryState = Block.getBlockById(1).getStateFromMeta(6);

    }

    public void render(PlanningObject po) {
        int y = po.lineNumber * PO_HEIGHT;
        int x = po.offsetDays * PO_WIDTH;
        int width = po.duration * PO_WIDTH;
        int height = 3;

//        System.out.println("Placing " + po.name);
//        System.out.println("LineNumber: " + po.lineNumber);
//        System.out.println("OffsetDays: " + po.offsetDays);
//        System.out.println("Duration:" + po.duration);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                BlockPos pos = offset.add(x + i, 0, y + j);
                IBlockState state = getState(po);
                world.setBlockState(pos, state);
            }
        }

        if (po.isSummaryTask || po.isProject) {
            IBlockState state = getState(po);
            world.setBlockState(offset.add(x, 0, y + height), state);
            world.setBlockState(offset.add(x, 0, y + height + 1), state);
            world.setBlockState(offset.add(x + 1, 0, y + height), state);

            world.setBlockState(offset.add(x + width - 1, 0, y + height), state);
            world.setBlockState(offset.add(x + width - 1, 0, y + height + 1), state);
            world.setBlockState(offset.add(x + width - 2, 0, y + height), state);
        }
    }

    public void renderCalendar(BlockPos offset, int days, int pos) {


    }

    private IBlockState getState(PlanningObject po) {
        if (po.isProject || po.isSummaryTask) {
            return summaryState;
        } else {
            return poState;
        }
    }
}
