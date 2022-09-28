package me.nootnoot.luckyblockluckyblocks.managers;

import lombok.Getter;
import me.nootnoot.luckyblockluckyblocks.entities.LuckyblockItem;
import me.nootnoot.luckyblockluckyblocks.entities.Luckyblock;
import me.nootnoot.luckyblockluckyblocks.enums.Rarity;
import me.nootnoot.luckyblockluckyblocks.storage.LuckyblockStorage;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class LuckyblockManager {

	@Getter
	private final List<Luckyblock> luckyblocks = new ArrayList<>();

	private final LuckyblockStorage db;

	public LuckyblockManager(LuckyblockStorage db){
		this.db = db;
	}

	public void addLuckyblock(Luckyblock luckyblock){
		this.luckyblocks.add(luckyblock);
	}

	public void removeLuckyblock(Luckyblock luckyblock){
		this.luckyblocks.remove(luckyblock);
	}

	public void addItem(Rarity rarity, LuckyblockItem item){
		db.addItem(rarity, item);
	}

	public List<LuckyblockItem> getItems(Rarity rarity){
		return db.getItems(rarity);
	}

	public Luckyblock getLuckyblockAtLocation(Location loc){
		for(Luckyblock l : luckyblocks){
			if(l.getLocation().equals(loc)){
				return l;
			}
		}
		return null;
	}


}
