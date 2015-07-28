package com.rplan.minecraft;

import java.util.List;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = RCraftMod.MODID, version = RCraftMod.VERSION)
public class RCraftMod
{
    public static final String MODID = "rcraftmod";
    public static final String VERSION = "0.1";

    @Mod.Instance(value = MODID)
    public static RCraftMod instance;

    @SidedProxy(modId = MODID, clientSide = "com.rplan.minecraft.ClientProxy", serverSide = "com.rplan.minecraft.ServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        ServerCommandManager manager = (ServerCommandManager)event.getServer().getCommandManager();
        manager.registerCommand(new TestCommand());
    }
}
