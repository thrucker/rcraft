package com.example.examplemod;

import java.util.List;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		// some example code
        System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
        List recipies = CraftingManager.getInstance().getRecipeList();
        //GameRegistry.addRecipe(recipe);
        IRecipe recipe = (IRecipe) recipies.get(0);
        ICommandManager manager;
    }
    
    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
    	ServerCommandManager manager = (ServerCommandManager)event.getServer().getCommandManager();
    	manager.registerCommand(new TestCommand());
    }
}