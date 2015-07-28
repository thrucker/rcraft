package com.rplan.minecraft;

import com.rplan.minecraft.blocks.Blocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {

    }

    public void init(FMLInitializationEvent e) {
        Blocks.init();
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}
