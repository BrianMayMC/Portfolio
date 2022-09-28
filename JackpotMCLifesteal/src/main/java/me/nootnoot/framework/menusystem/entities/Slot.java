package me.nootnoot.framework.menusystem.entities;

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
	@Getter private SlotSelector  selector;
	@Getter private final UUID id;

	public Slot(int index, ItemStack item){
		this.index = index;
		this.item = item;
		id = UUID.randomUUID();
	}

	public void setAction(SlotSelector slotSelector){
		this.selector = slotSelector;
	}



	public void select(){
		if(selector != null){
			synchronized (this){
				selector.select();
			}
		}
	}
}
