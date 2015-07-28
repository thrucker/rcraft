package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

public class TestCommand extends CommandBase {

	@Override
	public String getName() {
		return "foo";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "foobarbaz";
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player;
		if (sender instanceof EntityPlayer) {
		}
        sender.addChatMessage(new ChatComponentText("placing blocks"));
        if (args.length > 0) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    BlockPos pos = sender.getPosition().add(i, j, 2);
                    sender.getEntityWorld().setBlockToAir(pos);
                }
            }
        } else {
            Block dirt = Block.getBlockById(1);
            IBlockState s = dirt.getDefaultState();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    BlockPos pos = sender.getPosition().add(i, j, 2);
                    sender.getEntityWorld().setBlockState(pos, s);
                }
            }
        }
		sender.addChatMessage(new ChatComponentText("done"));
	}
}

