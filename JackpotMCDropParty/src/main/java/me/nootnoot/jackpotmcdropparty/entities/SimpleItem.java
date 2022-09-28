package me.nootnoot.jackpotmcdropparty.entities;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.jackpotmcdropparty.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Getter
@Setter
public class SimpleItem {
	private String material;
	private int amount;
	private String name;
	private List<String> lore;
	private int customModelData = -1;


	public SimpleItem(ItemStack item){
		this.material = item.getType().toString();
		this.amount = item.getAmount();
		this.name = item.getItemMeta().getDisplayName();
		this.lore = item.getItemMeta().getLore();
		if(item.getItemMeta().hasCustomModelData()) {
			this.customModelData = item.getItemMeta().getCustomModelData();
		}
	}

	public ItemStack getItem(){
		ItemStack item = new ItemStack(Material.matchMaterial(material), amount);
		ItemMeta meta = item.getItemMeta();
		if(name != null) {
			meta.setDisplayName(Utils.c(name));
		}
		if(lore != null) {
			meta.setLore(Utils.cL(lore));
		}
		if(customModelData != -1) {
			meta.setCustomModelData(customModelData);
		}
		item.setItemMeta(meta);
		return item;
	}
}
