package me.nootnoot.luckyblockfactions.commands;

import me.nootnoot.framework.commandsystem.CommandManager;
import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class FactionCommand extends CommandManager {

	public FactionCommand(){
		getCommands().add(new FactionChatCommand());
		getCommands().add(new FactionCloseCommand());
		getCommands().add(new FactionCreateCommand());
		getCommands().add(new FactionDemoteCommand());
		getCommands().add(new FactionDepositCommand());
		getCommands().add(new FactionDescriptionCommand());
		getCommands().add(new FactionDisbandCommand());
		getCommands().add(new FactionInfoCommand());
		getCommands().add(new FactionInviteCommand());
		getCommands().add(new FactionJoinCommand());
		getCommands().add(new FactionKickCommand());
		getCommands().add(new FactionLeaveCommand());
		getCommands().add(new FactionListCommand());
		getCommands().add(new FactionOpenCommand());
		getCommands().add(new FactionPromoteCommand());
		getCommands().add(new FactionRevokeCommand());
		getCommands().add(new FactionTopCommand());
		getCommands().add(new FactionTransferCommand());
		getCommands().add(new FactionWithdrawCommand());
		getCommands().add(new FactionClaimCommand());
		getCommands().add(new FactionUnclaimAllCommand());
		getCommands().add(new FactionUnclaimCommand());
		getCommands().add(new FactionBalanceCommand());
		getCommands().add(new FactionHomeCommand());
		getCommands().add(new FactionSetHomeCommand());
		getCommands().add(new FactionDelHomeCommand());
		getCommands().add(new FactionUpgradeCommand());
		getCommands().add(new FactionWhoCommand());
	}

	@Override
	public boolean onCommand(CommandSender p, Command command, String label, String[] args) {
		if(args.length == 1 && args[0].equalsIgnoreCase("help")){
			p.sendMessage(Utils.c("&4&l[!] &c&lFaction Help &4&l[!]"));
			for (SubCommand s : getCommands()) {
				p.sendMessage(Utils.c(s.getSyntax() + " " + s.getDescription()));
			}
		}
		else if(args.length >= 1) {
			for (int i = 0; i < getCommands().size(); i++) {
				SubCommand s = getCommands().get(i);
				if (s.getName().equalsIgnoreCase(args[0])) {
					if (!(p.hasPermission(s.getPermission())) && !(s.getPermission().equalsIgnoreCase(""))) {
						p.sendMessage(Utils.c("&fUnknown command. Type \"/help\" for help."));
						return true;
					}
					if (args.length - 1 < s.getNeededArgs()) {
						p.sendMessage(Utils.c("&c&l(!)&c Not enough arguments."));
						return true;
					}
					if (p instanceof Player) {
						s.execute((Player) p, Arrays.copyOfRange(args, 1, args.length));
					} else {
						s.executeConsole((ConsoleCommandSender) p, Arrays.copyOfRange(args, 1, args.length));
					}
				}
			}
		}
		return true;
	}
}
