package me.nootnoot.luckyblockbackpacks.storage;

import me.nootnoot.luckyblockbackpacks.LuckyblockBackpacks;
import me.nootnoot.luckyblockbackpacks.entities.Backpack;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BackpackStorage {

	public void saveBackpackPerPlayer(Player p, ItemStack[] contents){
		try{
			File f = new File(LuckyblockBackpacks.getInstance().getDataFolder().getAbsolutePath() + "/storage", p.getUniqueId() + ".yml");
			FileConfiguration c = YamlConfiguration.loadConfiguration(f);
			c.set("contents", contents);
			c.save(f);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public Backpack getBackpackPerPlayer(Player p){
		File f = new File(LuckyblockBackpacks.getInstance().getDataFolder().getAbsolutePath() + "/storage", p.getUniqueId() + ".yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(f);
		if(c.get("contents") == null) return new Backpack(p, new ArrayList<>());
		return new Backpack(p, (List<ItemStack>)c.get("contents"));
	}
}
