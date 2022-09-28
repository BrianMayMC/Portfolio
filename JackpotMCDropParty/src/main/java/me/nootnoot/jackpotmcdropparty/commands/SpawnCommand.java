package me.nootnoot.jackpotmcdropparty.commands;

import me.nootnoot.jackpotmcdropparty.JackpotMCDropParty;
import me.nootnoot.jackpotmcdropparty.menus.DropPartyMenu;
import me.nootnoot.jackpotmcdropparty.tasks.RemoveTask;
import me.nootnoot.jackpotmcdropparty.tasks.SpawnTask;
import me.nootnoot.jackpotmcdropparty.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SpawnCommand implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player p)) return true;

		if(!p.hasPermission("jackpotmcdropparty.admin")) {
			return true;
		}

		if(args.length < 1 || args.length > 3){
			p.sendMessage(Utils.c("&c&l(!)&c /dropparty start/open [material] [duration]"));
			return true;
		}

		if(!args[0].equalsIgnoreCase("start") && !args[0].equalsIgnoreCase("open") && !args[0].equalsIgnoreCase("reload")){
			p.sendMessage(Utils.c("&c&l(!)&c /dropparty start/open [material] [duration]"));
			return true;
		}
		else if(args[0].equalsIgnoreCase("reload")){
			JackpotMCDropParty.getInstance().reload();
		}
		else if(args[0].equalsIgnoreCase("open")){
			new DropPartyMenu(p).open();
			return true;
		}

		else {

			Material material = Material.matchMaterial(args[1].toUpperCase(Locale.ROOT));

			if (material == null) {
				p.sendMessage(Utils.c("&c&l(!)&c Invalid Material."));
				return true;
			}
			if (!material.isSolid()) {
				p.sendMessage(Utils.c("&c&l(!)&c Invalid Material."));
				return true;
			}

			int duration;
			try {
				duration = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				p.sendMessage(Utils.c("&c&l(!) &cInvalid duration (has to be a number!)"));
				return true;
			}

			Location targetBlockLocation = p.getTargetBlock(Set.of(Material.AIR), 50).getLocation();

			Location finalLocation = targetBlockLocation.add(0.5, 1, 0.5);

			if (finalLocation.getBlock().getType() != Material.AIR) {
				p.sendMessage(Utils.c("&c&l(!)&c Target block has to be air!"));
				return true;
			}

			JackpotMCDropParty.getInstance().getPartyItemManager().getPlacedDropParties().add(finalLocation);

			finalLocation.getBlock().setType(material);

			new RemoveTask(finalLocation).runTaskLater(JackpotMCDropParty.getInstance(), duration * 20L);


			int delay = JackpotMCDropParty.getInstance().getConfig().getInt("item-delay");

			new SpawnTask(finalLocation).runTaskTimer(JackpotMCDropParty.getInstance(), delay, delay);

		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("jackpotmcdropparty.admin")) return null;
		if(args.length == 1){
			return List.of("start", "open", "reload");
		}else if(args.length == 2){
			return List.of("[material]");
		}else if(args.length == 3){
			return List.of("[duration]");
		}
		return null;
	}
}
