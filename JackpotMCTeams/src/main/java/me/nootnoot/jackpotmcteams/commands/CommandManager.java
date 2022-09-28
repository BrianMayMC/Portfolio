package me.nootnoot.jackpotmcteams.commands;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.commands.*;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements TabExecutor {

	private final ArrayList<SubCommand> commands = new ArrayList<>();

	public CommandManager(){
		commands.add(new TeamAcceptCommand());
		if(JackpotMCTeams.getInstance().isBalance()){
			commands.add(new TeamBalanceCommand());
			commands.add(new TeamDepositCommand());
			commands.add(new TeamWithdrawCommand());
		}
		if(JackpotMCTeams.getInstance().isHomes()){
			commands.add(new TeamDeleteHomeCommand());
			commands.add(new TeamSetHomeCommand());
			commands.add(new TeamHomeCommand());
		}
		commands.add(new TeamChatCommand());
		commands.add(new TeamCreateCommand());
		commands.add(new TeamDemoteCommand());
		commands.add(new TeamCloseCommand());
		commands.add(new TeamDescriptionCommand());
		commands.add(new TeamDisbandCommand());
		commands.add(new TeamHelpCommand());
		commands.add(new TeamInviteCommand());
		commands.add(new TeamJoinCommand());
		commands.add(new TeamKickCommand());
		commands.add(new TeamLeaveCommand());
		commands.add(new TeamListCommand());
		commands.add(new TeamMakeLeaderCommand());
		commands.add(new TeamOpenCommand());
		commands.add(new TeamPromoteCommand());
		commands.add(new TeamTopCommand());
		commands.add(new TeamWhoCommand());
		commands.add(new TeamInfoCommand());
	}

	public ArrayList<String> GetHelpByPage(int page){
		ArrayList<String> message = new ArrayList<>();

		message.add(Utils.c("&e=== &bShowing help for &a/teams &e==="));
		for(int i = (page * 10); i < commands.size(); i++){
			if(message.size() == 10){
				return message;
			}
			message.add(Utils.c("&6" + commands.get(i).GetSyntax() + "&f&l | &7" + commands.get(i).GetDescription()));
		}
		return message;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player p){
			if(args.length == 0){
				TeamHelpCommand helpCommand = new TeamHelpCommand();
				helpCommand.execute(p, args);
			}else{
				for (SubCommand subCommand : commands) {
					if (args[0].equalsIgnoreCase(subCommand.getName())) {
						subCommand.execute(p, args);
					}
				}
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1){
			ArrayList<String> names = new ArrayList<>();
			for (SubCommand subCommand : commands) {
				names.add(subCommand.getName());
			}
			return names;
		}else if(args.length > 1){
			for (SubCommand subCommand : commands) {
				if (args[0].equalsIgnoreCase(subCommand.getName())) {
					return subCommand.GetTabCompletion((Player) sender, args);
				}
			}
		}
		return null;
	}
}
