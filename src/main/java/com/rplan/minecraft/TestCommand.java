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
            String cookie = RestClient.login("lorenz.hahn@actano.de", "MacBook Air");
            PlanningObjectLoader loader = new PlanningObjectLoader(cookie);
            PlanningObject[] pos = loader.getAll();

            BlockPlacer placer = new BlockPlacer(sender.getEntityWorld(), sender.getPosition());
			placer.renderCalendar(pos);
            placer.render(pos);
        }
        catch (Exception ex) {
            sender.addChatMessage(new ChatComponentText(ex.getMessage()));
        }
	}
}
