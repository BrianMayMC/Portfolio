package me.nootnoot.ventedmasks.entities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public class Mask {

	@Getter
	private String name;
	@Getter
	private final String configname;
	@Getter
	private final String url;
	@Getter
	private final ArrayList<String> lore = new ArrayList<>();
	@Getter
	private final HashMap<Integer, MaskLevel> boosts;

	public Mask(String name, String url, ArrayList<String> lore, String configname, HashMap<Integer, MaskLevel> boosts){
		setName(name);
		this.url = url;
		setLore(lore);
		this.configname = configname;
		this.boosts = boosts;
	}

	public void setName(String s){
		this.name = ChatColor.translateAlternateColorCodes('&', s);
	}

	public void setLore(ArrayList<String> lore){
		for(String string : lore){
			this.lore.add(ChatColor.translateAlternateColorCodes('&', string));
		}
	}

	private ItemStack getSkull(){
		ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta is_meta = (SkullMeta) is.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField;
		try{
			profileField = is_meta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(is_meta, profile);
		}catch(NoSuchFieldException | IllegalArgumentException | IllegalAccessException e){
			e.printStackTrace();
		}
		is.setItemMeta(is_meta);
		return is;
	}

	public ItemStack getMask(int level) {
		ItemStack head = getSkull();
		ItemMeta meta = head.getItemMeta();

		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		meta.setLore(lore);

		head.setItemMeta(meta);

		NBTItem nbtItem = new NBTItem(head);
		nbtItem.setString("id", configname);
		nbtItem.setBoolean("mask", true);
		nbtItem.setInteger("level", level);
		nbtItem.setInteger("amount", 0);

		return nbtItem.getItem();
	}


	public ItemStack upgradeMask(ItemStack is){
		NBTItem nbtMask = new NBTItem(is);

		int level = nbtMask.getInteger("level");
		nbtMask.removeKey("level");
		nbtMask.setInteger("level", level + 1);

		return nbtMask.getItem();
	}


}
