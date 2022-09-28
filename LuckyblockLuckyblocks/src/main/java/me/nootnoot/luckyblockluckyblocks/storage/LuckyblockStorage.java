package me.nootnoot.luckyblockluckyblocks.storage;

import me.nootnoot.luckyblockluckyblocks.LuckyblockLuckyblocks;
import me.nootnoot.luckyblockluckyblocks.entities.LuckyblockItem;
import me.nootnoot.luckyblockluckyblocks.enums.Rarity;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LuckyblockStorage {


	public void addItem(Rarity rarity, LuckyblockItem item){
		try {
			File f = new File(LuckyblockLuckyblocks.getInstance().getDataFolder() + "/storage", rarity.toString().toLowerCase(Locale.ROOT) + ".yml");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
			List<ItemStack> current = (List<ItemStack>)config.get("items");
			List<Double> chances = (List<Double>)config.get("chances");
			if(current == null){
				current = new ArrayList<>();
			}
			if(chances == null){
				chances = new ArrayList<>();
			}
			current.add(item.getItem());
			chances.add(item.getChance());
			config.set("items", current);
			config.set("chances", chances);
			config.save(f);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public List<LuckyblockItem> getItems(Rarity rarity){
		File f = new File(LuckyblockLuckyblocks.getInstance().getDataFolder() + "/storage", rarity.toString().toLowerCase(Locale.ROOT) + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		List<ItemStack> items = (List<ItemStack>)config.get("items");
		List<Double> chances = (List<Double>)config.get("chances");
		if(items == null) items = new ArrayList<>();
		if(chances == null) chances = new ArrayList<>();
		List<LuckyblockItem> eventitems = new ArrayList<>();

		for(int i = 0; i < items.size(); i++){
			eventitems.add(new LuckyblockItem(items.get(i), chances.get(i)));
		}

		return eventitems;
	}
}
