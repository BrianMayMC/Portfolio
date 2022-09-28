package me.nootnoot.luckyblockbackpacks.entities;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class Backpack {

	@Getter
	private final Player player;
	@Getter
	private int size;

	public Backpack(Player p, List<ItemStack> contents) {
		this.player = p;
		if(p != null) getSize(p);
		this.contents = contents;
	}

	@Getter
	private final List<ItemStack> contents;

	private void getSize(Player p){
		if(p.hasPermission("backpack.size.6")){
			size = 54;
		}else if(p.hasPermission("backpack.size.5")){
			size = 45;
		}else if(p.hasPermission("backpack.size.4")){
			size = 36;
		}else if(p.hasPermission("backpack.size.3")){
			size = 27;
		}else if(p.hasPermission("backpack.size.2")){
			size = 18;
		}else if(p.hasPermission("backpack.size.1")){
			size = 9;
		}else{
			size = 9;
		}
	}
}
