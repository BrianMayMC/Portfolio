package me.nootnoot.luckyblockoreregen.managers;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.luckyblockoreregen.LuckyBlockOreRegen;
import me.nootnoot.luckyblockoreregen.entities.RegenBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegenBlockManager {

	@Getter@Setter
	private List<Player> playersInMode;
	@Getter@Setter
	private HashMap<Location, Material> minedBlocks;

	public RegenBlockManager(){
		playersInMode = new ArrayList<>();
		minedBlocks = new HashMap<>();
	}

	public void RestoreBlocks(){
		for(Location location : minedBlocks.keySet()){
			location.getBlock().setType(minedBlocks.get(location));
		}
		minedBlocks.clear();
	}

}
