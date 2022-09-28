package me.nootnoot.jackpotmcitems.listeners;

import com.destroystokyo.paper.event.player.PlayerRecipeBookClickEvent;
import me.nootnoot.jackpotmcitems.JackpotMCItems;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class CraftListener implements Listener {


	@EventHandler
	public void onCraft(CraftItemEvent e){
		if(e.getInventory().getMatrix().length == 0) return;

		for(ItemStack item : e.getInventory().getMatrix()){
			if(item != null){
				if(item.getType() != Material.AIR){
					if(item.hasItemMeta()){
						if(item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(JackpotMCItems.getInstance(), "customItem"))){
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onCraftPrepare(InventoryClickEvent e){
		if(!(e.getView().getTopInventory() instanceof CraftingInventory)) return;
		if(e.getView().getTopInventory().getSize() != 27) return;
		if(e.getCurrentItem() == null) return;
		if(e.getCurrentItem().getType() == Material.AIR) return;
		if(e.getCurrentItem().hasItemMeta()){
			if(e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(JackpotMCItems.getInstance(), "customItem"))){
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void helper(PlayerRecipeBookClickEvent e){
		e.setCancelled(true);
	}
}
