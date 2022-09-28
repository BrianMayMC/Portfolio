package me.nootnoot.jackpotmcitems.managers;

import lombok.Getter;
import me.nootnoot.jackpotmcitems.customitems.*;
import me.nootnoot.jackpotmcitems.interfaces.CustomItem;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class ItemManager {

	@Getter
	private final ArrayList<CustomItem> customItems;

	public ItemManager(){
		customItems = new ArrayList<>();
		createItems();
	}

	public void reload(){
		for(CustomItem customItem : customItems){
			Bukkit.removeRecipe(customItem.getShapedRecipe().getKey());
		}
		customItems.clear();
		createItems();
	}

	public CustomItem getItem(String name){
		for(CustomItem customItem : customItems){
			if(customItem.getName().equalsIgnoreCase(name)){
				return customItem;
			}
		}
		return null;
	}

	public void createItems(){
		customItems.add(new ZapStick());
		customItems.add(new Blacksmith());
		customItems.add(new StrengthRod());
		customItems.add(new CritsMultiplier());
		customItems.add(new Levitator());
		customItems.add(new Deflector());
		customItems.add(new Cloud());
	}
}
