package me.nootnoot.luckyblockairdrops.menus.menusystem.entities;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Slot {

	@FunctionalInterface
	public interface SlotSelector{
		void select();
	}

	@Getter private final int index;
	@Getter private final ItemStack item;
	@Getter private final SlotSelector  selector;
	@Getter private final UUID id;

	public Slot(int index, ItemStack item, SlotSelector slotSelector){
		this.index = index;
		this.item = item;
		this.selector = slotSelector;
		id = UUID.randomUUID();
	}


	public void select(){
		if(selector != null){
			synchronized (this){
				selector.select();
			}
		}
	}
}
