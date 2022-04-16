package me.nootnoot.headhunting.managers;

import com.mojang.authlib.GameProfile;
import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.entities.Booster;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class BoosterManager {
	@Getter
	private final ArrayList<Booster> boosters = new ArrayList<>();

	@Getter
	private final HashMap<Player, Booster> activeboosters = new HashMap<>();

	private static BoosterManager instance;
	public static BoosterManager GetInstance(){
		if(instance == null){
			return instance = new BoosterManager();
		}
		return instance;
	}

	public BoosterManager(){
		fillBoosters();
	}

	public void fillBoosters(){
		ItemStack is = new ItemStack(Material.WATCH);
		ItemMeta is_meta = is.getItemMeta();

		for(String path : HeadHunting.getInstance().getConfig().getConfigurationSection("boosters.").getKeys(false)){
			String fullpath = "boosters." + path;

			is_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
					HeadHunting.getInstance().getConfig().getString(fullpath + ".name")));

			ArrayList<String> lore = new ArrayList<>();
			for(String s : HeadHunting.getInstance().getConfig().getStringList(fullpath + ".lore")){
				lore.add(ChatColor.translateAlternateColorCodes('&', s));
			}
			is_meta.setLore(lore);
			is.setItemMeta(is_meta);
			NBTItem nbtIs = new NBTItem(is);
			nbtIs.setBoolean("booster", true);

			String type = null;
			if(HeadHunting.getInstance().getConfig().getString(fullpath + ".type").equalsIgnoreCase("headhunting")){
				type = "headhunting";
			}else if(HeadHunting.getInstance().getConfig().getString(fullpath + ".type").equalsIgnoreCase("soul"))
				type = "soul";
			boosters.add(new Booster(
					path,
					ChatColor.translateAlternateColorCodes('&', HeadHunting.getInstance().getConfig().getString(fullpath + ".name")),
					HeadHunting.getInstance().getConfig().getInt(fullpath + ".duration"),
					HeadHunting.getInstance().getConfig().getDouble(fullpath + ".amplifier"),
					nbtIs.getItem(),
					type
			));
		}
	}

	public Booster ByConfigname(String configname){
		for(Booster booster : boosters){
			if(booster.getConfigname().equalsIgnoreCase(configname)){
				return booster;
			}
		}
		return null;
	}
	public Booster ByName(String name){
		for(Booster booster : boosters){
			if(booster.getName().equalsIgnoreCase(name)){
				return booster;
			}
		}
		return null;
	}
}
