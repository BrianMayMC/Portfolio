package me.nootnoot.luckyblockchestrefill.commands;

import me.nootnoot.luckyblockchestrefill.LuckyblockChestRefill;
import me.nootnoot.luckyblockchestrefill.entities.RefillChest;
import me.nootnoot.luckyblockchestrefill.entities.SimpleLocation;
import me.nootnoot.luckyblockchestrefill.tasks.RespawnTask;
import me.nootnoot.luckyblockchestrefill.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;
import java.util.Set;

public class RefillChestItemsCommand implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player p)) return true;
		if(!(p.hasPermission("refillchests.admin"))){
			p.sendMessage(Utils.c("&fUnknown command. Type \"/help\" for help."));
			return true;
		}
		if(args.length < 1 || args.length > 3){
			p.sendMessage(Utils.c("&c&l(!)&c /refillchests additem/removeitem/enable/place [type] [chance]"));
			return true;
		}
		double chance = 0;
		if(args.length == 3){
			try{
				chance = Double.parseDouble(args[2]);
			}
			catch(NumberFormatException e){
				p.sendMessage(Utils.c("&c&l(!)&c Invalid chance."));
				return true;
			}
		}
		if(args[0].equalsIgnoreCase("additem") && args.length == 3) {
			LuckyblockChestRefill.getInstance().getChestManager().addItem(p.getInventory().getItemInMainHand(), args[1], chance);
			p.sendMessage(Utils.c("&a&l(!)&a Successfully added item to chest type!"));
			p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
		}else if(args[0].equalsIgnoreCase("remove") && args.length == 1){
			Location loc = p.getTargetBlock(Set.of(Material.AIR), 15).getLocation();
			if(!loc.getBlock().hasMetadata("refillchest")){
				p.sendMessage(Utils.c("&c&l(!)&c This is not a refill chest."));
				return true;
			}else{
				p.sendMessage(Utils.c("&a&l(!)&a Successfully removed the chest at this location."));
				LuckyblockChestRefill.getInstance().getChestManager().removeChest(loc);
			}
		}else if(args[0].equalsIgnoreCase("removetype") && args.length == 2){
			LuckyblockChestRefill.getInstance().getChestManager().deleteType(args[1]);
			p.sendMessage(Utils.c("&a&l(!)&a Successfully deleted the type \"" + args[1] + "\" and it's corresponding items."));
		}else if(args[0].equalsIgnoreCase("enable") && args.length == 2){
			if(LuckyblockChestRefill.getInstance().getChestManager().getPlayersInMode().containsKey(p)){
				LuckyblockChestRefill.getInstance().getChestManager().getPlayersInMode().remove(p);
				p.sendMessage(Utils.c("&a&l(!)&a Successfully left the place mode!"));
				p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
			}else{
				LuckyblockChestRefill.getInstance().getChestManager().getPlayersInMode().put(p, args[1]);
				p.sendMessage(Utils.c("&a&l(!)&a Successfully joined the place mode!"));
				p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
			}
		}else if(args[0].equalsIgnoreCase("place") && args.length == 2){
			Location loc = p.getTargetBlock(Set.of(Material.AIR), 15).getLocation();
			LuckyblockChestRefill.getInstance().getChestManager().addChest(new RefillChest(new SimpleLocation(loc), args[1]));
			loc.getBlock().setType(Material.CHEST);
			loc.getBlock().setMetadata("refillchest", new FixedMetadataValue(LuckyblockChestRefill.getInstance(), true));
			p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
		}
		else if(args[0].equalsIgnoreCase("reset") && args.length == 1){
			p.sendMessage(Utils.c("&a&l(!)&a Successfully reset the items in chests."));
			new RespawnTask().run();
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("refillchests.admin")) return null;
		if(args.length == 1) return List.of("enable", "additem", "place", "remove");
		if(args.length == 2) return List.of("[type]");
		if(args.length == 3) return List.of("[chance]");
		return null;
	}
}
