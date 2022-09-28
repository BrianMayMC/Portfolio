package me.nootnoot.luckyblockbackpacks.listeners;

import jdk.jfr.Enabled;
import me.nootnoot.luckyblockbackpacks.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class BackpackDie implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		ItemStack backpack = null;
		for(ItemStack item : e.getDrops()){
			if(item.hasItemMeta()){
				if(item.getItemMeta().getPersistentDataContainer().has(Utils.key, PersistentDataType.STRING)){
					backpack = item;
				}
			}
		}
		if(backpack != null){
			e.getDrops().remove(backpack);
		}
	}
}
