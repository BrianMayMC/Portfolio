package me.nootnoot.albioncore.BLL.utils.ui.gui.interfaces;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Slot {
	public interface SlotSelector{
		void select();
	}
	private int index;
	//This will be used to set the layout, anything with this id will become this slot
	private String id = null;
	private SlotSelector selector = null;
	private ItemStack representation = new ItemStack(Material.AIR);
	public Slot(int slot){
		this.index = slot;
	}
	public Slot(String id){
		this.id  = id;
	}
	public Slot(int index, SlotSelector callback){
		this.index = index;
		this.selector = callback;
	}
	public Slot(String id, SlotSelector callback){
		this.id = id;
		this.selector = callback;
	}
	public Slot(int index, ItemStack representation){
		this.index = index;
		this.representation = representation;
	}
	public Slot(String id, ItemStack representation){
		this.id  = id;
		this.representation = representation;
	}
	public Slot(int index, ItemStack representation, SlotSelector callback){
		this.index = index;
		this.selector = callback;
		this.representation = representation;
	}
	public Slot(String id, ItemStack representation, SlotSelector callback){
		this.id = id;
		this.selector = callback;
		this.representation = representation;
	}
	boolean useIdentifier(){
		return id != null;
	}
	int getIndex(){
		return index;
	}
	String getID(){
		return id.toLowerCase();
	}
	void select(){
		if(selector != null){
			synchronized (this) {
				selector.select();
			}
		}
	}
	ItemStack getRepresentation(){
		return representation;
	}
}
