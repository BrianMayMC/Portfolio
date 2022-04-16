package me.nootnoot.headhunting.commands;

import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.entities.HHPlayer;
import me.nootnoot.headhunting.entities.Level;
import me.nootnoot.headhunting.managers.HHPlayerManager;
import me.nootnoot.headhunting.managers.LevelManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class level implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player)) return true;

		Player p = (Player) sender;
		HHPlayer hhP = HHPlayerManager.getInstance().getHHPlayer(p);
		Level level = LevelManager.getInstance().byInt(hhP.getLevel());

		StringBuilder current = new StringBuilder();
		int i = level.getAcceptedEntities().size() - 1;
		for(String s : level.getAcceptedEntities()){
			if(i-- == 0){
				current.append(s);
			}else{
				current.append(s);
				current.append(", ");
			}
		}

		for(String s : HeadHunting.getInstance().getConfig().getStringList("messages.level")){
			if(s.contains("%currentlevel%"))
				s = s.replace("%currentlevel%", String.valueOf(hhP.getLevel()));
			if(s.contains("%entities%"))
				s = s.replace("%entities%", current);
			if(s.contains("%soldheads%"))
				s = s.replace("%soldheads%", String.valueOf(hhP.getSoldHeads()));
			if(s.contains("%requiredheads%")){
				s = s.replace("%requiredheads%", String.valueOf(level.getRequiredHeads()));
			}
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
		}

		return true;
	}
}
