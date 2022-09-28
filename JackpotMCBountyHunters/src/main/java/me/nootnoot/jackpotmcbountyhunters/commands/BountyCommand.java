package me.nootnoot.jackpotmcbountyhunters.commands;

import me.nootnoot.framework.commandsystem.CommandManager;
import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmcbountyhunters.JackpotMCBountyHunters;
import me.nootnoot.jackpotmcbountyhunters.commands.subcommands.AdminForceRemoveBountyCommand;
import me.nootnoot.jackpotmcbountyhunters.commands.subcommands.AdminForceSetBountyCommand;
import me.nootnoot.jackpotmcbountyhunters.commands.subcommands.SetBountyCommand;
import me.nootnoot.jackpotmcbountyhunters.commands.subcommands.ViewBountyCommand;
import me.nootnoot.jackpotmcbountyhunters.menus.BountyTopMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BountyCommand extends CommandManager {

	public BountyCommand(){
		getCommands().add(new AdminForceRemoveBountyCommand());
		getCommands().add(new AdminForceSetBountyCommand());
		getCommands().add(new SetBountyCommand());
		getCommands().add(new ViewBountyCommand());
	}

	@Override
	public boolean onCommand(CommandSender p, Command command, String label, String[] args) {
		if(args.length == 1 && args[0].equalsIgnoreCase("help")){
			if(p.hasPermission("jackpotmc.bountyhunters.help")) {
				p.sendMessage(Utils.c("&6&l[!] &e&lBounty Hunters Help &6&l[!]"));
				for (SubCommand s : getCommands()) {
					p.sendMessage(Utils.c(s.getSyntax() + "&7 | " + s.getDescription()));
				}
			}else{
				p.sendMessage(Utils.c("&fUnknown command. Type \"/help\" for help."));
				return true;
			}
		}else if(args.length >= 1){
			for(int i = 0; i < getCommands().size(); i++){
				SubCommand s = getCommands().get(i);
				if(s.getName().equalsIgnoreCase(args[0])){
					if(!(p.hasPermission(s.getPermission()))){
						p.sendMessage(Utils.c("&fUnknown command. Type \"/help\" for help."));
						return true;
					}
					if(args.length - 1 < s.getNeededArgs()){
						p.sendMessage(Utils.c("&c&l(!)&c Not enough arguments."));
						return true;
					}
					if(p instanceof Player) {
						s.execute((Player) p, Arrays.copyOfRange(args, 1, args.length));
					}else{
						s.executeConsole((ConsoleCommandSender) p, Arrays.copyOfRange(args, 1, args.length));
					}
				}
			}
		}else{
			if(p instanceof	Player){
				JackpotMCBountyHunters.getInstance().getMenuManager().openInterface((Player)p, new BountyTopMenu());
			}
		}
		return true;
	}
}
