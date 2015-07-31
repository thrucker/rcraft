package com.rplan.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import static com.rplan.minecraft.BlockPlacer.*;

/**
 * Created by lorenz on 31.07.15.
 */
public class CalendarRenderer {
    private final IBlockState calendarWeekDay;
    private final IBlockState calendarWeekEndDay;
    private final IBlockState calendarDayStart;
    private final IBlockState calendarWeekEndDayStart;
    private final World world;
    private final BlockPos offset;

    public CalendarRenderer(World world, BlockPos baseCoordinate) {
        calendarWeekDay = Block.getBlockById(35).getDefaultState();
        calendarWeekEndDay = Block.getBlockById(35).getStateFromMeta(8);
        calendarDayStart = Block.getBlockById(171).getDefaultState();
        calendarWeekEndDayStart = Block.getBlockById(171).getStateFromMeta(8);
        this.world = world;
        offset = baseCoordinate;
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

    private static final int CALENDAR_OFFSET = -1;

}
