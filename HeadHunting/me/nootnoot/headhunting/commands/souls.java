package me.nootnoot.headhunting.commands;

import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.entities.HHPlayer;
import me.nootnoot.headhunting.managers.HHPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class souls implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player)) return true;

		Player p = (Player) sender;
		HHPlayer hhP = HHPlayerManager.getInstance().getHHPlayer(p);

		if(args.length > 1){
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Incorrect command."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCorrect command: /souls <ign> or /souls"));
			return true;
		}else if(args.length == 0){
			String message = HeadHunting.getInstance().getConfig().getString("messages.souls.notargetmessage");
			if(message.contains("%amount%"))
				message = message.replace("%amount%", String.valueOf(hhP.getSouls()));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
			return true;
		}else{
			Player target = Bukkit.getPlayer(args[0]);

			if(target == null){
				p.sendMessage(ChatColor.RED + "&c&l(!)&c Invalid player.");
				return true;
			}
			HHPlayer hhTarget = HHPlayerManager.getInstance().getHHPlayer(target);
			String message = HeadHunting.getInstance().getConfig().getString("messages.souls.targetmessage");
			if(message.contains("%name%"))
				message = message.replace("%name%", hhTarget.getName());
			if(message.contains("%amount%"))
				message = message.replace("%amount%", String.valueOf(hhTarget.getSouls()));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
		}
		return true;
	}
}
