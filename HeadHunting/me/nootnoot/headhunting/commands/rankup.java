package me.nootnoot.headhunting.commands;

import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.entities.HHPlayer;
import me.nootnoot.headhunting.entities.Level;
import me.nootnoot.headhunting.managers.HHPlayerManager;
import me.nootnoot.headhunting.managers.LevelManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class rankup implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player)) return true;


		Player p = (Player) sender;
		HHPlayer hhP = HHPlayerManager.getInstance().getHHPlayer(p);
		Level level = LevelManager.getInstance().byInt(hhP.getLevel());

		int currentHeads = hhP.getSoldHeads();
		int neededHeads = level.getRequiredHeads();

		StringBuilder sb = new StringBuilder();
		int i = level.getAcceptedEntities().size() - 1;
		for(String s : level.getAcceptedEntities()){
			if(i-- == 0){
				sb.append(s);
			}else{
				sb.append(s);
				sb.append(", ");
			}
		}

		if(currentHeads < neededHeads){
			p.sendMessage("");
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadHunting.getInstance().getConfig().getString("messages.rankup.unsuccessfulrankup")));
		}else {
			if (!(HeadHunting.getInstance().getEcon().getBalance(p) >= level.getUpgradeCost())) {
				for(String s : HeadHunting.getInstance().getConfig().getStringList("messages.rankup.notenoughmoney")){
					if(s.contains("%amount%"))
						s = s.replace("%amount%", String.valueOf(level.getUpgradeCost()));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
				}
				return true;
			}
			if(hhP.getLevel() + 1 == 16){
				p.sendMessage(HeadHunting.getInstance().getConfig().getString("messages.rankup.maxlevel"));
				return true;
			}
				HeadHunting.getInstance().getEcon().withdrawPlayer(p, level.getUpgradeCost());
				hhP.setLevel(hhP.getLevel() + 1);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);

				Level levelAfterRankup = LevelManager.getInstance().byInt(hhP.getLevel());

				hhP.setSoldHeads(0);
				p.setExp(0);
				p.setLevel(p.getLevel() + 1);
				int currentHeadsAfter = hhP.getSoldHeads();
				int neededHeadsAfter = level.getRequiredHeads();

				StringBuilder sbAfter = new StringBuilder();

				int i2 = level.getAcceptedEntities().size() - 1;
				for (String s : level.getAcceptedEntities()) {
					if (i2-- == 0) {
						sbAfter.append(s);
					} else {
						sbAfter.append(s);
						sbAfter.append(", ");
					}
				}

				for(String s : level.getRewards()){
					s = s.replace("%player%", p.getName());
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
				}

				p.sendMessage(ChatColor.RED + "-------------------------");
				p.sendMessage(ChatColor.RED + "Current level: " + levelAfterRankup.getLevel());
				p.sendMessage(ChatColor.RED + "Current entities: " + sbAfter);
				p.sendMessage(ChatColor.RED + "Current heads sold: " + currentHeadsAfter);
				p.sendMessage(ChatColor.RED + "Required heads sold: " + neededHeadsAfter);
				p.sendMessage(ChatColor.RED + "-------------------------");
				p.sendMessage("");
				p.sendMessage(ChatColor.translateAlternateColorCodes('&',
						HeadHunting.getInstance().getConfig().getString("messages.rankup.successfulrankup")));
			}

		return true;
	}
}
