package me.nootnoot.headhunting.entities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.Item;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

public class Head {
	@Getter
	private final String name;
	@Getter
	private final UUID uuid;
	@Getter
	private final String configname;
	@Getter
	private final ArrayList<String> lore = new ArrayList<>();
	@Getter
	private final String url;
	@Getter
	private final int soulsAmount;

	public Head(String name, String configname, ArrayList<String> lore, String url, int soulsAmount){
		this.name = name;
		this.configname = configname;
		setLore(lore);
		this.url = url;
		this.soulsAmount = soulsAmount;
		this.uuid = UUID.nameUUIDFromBytes(name.getBytes());
	}

	public void setLore(ArrayList<String> lore){
		for(String s : lore){
			this.lore.add(ChatColor.translateAlternateColorCodes('&', s));
		}
	}

	private ItemStack getSkull(){
		ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta is_meta = (SkullMeta) is.getItemMeta();
		GameProfile profile = new GameProfile(uuid, null);
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


	public ItemStack getHead() {
		ItemStack head = getSkull();
		ItemMeta meta = head.getItemMeta();

		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		meta.setLore(lore);

		head.setItemMeta(meta);

		NBTItem nbtItem = new NBTItem(head);
		nbtItem.setString("id", configname);
		nbtItem.setBoolean("head", true);

		return nbtItem.getItem();
	}
}
