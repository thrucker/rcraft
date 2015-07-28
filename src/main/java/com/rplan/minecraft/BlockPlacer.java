package com.rplan.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rheinicke on 28/07/15.
 */
public class BlockPlacer {

    private static final int PO_HEIGHT = 6;
    private static final int PO_WIDTH = 6;
    private static final int CALENDAR_OFFSET = -1;
    private World world;
    private BlockPos offset;
    private IBlockState poState;
    private IBlockState summaryState;
    private final IBlockState calendarWeekDay;
    private final IBlockState calendarWeekEndDay;
    private final IBlockState calendarDayStart;
    private final IBlockState calendarWeekEndDayStart;

    public BlockPlacer(World world, BlockPos offset) {
        this.world = world;
        this.offset = offset;
        poState = Block.getBlockById(133).getDefaultState();
        summaryState = Block.getBlockById(1).getStateFromMeta(6);
        calendarWeekDay = Block.getBlockById(35).getDefaultState();
        calendarWeekEndDay = Block.getBlockById(35).getStateFromMeta(8);
        calendarDayStart = Block.getBlockById(171).getDefaultState();
        calendarWeekEndDayStart = Block.getBlockById(171).getStateFromMeta(8);
    }

    public void render(PlanningObject[] pos) {
        Map<String, PlanningObject> poIdMap = new HashMap<String, PlanningObject>();


        for (PlanningObject po : pos) {
            render(po);
            poIdMap.put(po.id, po);
        }

    }

    public void render(PlanningObject po) {
        if (po.isProject || po.isSummaryTask) {
            renderTask(po);
            renderLittleHooks(po);
        } else {
            if (po.duration == 0) {
                renderMilestone(po);
            } else {
                renderTask(po);
            }
        }
    }

    private void renderMilestone(PlanningObject po) {
        int y = po.lineNumber * PO_HEIGHT;
        int x = po.offsetDays * PO_WIDTH;

        IBlockState state = getState(po);
        world.setBlockState(offset.add(x + 0, 0, y + 0), state);
        world.setBlockState(offset.add(x - 1, 0, y + 1), state);
        world.setBlockState(offset.add(x + 0, 0, y + 1), state);
        world.setBlockState(offset.add(x + 1, 0, y + 1), state);
        world.setBlockState(offset.add(x - 2, 0, y + 2), state);
        world.setBlockState(offset.add(x - 1, 0, y + 2), state);
        world.setBlockState(offset.add(x + 0, 0, y + 2), state);
        world.setBlockState(offset.add(x + 1, 0, y + 2), state);
        world.setBlockState(offset.add(x + 2, 0, y + 2), state);
        world.setBlockState(offset.add(x - 1, 0, y + 3), state);
        world.setBlockState(offset.add(x + 0, 0, y + 3), state);
        world.setBlockState(offset.add(x + 1, 0, y + 3), state);
        world.setBlockState(offset.add(x + 0, 0, y + 4), state);
    }

    private void renderTask(PlanningObject po) {
        int y = po.lineNumber * PO_HEIGHT;
        int x = po.offsetDays * PO_WIDTH;
        int width = po.duration * PO_WIDTH;
        int height = 3;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                BlockPos pos = offset.add(x + i, 0, y + j);
                IBlockState state = getState(po);
                world.setBlockState(pos, state);
            }
        }
    }

    private void renderLittleHooks(PlanningObject po) {
        int y = po.lineNumber * PO_HEIGHT;
        int x = po.offsetDays * PO_WIDTH;
        int width = po.duration * PO_WIDTH;
        int height = 3;

        IBlockState state = getState(po);
        world.setBlockState(offset.add(x, 0, y + height), state);
        world.setBlockState(offset.add(x, 0, y + height + 1), state);
        world.setBlockState(offset.add(x + 1, 0, y + height), state);

        world.setBlockState(offset.add(x + width - 1, 0, y + height), state);
        world.setBlockState(offset.add(x + width - 1, 0, y + height + 1), state);
        world.setBlockState(offset.add(x + width - 2, 0, y + height), state);

    }

    public void renderCalendar(PlanningObject[] pos) {
        int lines = pos.length;
        int days = getTimeDeltaFromPOs(pos);
        DateTime start = getStartDate(pos);

        renderCalendar(start, days, lines);
    }

    private int getTimeDeltaFromPOs(PlanningObject[] pos) {
        int maxEnd = 0;
        for (PlanningObject po : pos) {
            int endDay = po.offsetDays + po.duration;
            maxEnd = endDay > maxEnd? endDay : maxEnd;
        }
        return maxEnd;
    }

    private DateTime getStartDate(PlanningObject[] pos) {
        long minStart = Long.MAX_VALUE;
        PlanningObject minPo = null;
        for (PlanningObject po : pos) {
            if (po.start.getTime() < minStart) {
                minStart = po.start.getTime();
                minPo = po;
            }
        }
        DateTime dt = new DateTime(minPo.start);
        return dt;
    }

    public void renderCalendar(DateTime start, int days, int rows) {
        DateTime dateTime = start;
        for (int day = 0; day < days; day++) {
            for (int row = 0; row < rows; row++) {
                renderDay(dateTime, day, row);
            }
            dateTime = dateTime.plusDays(1);
        }
    }

    public void renderDay(DateTime start, int day, int row) {
        BlockPos topLeft = offset.add(day * PO_WIDTH, CALENDAR_OFFSET, row * PO_HEIGHT);
        for (int px = 0; px < PO_WIDTH; px++) {
            for (int pz = 0; pz < PO_HEIGHT; pz++) {
                IBlockState background;
                IBlockState marker;

                int dow = start.getDayOfWeek();
                if (dow == DateTimeConstants.SATURDAY || dow == DateTimeConstants.SUNDAY) {
                    background = calendarWeekEndDay;
                    marker = calendarWeekEndDayStart;
                } else {
                    background = calendarWeekDay;
                    marker = calendarDayStart;
                }

                world.setBlockState(topLeft.add(px, 0, pz), background);
                if (px == 0) {
                    world.setBlockState(topLeft.add(px, 1, pz), marker);
                } else {
                    world.setBlockToAir(topLeft.add(px, 1, pz));
                }
            }
        }
    }

    private IBlockState getState(PlanningObject po) {
        if (po.isProject || po.isSummaryTask) {
            return summaryState;
        } else {
            return poState;
        }
    }

}
