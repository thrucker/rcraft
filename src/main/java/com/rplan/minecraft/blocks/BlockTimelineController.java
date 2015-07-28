package com.rplan.minecraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockTimelineController extends Block {
    protected BlockTimelineController() {
        super(Material.iron);

        setUnlocalizedName("timelineController");
        setCreativeTab(CreativeTabs.tabMisc);

        GameRegistry.registerBlock(this, "timelineController");
    }
}
