package me.nootnoot.luckyblockairdrops.listeners;

import me.nootnoot.luckyblockairdrops.LuckyblockAirdrops;
import me.nootnoot.luckyblockairdrops.entities.Airdrop;
import me.nootnoot.luckyblockairdrops.tasks.RemoveTask;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.function.Predicate;

public class AirdropListeners implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent e){
		if(e.getBlock().hasMetadata("airdrop")){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onEmpty(InventoryClickEvent e){
		if(e.getInventory().getType() != InventoryType.CHEST) return;
		if(e.getInventory().getLocation() == null) return;
		if(e.getCurrentItem() == null) return;
		Chest chest = (Chest)e.getInventory().getLocation().getBlock().getState();
		if(!(chest.hasMetadata("airdrop"))) return;

		int i = 0;
		for(ItemStack item : e.getInventory().getContents()){
			if(item != null){
				if(item.getType() != Material.AIR){
					i++;
				}
			}
		}
		for(Airdrop a : LuckyblockAirdrops.getInstance().getAirdropManager().getAirdrops()){
			if(a.getLocation().equals(e.getInventory().getLocation())) {
				if (i <= 1) {
					new RemoveTask(a).run();
				}
			}
		}
	}
}

