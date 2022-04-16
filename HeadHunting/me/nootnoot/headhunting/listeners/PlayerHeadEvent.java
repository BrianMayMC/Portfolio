package me.nootnoot.headhunting.listeners;

import de.tr7zw.nbtapi.NBTItem;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import me.nootnoot.headhunting.HeadHunting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PlayerHeadEvent implements Listener {

	@EventHandler
	public void onHeadInvClick(InventoryClickEvent e) {
		if (e.getCurrentItem() == null) return;
		if (e.getCurrentItem().getType() == Material.AIR) return;

		ItemStack is = e.getCurrentItem();
		NBTItem nbtIs = new NBTItem(is);
		if (!nbtIs.hasNBTData()) return;
		if (!nbtIs.hasKey("playerhead")) return;

		ItemMeta meta = is.getItemMeta();
		ArrayList<String> lore = (ArrayList<String>) meta.getLore();
		String name = nbtIs.getString("name");
		int index = 0;
		for(String s : lore){
			if(s.contains("$")){
				index = lore.indexOf(s);
				break;
			}
		}

		double balance = ((HeadHunting.getInstance().getEcon().getBalance(Bukkit.getOfflinePlayer(name)) / 100) * 20);
		String balanceInLore = lore.get(index).substring(lore.get(index).indexOf("$"));
		String s = lore.get(index).replace(balanceInLore, "$" + balance);
		lore.set(index, s);
		meta.setLore(lore);
		is.setItemMeta(meta);
	}

	@EventHandler
	public void onHeadClick(PlayerInteractEvent e){
		if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

		if (e.getItem() == null) return;
		if (e.getItem().getType() == Material.AIR) return;

		ItemStack is = e.getItem();
		NBTItem nbtIs = new NBTItem(is);
		if (!nbtIs.hasNBTData()) return;
		if (!nbtIs.hasKey("playerhead")) return;

		e.setCancelled(true);

		String name = nbtIs.getString("name");
		double balance = ((HeadHunting.getInstance().getEcon().getBalance(Bukkit.getOfflinePlayer(name)) / 100) * 20);

		HeadHunting.getInstance().getEcon().depositPlayer(e.getPlayer(), balance);
		e.getPlayer().getInventory().remove(is);
	}
}

