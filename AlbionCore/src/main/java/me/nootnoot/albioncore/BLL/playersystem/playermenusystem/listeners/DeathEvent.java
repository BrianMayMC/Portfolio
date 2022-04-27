package me.nootnoot.albioncore.BLL.playersystem.playermenusystem.listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.albioncore.BLL.playersystem.playermenusystem.PlayerMenuItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class DeathEvent implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		for(ItemStack drop : e.getDrops()){
			NBTItem item = new NBTItem(drop);
			if(item.hasKey("playerMenu")){
				drop.setType(Material.AIR);
			}
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		e.getPlayer().getInventory().setItem(8, new PlayerMenuItem().CreateMainItem());
	}
}
