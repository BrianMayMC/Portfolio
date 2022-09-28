package me.nootnoot.luckyblockbackpacks.listeners;

import me.nootnoot.luckyblockbackpacks.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class BackpackPlace implements Listener {

	@EventHandler
	public void onBlockPlace(PlayerInteractEntityEvent e){
		if(!(e.getRightClicked() instanceof ItemFrame)) return;
		if(!(e.getPlayer().getInventory().getItemInMainHand().hasItemMeta())) return;
		if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(Utils.key, PersistentDataType.STRING)){
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onAnvil(PrepareAnvilEvent e){
		for(ItemStack item : e.getInventory().getContents()){
			if(item != null){
				if(item.hasItemMeta()){
					if(item.getItemMeta().getPersistentDataContainer().has(Utils.key, PersistentDataType.STRING)){
						e.setResult(new ItemStack(Material.AIR));
					}
				}
			}
		}
	}

	@EventHandler
	public void chest(InventoryClickEvent e){



	}

}
