package me.nootnoot.luckyblockchestrefill.manager;

import lombok.Getter;
import me.nootnoot.luckyblockchestrefill.LuckyblockChestRefill;
import me.nootnoot.luckyblockchestrefill.entities.RefillChest;
import me.nootnoot.luckyblockchestrefill.entities.RefillChestItem;
import me.nootnoot.luckyblockchestrefill.entities.SimpleItem;
import me.nootnoot.luckyblockchestrefill.storage.ChestStorage;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

public class ChestManager {


	@Getter
	private final HashMap<Player, String> playersInMode = new HashMap<>();

	private final ChestStorage db;

	public ChestManager(ChestStorage db){
		this.db = db;
	}

	public void addItem(ItemStack item, String type, double chance){
		db.addItem(new RefillChestItem(new SimpleItem(item), type, chance));
	}

	public List<RefillChestItem> getItems(String type){
		return db.getItems(type);
	}

	public void addChest(RefillChest chest){
		db.addChest(chest);
	}

	public void removeChest(Location loc){
		db.removeChest(loc);
	}

	public List<RefillChest> getChests(){
		return db.getChests();
	}

	public void deleteType(String type){
		db.removeType(type);
	}
}
