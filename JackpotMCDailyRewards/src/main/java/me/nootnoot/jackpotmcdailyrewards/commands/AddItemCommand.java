package me.nootnoot.jackpotmcdailyrewards.commands;

import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmcdailyrewards.JackpotMCDailyRewards;
import me.nootnoot.jackpotmcdailyrewards.entities.SimpleItem;
import me.nootnoot.jackpotmcdailyrewards.menus.ItemRemoveMenu;
import me.nootnoot.jackpotmcdailyrewards.menus.SpinMenu;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class AddItemCommand implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player p)) return true;
		if(args.length == 0){
			if(!(p.hasPermission("jackpotmcdailyrewards.bypass"))) {
				if (JackpotMCDailyRewards.getInstance().getCooldownStorage().getCooldowns() != null) {
					if (JackpotMCDailyRewards.getInstance().getCooldownStorage().getRewardCooldown(p.getUniqueId().toString()) != null) {
						p.sendMessage(Utils.c("&c&l(!)&c You are still on cooldown for " + JackpotMCDailyRewards.getInstance().getRewardsManager().convertSecs(JackpotMCDailyRewards.getInstance().getCooldownStorage().getCooldown(p.getUniqueId().toString()))));
						return true;
					}
				}
			}
			JackpotMCDailyRewards.getInstance().getMenuManager().openInterface(p, new SpinMenu(true, new HashMap<>()));
		}else if (args.length == 1){
			if (!(p.hasPermission("jackpotmcdailyrewards.admin"))) {
				p.sendMessage(Utils.c("&fUnknown command. Type \"/help\" for help."));
				return true;
			}
			if (args[0].equalsIgnoreCase("add")) {
				if (p.getInventory().getItemInMainHand().getType() == Material.AIR) {
					p.sendMessage(Utils.c("&c&l(!)&c You cannot add AIR to the rewards!"));
					return true;
				}
				JackpotMCDailyRewards.getInstance().getRewardsManager().addItem(new SimpleItem(p.getInventory().getItemInMainHand()));
				p.sendMessage(Utils.c("&a&l(!)&a Successfully added item to rewards!"));
				return true;
			} else if (args[0].equalsIgnoreCase("remove")) {
				JackpotMCDailyRewards.getInstance().getMenuManager().openInterface(p, new ItemRemoveMenu());
			}
		}
		else {
			p.sendMessage(Utils.c("&c&l(!)&c /dailyrewards [add/remove]"));
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1) return List.of("add", "remove");
		return null;
	}
}
