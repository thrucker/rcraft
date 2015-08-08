package com.rplan.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class LinkPlacer {
    private static final int A = 1;
    private static final int B = 1;
    private static final int E = 1;
    private IBlockPlacer blockPlacer;

    public LinkPlacer(IBlockPlacer blockPlacer) {
        this.blockPlacer = blockPlacer;
    }

    public void renderLink(Link link, Map<String, PlanningObject> poIdMap) {
        PlanningObject sourcePo = poIdMap.get(link.sourceId);
        PlanningObject targetPo = poIdMap.get(link.targetId);

        int[] sourceCoordinates;
        int[] targetCoordinates;
        int direction;
        boolean sameSide = false;

        switch (link.type) {
            case START_START:
                sourceCoordinates = getStartCoordinate(sourcePo);
                targetCoordinates = getStartCoordinate(targetPo);
                direction = -1;
                sameSide = true;
                break;
            case START_END:
                sourceCoordinates = getStartCoordinate(sourcePo);
                targetCoordinates = getEndCoordinate(targetPo);
                direction = -1;
                break;
            case END_START:
                sourceCoordinates = getEndCoordinate(sourcePo);
                targetCoordinates = getStartCoordinate(targetPo);
                direction = 1;
                break;
            case END_END:
                sourceCoordinates = getEndCoordinate(sourcePo);
                targetCoordinates = getEndCoordinate(targetPo);
                direction = 1;
                sameSide = true;
                break;
            default:
                throw new RuntimeException("unknown link type");
        }

        if (sameSide) {
            placeLinkSameSide(sourceCoordinates, targetCoordinates, direction);
        } else {
            placeLinkOppositeSide(sourceCoordinates, targetCoordinates, direction);
        }
    }

    private void placeLinkOppositeSide(int[] sourceCoordinates, int[] targetCoordinates, int direction) {

    }

    public void placeLinkSameSide(int[] sourceCoordinates, int[] targetCoordinates, int direction) {
        int dx = targetCoordinates[0] - sourceCoordinates[0];
        int dy = targetCoordinates[1] - sourceCoordinates[1];
        int a = direction * Math.max(A, direction * dx + E);

        int b = dy;
        int c = dx - a;

        int x = (int) (sourceCoordinates[0] + Math.signum(direction));
        int y = sourceCoordinates[1];

        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);

        blockPlacer.placeBlock(x, y);

        // a
        int xInc = (int) Math.signum(a);
        for (int i = 0; i < Math.abs(a); i++) {
            x += xInc;
            blockPlacer.placeBlock(x, y);
        }

        // b
        int yInc = (int) Math.signum(b);
        for (int i = 0; i < Math.abs(b); i++) {
            y += yInc;
            blockPlacer.placeBlock(x, y);
        }

        // c
        xInc = (int) Math.signum(c);
        for (int i = 0; i < Math.abs(c); i++) {
            x += xInc;
            blockPlacer.placeBlock(x, y);
        }
    }

    public void placeLink(int[] sourceCoordinates, int[] targetCoordinates, int sourceDirection) {
        int dx = targetCoordinates[0] - sourceCoordinates[0];
        int dy = targetCoordinates[1] - sourceCoordinates[1];
        int a = sourceDirection * A;
        int b = (int) (Math.signum(dy) * B);
        int c = Math.min(0, dx - a - E);
        int d = dy - b;
        int e = Math.max(0, dx - a - E) + E;

        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        System.out.println("d = " + d);
        System.out.println("e = " + e);
        System.out.println("dx = " + dx);
        System.out.println("dy = " + dy);

        int x = sourceCoordinates[0];
        int y = sourceCoordinates[1];
        int xInc = (int) Math.signum(a);
        int yInc = (int) Math.signum(dy);

        // a
        for (int i = 0; i < Math.abs(a); i++, x += xInc) {
            blockPlacer.placeBlock(x, y);
        }

        // b
        for (int i = 0; i < Math.abs(b); i++, y += yInc) {
            blockPlacer.placeBlock(x, y);
        }

        // c
        xInc = (int) Math.signum(c);
        for (int i = 0; i < Math.abs(c); i++, x += xInc) {
            blockPlacer.placeBlock(x, y);
        }

        // d
        for (int i = 0; i < Math.abs(d); i++, y += yInc) {
            blockPlacer.placeBlock(x, y);
        }

        // e
        xInc = (int) Math.signum(e);
        for (int i = 0; i < Math.abs(e); i++, x += xInc) {
            blockPlacer.placeBlock(x, y);
        }
    }

    private int[] getStartCoordinate(PlanningObject po) {
        int x = po.offsetDays * BlockPlacer.PO_WIDTH ;
        int y = po.lineNumber * BlockPlacer.PO_HEIGHT + 1;

        return new int[] {x, y};
    }

    private int[] getEndCoordinate(PlanningObject po) {
        int x = po.offsetDays * BlockPlacer.PO_WIDTH - 1;
        int y = po.lineNumber * BlockPlacer.PO_HEIGHT + 1;
        int width = po.duration * BlockPlacer.PO_WIDTH;

        return new int[] {x + width, y};
    }
}
