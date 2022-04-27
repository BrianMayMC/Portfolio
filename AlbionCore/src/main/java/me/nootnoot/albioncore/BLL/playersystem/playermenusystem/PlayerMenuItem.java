package me.nootnoot.albioncore.BLL.playersystem.playermenusystem;

import de.tr7zw.nbtapi.NBTItem;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PlayerMenuItem {

	public ItemStack CreateMainItem(){
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.AQUA + "Player Menu");

		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Click to open your player menu.");

		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);

		NBTItem nbtItem = new NBTItem(item);
		nbtItem.setBoolean("playerMenu", true);

		return nbtItem.getItem();
	}

	public ItemStack CreateArtifactItem(){
		ItemStack item = new ItemStack(Material.BEACON);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.AQUA + "Artifact Pouch");

		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Click to open your artifact pouch.");

		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);

		NBTItem nbtItem = new NBTItem(item);
		nbtItem.setBoolean("artifactPouch", true);

		return nbtItem.getItem();
	}

}
