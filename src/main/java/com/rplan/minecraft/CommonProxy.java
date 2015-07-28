package com.rplan.minecraft;

import com.rplan.minecraft.blocks.Blocks;
import com.rplan.minecraft.tileentity.TileEntities;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {

    }

    public void init(FMLInitializationEvent e) {
        Blocks.init();
        TileEntities.init();
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}
