package me.nootnoot.luckyblockbackpacks.listeners;

import me.nootnoot.luckyblockbackpacks.LuckyblockBackpacks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class ShulkerListener implements Listener {

	@EventHandler
	public void onShulker(InventoryClickEvent e){
		if(!(LuckyblockBackpacks.getInstance().getBackpackManager().getPlayersInMenu().containsKey((Player)e.getWhoClicked()))) return;
		if(e.getCurrentItem() != null) {
			if (e.getCurrentItem().getType() == Material.SHULKER_BOX) {
				e.setCancelled(true);
			}
		}
		if(e.getHotbarButton() == -1) return;
		if(e.getWhoClicked().getInventory().getItem(e.getHotbarButton()) == null) return;
		if(e.getWhoClicked().getInventory().getItem(e.getHotbarButton()).getType() == Material.SHULKER_BOX){
			e.setCancelled(true);
		}
	}


}
