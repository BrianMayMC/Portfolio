package me.nootnoot.ventedmasks.commands;

import me.nootnoot.ventedmasks.entities.UpgradeItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GiveUpgrade implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = null;
		ConsoleCommandSender console = null;
		if(sender instanceof Player){
			p = (Player) sender;
			if(!p.hasPermission("masks.giveupgrade")) return true;

			if (args.length != 2) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Incorrect command."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCorrect command: /giveupgrade <ign> <amount>"));
				return true;
			}

		}else if(sender instanceof ConsoleCommandSender){
			console = (ConsoleCommandSender) sender;

			if (args.length != 2) {
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Incorrect command."));
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCorrect command: /giveupgrade <ign> <amount>"));
				return true;
			}
		}

		Player target = Bukkit.getPlayer(args[0]);

		if(target == null){
			if(p != null){
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Invalid player."));
			}else if(console != null){
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Invalid player."));
			}
		}

		UpgradeItem.giveItem(target, Integer.parseInt(args[1]));




		return true;
	}
}
