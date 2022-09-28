package me.nootnoot.jackpotmcdropparty.listeners;

import me.nootnoot.jackpotmcdropparty.JackpotMCDropParty;
import me.nootnoot.jackpotmcdropparty.entities.SimpleItem;
import me.nootnoot.jackpotmcdropparty.utils.Utils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemAddToMenuListener implements Listener {


	@EventHandler
	public void onAdd(InventoryClickEvent e){
		if(!(e.getView().getTitle().equalsIgnoreCase("Current Drop Party Items"))) return;

		ItemStack item = e.getCurrentItem();

		if(e.getClickedInventory() == e.getView().getTopInventory()) return;
		if(item == null) return;
		if(item.getType() == Material.AIR) return;

		Material material = item.getType();
		int amount = item.getAmount();
		ItemMeta meta = item.getItemMeta();

		ItemStack newItem = new ItemStack(material, amount);
		newItem.setItemMeta(meta);

		JackpotMCDropParty.getInstance().getPartyItemManager().AddItem(new SimpleItem(newItem));
		e.getView().getTopInventory().setItem(e.getView().getTopInventory().firstEmpty(), newItem);
		e.setCancelled(true);
		e.getWhoClicked().sendMessage(Utils.c("&a&l(!)&a Successfully added item to drop party list."));
	}
}
