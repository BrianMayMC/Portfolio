package me.nootnoot.jackpotmcplaytimerewards.commands;

import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmcplaytimerewards.JackpotMCPlaytimeRewards;
import me.nootnoot.jackpotmcplaytimerewards.menus.LevelMenu;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlaytimeRewardsCommand implements TabExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if(!(sender instanceof Player p)) return true;
		if(args.length == 0){
			p.sendMessage(Utils.c("&6&lPlaytime: &e" + Utils.formatPlaytime(p.getPersistentDataContainer().getOrDefault(new NamespacedKey(JackpotMCPlaytimeRewards.getInstance(), "playtime"), PersistentDataType.LONG, 0L))));
			return true;
		}
		else if(args.length == 1){
			if(args[0].equalsIgnoreCase("open")) {
				LevelMenu.setPage(1);
				JackpotMCPlaytimeRewards.getInstance().getMenuManager().openInterface(p, new LevelMenu());
			}
		}else if(args.length == 3){
			if(p.hasPermission("jackpotmcplaytimerewards.admin")) {
				if (args[0].equalsIgnoreCase("reset")) {
					Player target = Bukkit.getPlayer(args[1]);
					if (target == null) {
						p.sendMessage(Utils.c("&C&l(!)&c Invalid player."));
						return true;
					}
					int levelTo;
					try {
						levelTo = Integer.parseInt(args[2]);
					} catch (NumberFormatException e) {
						p.sendMessage(Utils.c("&C&l(!)&c Invalid level."));
						return true;
					}
					target.getPersistentDataContainer().set(new NamespacedKey(JackpotMCPlaytimeRewards.getInstance(), "level"), PersistentDataType.INTEGER, levelTo);
					p.sendMessage(Utils.c("&a&l(!)&a Successfully reset " + target.getName() + "'s playtime level to " + levelTo + "."));
				}
			}
		}
		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(sender.hasPermission("jackpotmcplaytimerewards.admin")){
			if(args.length == 1){
				return List.of("open", "reset");
			}else if(args.length == 3){
				return List.of("[level]");
			}
		}
		else if(args.length == 1) return List.of("open");
		return null;
	}
}
