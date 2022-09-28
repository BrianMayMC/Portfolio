package me.nootnoot.jackpotmcteams.commands;

import me.nootnoot.jackpotmcteams.commands.admincommands.*;
import me.nootnoot.jackpotmcteams.commands.commands.TeamHelpCommand;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminCommandManager implements TabExecutor {

	private final ArrayList<SubCommand> adminCommands = new ArrayList<>();

	public AdminCommandManager(){
		adminCommands.add(new AdminAddKillsCommand());
		adminCommands.add(new AdminForceJoinCommand());
		adminCommands.add(new AdminForceLeaveCommand());
		adminCommands.add(new AdminRemoveKillsCommand());
		adminCommands.add(new AdminSetKillsCommand());
		adminCommands.add(new AdminReloadCommand());
		adminCommands.add(new AdminHelpCommand());
		adminCommands.add(new AdminForceDisbandCommand());
	}


	public ArrayList<String> GetHelpByPage(int page){
		ArrayList<String> message = new ArrayList<>();

		message.add(Utils.c("&e=== &bShowing help for &a/teamsadmin &e==="));
		for(int i = (page * 10); i < adminCommands.size(); i++){
			if(message.size() == 10){
				return message;
			}
			message.add(Utils.c("&6" + adminCommands.get(i).GetSyntax() + "&f&l | &7" + adminCommands.get(i).GetDescription()));
		}
		return message;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player p){
			if(!p.hasPermission("jackpotmcteams.admin")){
				p.sendMessage(Utils.c("&fUnknown command. Type \"/help\" for help."));
				return true;
			}
			if(args.length == 0){
				AdminHelpCommand helpCommand = new AdminHelpCommand();
				helpCommand.execute(p, args);
			}else{
				for (SubCommand adminCommand : adminCommands) {
					if (args[0].equalsIgnoreCase(adminCommand.getName())) {
						adminCommand.execute(p, args);
					}
				}
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		if(!p.hasPermission("jackpotmcteams.admin")){
			return null;
		}
		if(args.length == 1){
			ArrayList<String> names = new ArrayList<>();
			for (SubCommand subCommand : adminCommands) {
				names.add(subCommand.getName());
			}
			return names;
		}else if(args.length > 1){
			for (SubCommand subCommand : adminCommands) {
				if (args[0].equalsIgnoreCase(subCommand.getName())) {
					return subCommand.GetTabCompletion((Player) sender, args);
				}
			}
		}
		return null;
	}
}
