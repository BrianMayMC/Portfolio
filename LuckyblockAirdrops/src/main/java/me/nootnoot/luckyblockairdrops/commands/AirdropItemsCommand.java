package me.nootnoot.luckyblockairdrops.commands;

import me.nootnoot.luckyblockairdrops.LuckyblockAirdrops;
import me.nootnoot.luckyblockairdrops.enums.Rarity;
import me.nootnoot.luckyblockairdrops.menus.CommonMenu;
import me.nootnoot.luckyblockairdrops.menus.RareMenu;
import me.nootnoot.luckyblockairdrops.menus.UncommonMenu;
import me.nootnoot.luckyblockairdrops.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class AirdropItemsCommand implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player p)) return true;
		if(!(p.hasPermission("airdrops.admin"))){
			p.sendMessage(Utils.c("&fUnknown command. Type \"/help\" for help."));
			return true;
		}

		if(args.length != 1){
			p.sendMessage(Utils.c("&c&l(!)&c /airdropitems [rarity]"));
			return true;
		}

		String rarityString = args[0];
		if(rarityString.equalsIgnoreCase("common")) {
			LuckyblockAirdrops.getInstance().getMenuManager().openInterface(p, new CommonMenu());
			return true;
		}
		else if(rarityString.equalsIgnoreCase("uncommon")){
			LuckyblockAirdrops.getInstance().getMenuManager().openInterface(p, new UncommonMenu());
		}
		else{
			LuckyblockAirdrops.getInstance().getMenuManager().openInterface(p, new RareMenu());
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender.hasPermission("airdrops.admin"))) return null;
		if(args.length == 1){
			return List.of("common", "uncommon", "rare");
		}
		return null;
	}
}
