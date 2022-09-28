package me.nootnoot.luckyblockbackpacks.managers;

import lombok.Getter;
import me.nootnoot.luckyblockbackpacks.entities.Backpack;
import me.nootnoot.luckyblockbackpacks.storage.BackpackStorage;
import me.nootnoot.luckyblockbackpacks.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class BackpackManager {

	private final BackpackStorage db;
	@Getter
	final Map<Player, Player> playersInMenu = new HashMap<>();

	public BackpackManager(BackpackStorage db){
		this.db = db;
	}

	public void openBackpack(Player p, Player target){
		Backpack backpack = db.getBackpackPerPlayer(target);
		Inventory inv = Bukkit.createInventory(p, backpack.getSize(), Utils.c("&7" + target.getName() + "'s Backpack"));
		for(int i = 0; i < backpack.getContents().size(); i++){
			if(i >= inv.getSize()) break;
			inv.setItem(i, backpack.getContents().get(i));
		}
		playersInMenu.put(p, target);
		p.openInventory(inv);
	}

	public void saveBackpack(Player p, Inventory inv){
		db.saveBackpackPerPlayer(p, inv.getContents());
	}
}
