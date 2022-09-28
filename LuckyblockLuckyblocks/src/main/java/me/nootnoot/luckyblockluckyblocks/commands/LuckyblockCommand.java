package me.nootnoot.luckyblockluckyblocks.commands;

import me.nootnoot.luckyblockluckyblocks.LuckyblockLuckyblocks;
import me.nootnoot.luckyblockluckyblocks.entities.LuckyblockItem;
import me.nootnoot.luckyblockluckyblocks.enums.Rarity;
import me.nootnoot.luckyblockluckyblocks.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LuckyblockCommand implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player p)) return true;
		if(!(p.hasPermission("luckyblock.admin"))) return true;

		if(args.length != 3 && !(args[0].equalsIgnoreCase("add"))){
			p.sendMessage(Utils.c("&c&l(!)&c /luckyblock add [rarity] [chance]"));
			return true;
		}

		double chance;
		Rarity rarity;

		try{
			chance = Double.parseDouble(args[2]);
			rarity = Rarity.get(args[1]);
		}
		catch(NumberFormatException e){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid arguments."));
			return true;
		}
		if(rarity == null){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid rarity."));
			return true;
		}

		ItemStack item = p.getInventory().getItemInMainHand();
		if(item.getType() == Material.AIR){
			p.sendMessage(Utils.c("&c&l(!)&c Item cannot be air."));
			return true;
		}

		LuckyblockLuckyblocks.getInstance().getManager().addItem(rarity, new LuckyblockItem(item, chance));
		p.sendMessage(Utils.c("&a&l(!)&a Successfully added item to the list."));
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender.hasPermission("luckyblock.admin"))) return null;
		if(args.length == 1) return List.of("add");
		if(args.length == 2) return List.of("common", "uncommon", "rare");
		if(args.length == 3) return List.of("[chance]");
		return null;
	}
}
