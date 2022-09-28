package me.nootnoot.luckyblockairdrops.commands;

import lombok.extern.jackson.Jacksonized;
import me.nootnoot.luckyblockairdrops.LuckyblockAirdrops;
import me.nootnoot.luckyblockairdrops.entities.Airdrop;
import me.nootnoot.luckyblockairdrops.enums.Rarity;
import me.nootnoot.luckyblockairdrops.tasks.SpawnTask;
import me.nootnoot.luckyblockairdrops.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class AirdropCommand implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player p)) return true;

		if(!(p.hasPermission("airdrop.admin"))){
			p.sendMessage(Utils.c("&fUnknown command. Type \"help\" for help."));
			return true;
		}

		if(args.length != 2 || !args[0].equalsIgnoreCase("spawn")){
			p.sendMessage(Utils.c("&c&l(!)&c /airdrop spawn [rarity]"));
			return true;
		}

		String rarityString = args[1];
		Rarity rarity;
		if(rarityString.equalsIgnoreCase("common")) rarity = Rarity.COMMON;
		else if(rarityString.equalsIgnoreCase("uncommon")) rarity = Rarity.UNCOMMON;
		else rarity = Rarity.RARE;

		new SpawnTask(rarity, 1).run();
		LuckyblockAirdrops.getInstance().getServer().broadcastMessage(Utils.c("&6&l(!)&e Airdrops have now spawned around the earth. Go to https://maps.luckyblock.gg to view their location!"));
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1) return List.of("spawn");
		if(args.length == 2) return List.of("common", "uncommon", "rare");
		return null;
	}
}
