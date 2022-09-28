package me.nootnoot.luckyblockbackpacks.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.extern.jackson.Jacksonized;
import me.nootnoot.luckyblockbackpacks.LuckyblockBackpacks;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class Utils {

	public static String c(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public static List<String> cL(List<String> s){
		List<String> newList = new ArrayList<>();
		for(String string : s){
			newList.add(c(string));
		}
		return newList;
	}
	public static NamespacedKey key = new NamespacedKey(LuckyblockBackpacks.getInstance(), "backpack");

	private static ItemStack getSkull(Player p){
		ItemStack is = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta is_meta = (SkullMeta) is.getItemMeta();
		is_meta.setDisplayName(Utils.c(LuckyblockBackpacks.getInstance().getConfig().getString("backpack.name").replace("%player%", p.getName())));
		is_meta.setLore(Utils.cL(LuckyblockBackpacks.getInstance().getConfig().getStringList("backpack.lore")));
		is_meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "yes");

		GameProfile profile = new GameProfile(new UUID(1, 2), null);
		byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", "http://textures.minecraft.net/texture/9165ee13a606e1b44695af46c39b52ce66657a4c4a623d0b282a7b8ce0509404").getBytes());
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

	public static ItemStack getBackpackItem(Player p){
		return getSkull(p);
	}
}
