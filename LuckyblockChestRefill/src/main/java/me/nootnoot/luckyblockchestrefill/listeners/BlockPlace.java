package me.nootnoot.luckyblockchestrefill.listeners;

import me.nootnoot.luckyblockchestrefill.LuckyblockChestRefill;
import me.nootnoot.luckyblockchestrefill.entities.RefillChest;
import me.nootnoot.luckyblockchestrefill.entities.SimpleLocation;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockPlace implements Listener {

	@EventHandler
	public void place(BlockPlaceEvent e){
		if(!(LuckyblockChestRefill.getInstance().getChestManager().getPlayersInMode().containsKey(e.getPlayer()))) return;

		LuckyblockChestRefill.getInstance().getChestManager().addChest(
				new RefillChest(new SimpleLocation(e.getBlock().getLocation()), LuckyblockChestRefill.getInstance().getChestManager().getPlayersInMode().get(e.getPlayer())));

		e.getBlock().setType(Material.CHEST);
		e.getBlock().setMetadata("refillchest", new FixedMetadataValue(LuckyblockChestRefill.getInstance(), true));

	}

	@EventHandler
	public void mine(BlockBreakEvent e){
		if(e.getBlock().hasMetadata("refillchest")){
			e.setCancelled(true);
		}
	}
}
