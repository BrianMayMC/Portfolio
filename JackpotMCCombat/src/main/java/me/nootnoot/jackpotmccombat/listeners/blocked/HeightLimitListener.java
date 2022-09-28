package me.nootnoot.jackpotmccombat.listeners.blocked;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class HeightLimitListener implements Listener {

	@EventHandler
	public void build(BlockPlaceEvent e){
		if(e.getBlock().getY() > 200){
			if(e.getBlock().getType() != Material.BEACON){
				e.setCancelled(true);
			}
		}
	}

}
