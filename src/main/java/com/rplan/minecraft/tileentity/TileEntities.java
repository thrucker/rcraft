package com.rplan.minecraft.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntities {
    public static TileEntityTimelineController tileEntityTimelineController;

    public static void init() {
        GameRegistry.registerTileEntity(TileEntityTimelineController.class, "TileEntityTimelineController");
    }
}
