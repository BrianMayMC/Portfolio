package me.nootnoot.headhunting.commands;

import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.entities.Booster;
import me.nootnoot.headhunting.managers.BoosterManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BoosterCommand implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player)) return true;

		if(args.length != 3){
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Invalid command."));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/booster give <name> <booster>"));
			return true;
		}

		if(!args[0].equalsIgnoreCase("give")){
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Invalid command."));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/booster give <name> <booster>"));
			return true;
		}
		Player p = (Player) sender;
		Player target = Bukkit.getPlayer(args[1]);
		if(target == null){
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Invalid player."));
			return true;
		}
		Booster booster = BoosterManager.GetInstance().ByConfigname(args[2]);

		if(booster == null){
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					HeadHunting.getInstance().getConfig().getString("messages.boosters.invalidbooster")));
			return true;
		}
		target.getInventory().addItem(booster.getIs());


		return true;
	}
}
