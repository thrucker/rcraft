package com.rplan.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rheinicke on 28/07/15.
 */
public class BlockPlacer {

    public static final int PO_HEIGHT = 6;
    public static final int PO_WIDTH = 6;
    private World world;
    private BlockPos offset;
    private IBlockState poState;
    private IBlockState summaryState;

    private final IBlockState linkState;
    private final CalendarRenderer calendar;

    public BlockPlacer(World world, BlockPos offset) {
        this.world = world;
        this.offset = offset;
        poState = Block.getBlockById(133).getDefaultState();
        summaryState = Block.getBlockById(1).getStateFromMeta(6);
        linkState = Block.getBlockById(57).getDefaultState();
        calendar = new CalendarRenderer(world, offset);
    }

    public void render(PlanningObject[] pos) {
        Map<String, PlanningObject> poIdMap = new HashMap<String, PlanningObject>();


        for (PlanningObject po : pos) {
            render(po);
            poIdMap.put(po.id, po);
        }

        renderLinks(poIdMap);
    }

    private void renderLinks(Map<String, PlanningObject> poIdMap) {
        List<Link> links = getLinks(poIdMap);

        for (Link link : links) {
            renderLink(link, poIdMap);
        }
    }

    private void renderLink(Link link, Map<String, PlanningObject> poIdMap) {
        PlanningObject sourcePo = poIdMap.get(link.sourceId);
        PlanningObject targetPo = poIdMap.get(link.targetId);

        int[] sourceCoordinates;
        int[] targetCoordinates;
        boolean down;
        boolean right;
        int sourceOffset = 0;
        int targetOffset = 0;

        switch (link.type) {
            case START_START:
                sourceCoordinates = getStartCoordinate(sourcePo);
                targetCoordinates = getStartCoordinate(targetPo);
                sourceOffset = -1;
                targetOffset = -1;
                break;
            case START_END:
                sourceCoordinates = getStartCoordinate(sourcePo);
                targetCoordinates = getEndCoordinate(targetPo);
                sourceOffset = -1;
                targetOffset = 1;
                break;
            case END_START:
                sourceCoordinates = getEndCoordinate(sourcePo);
                targetCoordinates = getStartCoordinate(targetPo);
                sourceOffset = 1;
                targetOffset = -1;
                break;
            case END_END:
                sourceCoordinates = getEndCoordinate(sourcePo);
                targetCoordinates = getEndCoordinate(targetPo);
                sourceOffset = 1;
                targetOffset = 1;
                break;
            default:
                throw new RuntimeException("unknown link type");
        }

        world.setBlockState(offset.add(sourceCoordinates[0], 0, sourceCoordinates[1]), linkState);
        world.setBlockState(offset.add(targetCoordinates[0], 0, targetCoordinates[1]), linkState);

        sourceCoordinates[0] += sourceOffset;
        targetCoordinates[0] += targetOffset;

        down = targetCoordinates[1] - sourceCoordinates[1] > 0;
        right = targetCoordinates[0] - sourceCoordinates[0] > 0;

        if (down) {
            if (right) {
                switch (link.type) {
                    case START_END:
                        renderLinkVHV(sourceCoordinates, targetCoordinates, false, false);
                        break;
                    case START_START:
                    case END_START:
                        renderLinkVH(sourceCoordinates, targetCoordinates, false, false);
                        break;
                    case END_END:
                        renderLinkHV(sourceCoordinates, targetCoordinates, false, false);
                        break;
                }
            } else {
                switch (link.type) {
                    case START_START:
                    case START_END:
                        renderLinkHV(sourceCoordinates, targetCoordinates, true, false);
                        break;
                    case END_START:
                        renderLinkVHV(sourceCoordinates, targetCoordinates, true, false);
                        break;
                    case END_END:
                        renderLinkVH(sourceCoordinates, targetCoordinates, true, false);
                        break;
                }
            }
        } else {
            if (right) {
                switch (link.type) {
                    case START_END:
                        renderLinkVHV(sourceCoordinates, targetCoordinates, false, true);
                        break;
                    case START_START:
                    case END_START:
                        renderLinkVH(sourceCoordinates, targetCoordinates, false, true);
                        break;
                    case END_END:
                        renderLinkHV(sourceCoordinates, targetCoordinates, false, true);
                        break;
                }
            } else {
                switch (link.type) {
                    case START_START:
                    case START_END:
                        renderLinkHV(sourceCoordinates, targetCoordinates, true, true);
                        break;
                    case END_START:
                        renderLinkVHV(sourceCoordinates, targetCoordinates, true, true);
                        break;
                    case END_END:
                        renderLinkVH(sourceCoordinates, targetCoordinates, true, true);
                        break;
                }
            }
        }
    }

