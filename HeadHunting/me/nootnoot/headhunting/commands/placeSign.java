package me.nootnoot.headhunting.commands;

import me.nootnoot.headhunting.HeadHunting;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;


public class placeSign implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player)) return true;

		if(!sender.hasPermission("headhunting.placesign")) return true;

		Player p = (Player) sender;

		if(args.length != 1){
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Incorrect command."));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCorrect command: /placesign <name>"));
			return true;
		}

		String name = args[0];

		p.getWorld().getBlockAt(p.getLocation()).setType(Material.WALL_SIGN);
		p.getWorld().getBlockAt(p.getLocation()).setMetadata("sign", new FixedMetadataValue(HeadHunting.getInstance(), true));


		Sign sign = (Sign) p.getWorld().getBlockAt(p.getLocation()).getState();

		for(int i = 0; i < HeadHunting.getInstance().getConfig().getStringList("messages.signs.lines.").size(); i++){
			String current = HeadHunting.getInstance().getConfig().getStringList("messages.signs.lines.").get(i);
			if(current.contains("%name%")){
				current = current.replace("%player%", name);
			}
			sign.setLine(i, ChatColor.translateAlternateColorCodes('&', current));
		}
		sign.update();

		return true;
	}
}
