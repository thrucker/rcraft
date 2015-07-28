package com.rplan.minecraft;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
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
        try {
            String cookie = RestClient.login(args[0], args[1]);
            PlanningObjectLoader loader = new PlanningObjectLoader(cookie);
            PlanningObject[] pos = loader.getAll();

            BlockPlacer placer = new BlockPlacer(sender.getEntityWorld(), sender.getPosition());
            for (int i = 0; i < pos.length; i++) {
                placer.render(pos[i]);
            }
        }
        catch (Exception ex) {
            sender.addChatMessage(new ChatComponentText(ex.getMessage()));
        }
	}
}
