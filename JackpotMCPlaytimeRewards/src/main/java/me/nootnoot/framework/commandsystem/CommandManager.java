package me.nootnoot.framework.commandsystem;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CommandManager implements TabExecutor {

	@Getter
	private final ArrayList<SubCommand> commands = new ArrayList<>();

	@Override
	public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
		if(args.length == 1) {
			List<String> commandNames = new ArrayList<>();
			for (SubCommand s : getCommands()) {
				if (sender.hasPermission(s.getPermission())) {
					commandNames.add(s.getName());
				}
			}
			return commandNames;
		}
		for(int i = 1; i < args.length; i++){
			for (SubCommand s : getCommands()) {
				if (sender.hasPermission(s.getPermission())) {
					if(args[0].equalsIgnoreCase(s.getName())){
						return s.getTabCompletion((Player) sender, Arrays.copyOfRange(args, 1, args.length));
					}
				}
			}
		}
		return null;
	}
}
