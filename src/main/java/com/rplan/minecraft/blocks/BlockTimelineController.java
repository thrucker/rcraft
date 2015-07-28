package com.rplan.minecraft.blocks;

import com.rplan.minecraft.tileentity.TileEntityTimelineController;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockTimelineController extends Block implements ITileEntityProvider {
    protected BlockTimelineController() {
        super(Material.iron);

        setUnlocalizedName("timelineController");
        setCreativeTab(CreativeTabs.tabMisc);

        GameRegistry.registerBlock(this, "timelineController");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTimelineController();
    }
}
