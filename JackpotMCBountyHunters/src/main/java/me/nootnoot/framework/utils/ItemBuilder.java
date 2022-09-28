package me.nootnoot.framework.utils;


import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ItemBuilder {

	public static ItemStack create(Material material, int amount, String name, List<String> lore){
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.c(name));
		meta.setLore(Utils.cL(lore));
		return item;
	}

	public static ItemStack create(Material material, int amount, String name, List<String> lore, NamespacedKey key, String value){
		ItemStack item = create(material, amount, name, lore);
		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
		return item;
	}
}
