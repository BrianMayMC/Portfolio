package me.nootnoot.luckyblockairdrops.storage;

import lombok.extern.jackson.Jacksonized;
import me.nootnoot.luckyblockairdrops.LuckyblockAirdrops;
import me.nootnoot.luckyblockairdrops.enums.Rarity;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ItemStorage {

	public void saveAirdropItems(Rarity rarity, List<ItemStack> items){
		try {
			File f = null;
			switch (rarity) {
				case RARE -> f = new File(LuckyblockAirdrops.getInstance().getDataFolder() + "/storage", "rare.yml");
				case UNCOMMON -> f = new File(LuckyblockAirdrops.getInstance().getDataFolder() + "/storage", "uncommon.yml");
				case COMMON -> f = new File(LuckyblockAirdrops.getInstance().getDataFolder() + "/storage", "common.yml");
			}

			YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
			config.set("items", items);
			config.save(f);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public List<ItemStack> loadAirdropItems(Rarity rarity){
		File f = null;
		switch(rarity){
			case RARE -> f = new File(LuckyblockAirdrops.getInstance().getDataFolder() + "/storage", "rare.yml");
			case UNCOMMON -> f = new File(LuckyblockAirdrops.getInstance().getDataFolder() + "/storage", "uncommon.yml");
			case COMMON ->  f = new File(LuckyblockAirdrops.getInstance().getDataFolder() + "/storage", "common.yml");
		}

		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if(config.get("items") == null){
			return List.of(new ItemStack(Material.DIRT));
		}
		return (List<ItemStack>)config.get("items");
	}
}
