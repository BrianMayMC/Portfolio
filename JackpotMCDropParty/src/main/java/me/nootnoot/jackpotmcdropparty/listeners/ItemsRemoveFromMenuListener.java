package me.nootnoot.jackpotmcdropparty.listeners;

import me.nootnoot.jackpotmcdropparty.JackpotMCDropParty;
import me.nootnoot.jackpotmcdropparty.entities.SimpleItem;
import me.nootnoot.jackpotmcdropparty.utils.Utils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ItemsRemoveFromMenuListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(!(e.getView().getTitle().equalsIgnoreCase("Current Drop Party Items"))) return;

		ItemStack item = e.getCurrentItem();
		if(e.getClickedInventory() != e.getView().getTopInventory()) return;

		if(item == null) return;
		if(item.getType() == Material.AIR) return;

		JackpotMCDropParty.getInstance().getPartyItemManager().RemoveItem(new SimpleItem(item));
		e.getView().getTopInventory().setItem(e.getSlot(), new ItemStack(Material.AIR));
		e.setCancelled(true);
		e.getWhoClicked().sendMessage(Utils.c("&a&l(!)&a Successfully removed item from drop party list."));
	}
}
