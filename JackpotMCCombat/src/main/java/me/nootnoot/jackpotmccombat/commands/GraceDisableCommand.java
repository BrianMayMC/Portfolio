package me.nootnoot.jackpotmccombat.commands;

import me.nootnoot.jackpotmccombat.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class GraceDisableCommand implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player p)) return true;

		if(args.length != 1){
			p.sendMessage(Utils.c("&c&l(!)&c /pvp off"));
			return true;
		}
		if(!args[0].equalsIgnoreCase("off")){
			p.sendMessage(Utils.c("&c&l(!)&c /pvp off"));
			return true;
		}

		PersistentDataContainer container = p.getPersistentDataContainer();
		if(!container.has(Utils.key, PersistentDataType.INTEGER)){
			p.sendMessage(Utils.c("&c&l(!)&c Your grace has already been disabled!"));
			return true;
		}

		container.remove(Utils.key);
		p.sendMessage(Utils.c("&a&l(!)&a Your grace period has been disabled!"));
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1){
			return List.of("off");
		}
		return null;
	}
}
