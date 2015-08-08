package com.rplan.minecraft;

import com.rplan.minecraft.LinkPlacer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.mockito.InOrder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class LinkPlacerTest {

    @org.junit.Test
    public void testSameSideRightDownPositive() throws Exception {
        IBlockPlacer blockPlacerMock = mock(IBlockPlacer.class);
        InOrder order = inOrder(blockPlacerMock);

        LinkPlacer linkPlacer = new LinkPlacer(blockPlacerMock);

        int[] sourceCoordinates = {0, 0};
        int[] targetCoordinates = {4, 4};

        linkPlacer.placeLinkSameSide(sourceCoordinates, targetCoordinates, 1);

        // a = 5 + 1
        order.verify(blockPlacerMock).placeBlock(1, 0);
        order.verify(blockPlacerMock).placeBlock(2, 0);
        order.verify(blockPlacerMock).placeBlock(3, 0);
        order.verify(blockPlacerMock).placeBlock(4, 0);
        order.verify(blockPlacerMock).placeBlock(5, 0);
        order.verify(blockPlacerMock).placeBlock(6, 0);

        // b = 4
        order.verify(blockPlacerMock).placeBlock(6, 1);
        order.verify(blockPlacerMock).placeBlock(6, 2);
        order.verify(blockPlacerMock).placeBlock(6, 3);
        order.verify(blockPlacerMock).placeBlock(6, 4);

        // c = -1
        order.verify(blockPlacerMock).placeBlock(5, 4);

        verifyNoMoreInteractions(blockPlacerMock);
    }

    @org.junit.Test
    public void testSameSideRightUpPositive() throws Exception {
        IBlockPlacer blockPlacerMock = mock(IBlockPlacer.class);
        InOrder order = inOrder(blockPlacerMock);

        LinkPlacer linkPlacer = new LinkPlacer(blockPlacerMock);

        int[] sourceCoordinates = {0, 4};
        int[] targetCoordinates = {4, 0};

        linkPlacer.placeLinkSameSide(sourceCoordinates, targetCoordinates, 1);

        // a = 5 + 1
        order.verify(blockPlacerMock).placeBlock(1, 4);
        order.verify(blockPlacerMock).placeBlock(2, 4);
        order.verify(blockPlacerMock).placeBlock(3, 4);
        order.verify(blockPlacerMock).placeBlock(4, 4);
        order.verify(blockPlacerMock).placeBlock(5, 4);
        order.verify(blockPlacerMock).placeBlock(6, 4);

        // b = -4
        order.verify(blockPlacerMock).placeBlock(6, 3);
        order.verify(blockPlacerMock).placeBlock(6, 2);
        order.verify(blockPlacerMock).placeBlock(6, 1);
        order.verify(blockPlacerMock).placeBlock(6, 0);

        // c = -1
        order.verify(blockPlacerMock).placeBlock(5, 0);

        verifyNoMoreInteractions(blockPlacerMock);
    }

    @org.junit.Test
    public void testSameSideLeftDownPositive() throws Exception {
        IBlockPlacer blockPlacerMock = mock(IBlockPlacer.class);
        InOrder order = inOrder(blockPlacerMock);

        LinkPlacer linkPlacer = new LinkPlacer(blockPlacerMock);

        int[] sourceCoordinates = {4, 0};
        int[] targetCoordinates = {0, 4};

        linkPlacer.placeLinkSameSide(sourceCoordinates, targetCoordinates, 1);

        // a = 1 + 1
        order.verify(blockPlacerMock).placeBlock(5, 0);
        order.verify(blockPlacerMock).placeBlock(6, 0);

        // b = 4
        order.verify(blockPlacerMock).placeBlock(6, 1);
        order.verify(blockPlacerMock).placeBlock(6, 2);
        order.verify(blockPlacerMock).placeBlock(6, 3);
        order.verify(blockPlacerMock).placeBlock(6, 4);

        // c = -5
        order.verify(blockPlacerMock).placeBlock(5, 4);
        order.verify(blockPlacerMock).placeBlock(4, 4);
        order.verify(blockPlacerMock).placeBlock(3, 4);
        order.verify(blockPlacerMock).placeBlock(2, 4);
        order.verify(blockPlacerMock).placeBlock(1, 4);

        verifyNoMoreInteractions(blockPlacerMock);
    }

    @org.junit.Test
    public void testSameSideLeftUpPositive() throws Exception {
        IBlockPlacer blockPlacerMock = mock(IBlockPlacer.class);
        InOrder order = inOrder(blockPlacerMock);

        LinkPlacer linkPlacer = new LinkPlacer(blockPlacerMock);

        int[] sourceCoordinates = {4, 4};
        int[] targetCoordinates = {0, 0};

        linkPlacer.placeLinkSameSide(sourceCoordinates, targetCoordinates, 1);

        // a = 1 + 1
        order.verify(blockPlacerMock).placeBlock(5, 4);
        order.verify(blockPlacerMock).placeBlock(6, 4);

        // b = -4
        order.verify(blockPlacerMock).placeBlock(6, 3);
        order.verify(blockPlacerMock).placeBlock(6, 2);
        order.verify(blockPlacerMock).placeBlock(6, 1);
        order.verify(blockPlacerMock).placeBlock(6, 0);

        // c = -5
        order.verify(blockPlacerMock).placeBlock(5, 0);
        order.verify(blockPlacerMock).placeBlock(4, 0);
        order.verify(blockPlacerMock).placeBlock(3, 0);
        order.verify(blockPlacerMock).placeBlock(2, 0);
        order.verify(blockPlacerMock).placeBlock(1, 0);

        verifyNoMoreInteractions(blockPlacerMock);
    }

    @org.junit.Test
    public void testSameSideRightDownNegative() throws Exception {
        IBlockPlacer blockPlacerMock = mock(IBlockPlacer.class);
        InOrder order = inOrder(blockPlacerMock);

        LinkPlacer linkPlacer = new LinkPlacer(blockPlacerMock);

        int[] sourceCoordinates = {0, 0};
        int[] targetCoordinates = {4, 4};

        linkPlacer.placeLinkSameSide(sourceCoordinates, targetCoordinates, -1);

        // a = -1 - 1
        order.verify(blockPlacerMock).placeBlock(-1, 0);
        order.verify(blockPlacerMock).placeBlock(-2, 0);

        // b = 4
        order.verify(blockPlacerMock).placeBlock(-2, 1);
        order.verify(blockPlacerMock).placeBlock(-2, 2);
        order.verify(blockPlacerMock).placeBlock(-2, 3);
        order.verify(blockPlacerMock).placeBlock(-2, 4);

        // c = 5
        order.verify(blockPlacerMock).placeBlock(-1, 4);
        order.verify(blockPlacerMock).placeBlock(0, 4);
        order.verify(blockPlacerMock).placeBlock(1, 4);
        order.verify(blockPlacerMock).placeBlock(2, 4);
        order.verify(blockPlacerMock).placeBlock(3, 4);

        verifyNoMoreInteractions(blockPlacerMock);
    }

    @org.junit.Test
    public void testSameSideRightUpNegative() throws Exception {
        IBlockPlacer blockPlacerMock = mock(IBlockPlacer.class);
        InOrder order = inOrder(blockPlacerMock);

        LinkPlacer linkPlacer = new LinkPlacer(blockPlacerMock);

        int[] sourceCoordinates = {0, 4};
        int[] targetCoordinates = {4, 0};

        linkPlacer.placeLinkSameSide(sourceCoordinates, targetCoordinates, -1);

        // a = -1 - 1
        order.verify(blockPlacerMock).placeBlock(-1, 4);
        order.verify(blockPlacerMock).placeBlock(-2, 4);

        // b = -4
        order.verify(blockPlacerMock).placeBlock(-2, 3);
        order.verify(blockPlacerMock).placeBlock(-2, 2);
        order.verify(blockPlacerMock).placeBlock(-2, 1);
        order.verify(blockPlacerMock).placeBlock(-2, 0);

        // c = 5
        order.verify(blockPlacerMock).placeBlock(-1, 0);
        order.verify(blockPlacerMock).placeBlock(0, 0);
        order.verify(blockPlacerMock).placeBlock(1, 0);
        order.verify(blockPlacerMock).placeBlock(2, 0);
        order.verify(blockPlacerMock).placeBlock(3, 0);

        verifyNoMoreInteractions(blockPlacerMock);
    }

    @org.junit.Test
    public void testSameSideLeftDownNegative() throws Exception {
        IBlockPlacer blockPlacerMock = mock(IBlockPlacer.class);
        InOrder order = inOrder(blockPlacerMock);

        LinkPlacer linkPlacer = new LinkPlacer(blockPlacerMock);

        int[] sourceCoordinates = {4, 0};
        int[] targetCoordinates = {0, 4};

        linkPlacer.placeLinkSameSide(sourceCoordinates, targetCoordinates, -1);

        // a = -5 - 1
        order.verify(blockPlacerMock).placeBlock(3, 0);
        order.verify(blockPlacerMock).placeBlock(2, 0);
        order.verify(blockPlacerMock).placeBlock(1, 0);
        order.verify(blockPlacerMock).placeBlock(0, 0);
        order.verify(blockPlacerMock).placeBlock(-1, 0);
        order.verify(blockPlacerMock).placeBlock(-2, 0);

        // b = 4
        order.verify(blockPlacerMock).placeBlock(-2, 1);
        order.verify(blockPlacerMock).placeBlock(-2, 2);
        order.verify(blockPlacerMock).placeBlock(-2, 3);
        order.verify(blockPlacerMock).placeBlock(-2, 4);

        // c = 1
        order.verify(blockPlacerMock).placeBlock(-1, 4);

        verifyNoMoreInteractions(blockPlacerMock);
    }

    @org.junit.Test
    public void testSameSideLeftUpNegative() throws Exception {
        IBlockPlacer blockPlacerMock = mock(IBlockPlacer.class);
        InOrder order = inOrder(blockPlacerMock);

        LinkPlacer linkPlacer = new LinkPlacer(blockPlacerMock);

        int[] sourceCoordinates = {4, 4};
        int[] targetCoordinates = {0, 0};

        linkPlacer.placeLinkSameSide(sourceCoordinates, targetCoordinates, -1);

        // a = -5 -1
        order.verify(blockPlacerMock).placeBlock(3, 4);
        order.verify(blockPlacerMock).placeBlock(2, 4);
        order.verify(blockPlacerMock).placeBlock(1, 4);
        order.verify(blockPlacerMock).placeBlock(0, 4);
        order.verify(blockPlacerMock).placeBlock(-1, 4);
        order.verify(blockPlacerMock).placeBlock(-2, 4);

        // b = -4
        order.verify(blockPlacerMock).placeBlock(-2, 3);
        order.verify(blockPlacerMock).placeBlock(-2, 2);
        order.verify(blockPlacerMock).placeBlock(-2, 1);
        order.verify(blockPlacerMock).placeBlock(-2, 0);

        // c = 1
        order.verify(blockPlacerMock).placeBlock(-1, 0);
        
        verifyNoMoreInteractions(blockPlacerMock);
    }
}