    private void renderLinkVHV(int[] sourceCoordinates, int[] targetCoordinates, boolean invertH, boolean invertV) {
        // V
        int from1 = invertV ? targetCoordinates[1] : sourceCoordinates[1];
        int to1 = from1 + 3;
        for (int y = from1; y <= to1; y++) {
            world.setBlockState(offset.add(sourceCoordinates[0], 0, y), linkState);
        }

        // H
        int from2 = invertH ? targetCoordinates[0] : sourceCoordinates[0];
        int to2 = invertH ? sourceCoordinates[0] : targetCoordinates[0];
        for (int x = from2; x <= to2; x++) {
            world.setBlockState(offset.add(x, 0, to1), linkState);
        }

        // V
        int from3 = to1;
        int to3 = invertV ? sourceCoordinates[1] : targetCoordinates[1];
        for (int y = from3; y <= to3; y++) {
            world.setBlockState(offset.add(to2, 0, y), linkState);
        }
    }

    private void renderLinkVH(int[] sourceCoordinates, int[] targetCoordinates, boolean invertH, boolean invertV) {
        // V
        int from1 = invertV ? targetCoordinates[1] : sourceCoordinates[1];
        int to1 = invertV ? sourceCoordinates[1] : targetCoordinates[1];
        for (int y = from1; y <= to1; y++) {
            world.setBlockState(offset.add(sourceCoordinates[0], 0, y), linkState);
        }

        // H
        int from2 = invertH ? targetCoordinates[0] : sourceCoordinates[0];
        int to2 = invertH ? sourceCoordinates[0] : targetCoordinates[0];
        for (int x = from2; x <= to2; x++) {
            world.setBlockState(offset.add(x, 0, targetCoordinates[1]), linkState);
        }
    }

    private void renderLinkHV(int[] sourceCoordinates, int[] targetCoordinates, boolean invertH, boolean invertV) {
        // H
        int from1 = invertH ? targetCoordinates[0] : sourceCoordinates[0];
        int to1 = invertH ? sourceCoordinates[0] : targetCoordinates[0];
        for (int x = from1; x <= to1; x++) {
            world.setBlockState(offset.add(x, 0, sourceCoordinates[1]), linkState);
        }

        // V
        int from2 = invertV ? targetCoordinates[1] : sourceCoordinates[1];
        int to2 = invertV ? sourceCoordinates[1] : targetCoordinates[1];
        for (int y = from2; y <= to2; y++) {
            world.setBlockState(offset.add(targetCoordinates[0], 0, y), linkState);
        }
    }

    private List<Link> getLinks(Map<String, PlanningObject> poIdMap) {
        List<Link> links = new ArrayList<Link>();

        for (PlanningObject po : poIdMap.values()) {
            links.addAll(po.links);
        }

        return links;
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

    private int[] getStartCoordinate(PlanningObject po) {
        int x = po.offsetDays * PO_WIDTH - 1;
        int y = po.lineNumber * PO_HEIGHT + 1;

        return new int[] {x, y};
    }

    private int[] getEndCoordinate(PlanningObject po) {
        int x = po.offsetDays * PO_WIDTH;
        int y = po.lineNumber * PO_HEIGHT + 1;
        int width = po.duration * PO_WIDTH;

        return new int[] {x + width, y};
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

        calendar.renderCalendar(start, days, lines);
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


    private IBlockState getState(PlanningObject po) {
        if (po.isProject || po.isSummaryTask) {
            return summaryState;
        } else {
            return poState;
        }
    }

}
