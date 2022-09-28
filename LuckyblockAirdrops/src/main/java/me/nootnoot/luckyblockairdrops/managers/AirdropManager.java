package me.nootnoot.luckyblockairdrops.managers;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.luckyblockairdrops.entities.Airdrop;
import me.nootnoot.luckyblockairdrops.enums.Rarity;
import me.nootnoot.luckyblockairdrops.storage.ItemStorage;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.Marker;

import java.util.ArrayList;
import java.util.List;

public class AirdropManager {

	private final ItemStorage db;

	@Getter
	private final List<Airdrop> airdrops = new ArrayList<>();

	@Getter
	private final List<AreaMarker> markers = new ArrayList<>();

	public AirdropManager(ItemStorage db){
		this.db = db;
	}

	public void saveItems(Rarity rarity, List<ItemStack> contents){
		db.saveAirdropItems(rarity, contents);
	}

	public List<ItemStack> loadItems(Rarity rarity){
		return db.loadAirdropItems(rarity);
	}


}
