package me.nootnoot.luckyblockbackpacks.commands;

import me.nootnoot.luckyblockbackpacks.LuckyblockBackpacks;
import me.nootnoot.luckyblockbackpacks.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class BackpackCommand implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player)) return true;
		Player p = (Player) sender;
		if(args.length == 0){
			LuckyblockBackpacks.getInstance().getBackpackManager().openBackpack(p, p);
			return true;
		}
		if(args.length == 1 && args[0].equalsIgnoreCase("give")) {
			ItemStack backpack = Utils.getBackpackItem(p);
			if (p.getInventory().firstEmpty() == -1) {
				p.sendMessage(Utils.c("&c&l(!)&c Your inventory is full!"));
				return true;
			}
			for(ItemStack item : p.getInventory().getContents()){
				if(item != null) {
					if (item.hasItemMeta()) {
						if (item.getItemMeta().getPersistentDataContainer().has(Utils.key, PersistentDataType.STRING)) {
							p.sendMessage(Utils.c("&c&l(!)&c You already have your backpack in your inventory!"));
							return true;
						}
					}
				}
			}
			p.getInventory().addItem(backpack);
			p.sendMessage(Utils.c("&a&l(!)&a Successfully added your backpack to your inventory!"));
			return true;
		}else if(args.length == 2 && args[0].equalsIgnoreCase("open")){
			Player target = Bukkit.getPlayer(args[1]);
			if(target == null){
				p.sendMessage(Utils.c("&c&l(!)&c Invalid player."));
				return true;
			}
			LuckyblockBackpacks.getInstance().getBackpackManager().openBackpack(p, target);
			return true;
		}
		p.sendMessage(Utils.c("&c&l(!)&c Incorrect command. Do /backpack give"));
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(sender.hasPermission("luckyblockbackpacks.admin")){
			if(args.length == 1) {
				return List.of("give", "open");
			}
		}
		if(args.length == 1){
			return List.of("give");
		}
		return null;
	}
}
