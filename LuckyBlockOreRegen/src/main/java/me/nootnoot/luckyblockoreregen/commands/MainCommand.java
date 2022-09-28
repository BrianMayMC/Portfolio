package me.nootnoot.luckyblockoreregen.commands;

import de.tr7zw.nbtapi.NBTBlock;
import me.nootnoot.luckyblockoreregen.LuckyBlockOreRegen;
import me.nootnoot.luckyblockoreregen.entities.RegenBlock;
import me.nootnoot.luckyblockoreregen.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(sender instanceof Player p){

			if(args[0].equalsIgnoreCase("set")){
				if(args.length == 2){
					Material material = Material.matchMaterial(args[1].toUpperCase(Locale.ROOT));
					if(material == null){
						p.sendMessage(Utils.c("&c&l(!)&c Material does not exist."));
						return true;
					}
					if(!material.isSolid()){
						p.sendMessage(Utils.c("&c&l(!)&c The block has to be SOLID!"));
						return true;
					}
					Location location = p.getTargetBlock(Set.of(Material.AIR), 20).getLocation();
					LuckyBlockOreRegen.getInstance().getRegenBlockStorage().createBlock(material, location);
					location.getBlock().setType(material);
					NBTBlock block = new NBTBlock(location.getBlock());
					block.getData().setString("customblock", "yes");
				}
			}else if(args[0].equalsIgnoreCase("delete")){
				Location location = p.getTargetBlock(Set.of(Material.AIR), 20).getLocation();
				for(RegenBlock regenBlock : LuckyBlockOreRegen.getInstance().getRegenBlockStorage().getBlocks().values()){
					if(regenBlock.getLocation().equals(location)){
						LuckyBlockOreRegen.getInstance().getRegenBlockStorage().removeBlock(regenBlock);
						return true;
					}
				}
				p.sendMessage(Utils.c("&c&l(!)&c Target block is not a custom block."));

			}
			else if(args[0].equalsIgnoreCase("toggle")){
				if(!LuckyBlockOreRegen.getInstance().getRegenBlockManager().getPlayersInMode().contains(p)){
					LuckyBlockOreRegen.getInstance().getRegenBlockManager().getPlayersInMode().add(p);
					p.sendMessage(Utils.c("&a&l(!)&a You've entered the place mode!"));
				}else{
					LuckyBlockOreRegen.getInstance().getRegenBlockManager().getPlayersInMode().remove(p);
					p.sendMessage(Utils.c("&a&l(!)&a You've left the place mode!"));
				}
			}else{
				p.sendMessage(Utils.c("&c&l(!)&c /customblock [toggle/set] <material>"));
				return true;
			}
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1){
			return List.of("set", "toggle");
		}else if(args.length == 2){
			return List.of("[material]");
		}
		return null;
	}
}
