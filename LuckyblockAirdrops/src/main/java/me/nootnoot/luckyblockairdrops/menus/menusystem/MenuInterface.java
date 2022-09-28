package me.nootnoot.luckyblockairdrops.menus.menusystem;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.nootnoot.luckyblockairdrops.menus.menusystem.entities.Slot;
import me.nootnoot.luckyblockairdrops.menus.menusystem.exceptions.InvalidInventorySize;
import me.nootnoot.luckyblockairdrops.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public abstract class MenuInterface {

	protected String name;
	protected int size;
	@Setter(AccessLevel.PROTECTED)@Getter(AccessLevel.PROTECTED)
	protected Player owner;

	protected List<Slot> slots = new ArrayList<>();

	@Getter protected int id = -1;

	public MenuInterface(String name, int size){
		this.name = name;
		if(size % 9 != 0){
			try{
				throw new InvalidInventorySize("Inventory size must be divisible by 9.");
			}catch(InvalidInventorySize e){
				e.printStackTrace();
			}
		}
		this.size = size;
		inventory = Bukkit.createInventory(owner, size, Utils.c(name));
	}

	@Getter protected Inventory inventory;


	public final Inventory loadInventory(){
		setOwner(owner);
		SetItems();
		return inventory;
	}

	protected void SetItems(){
		for (Slot slot : slots) {
			inventory.setItem(slot.getIndex(), slot.getItem());
		}
	}

	public void setSlot(Slot slot){
		if(slot.getIndex() >= size || slot.getIndex() < 0){
			try{
				throw new IndexOutOfBoundsException("Index " + slot.getIndex() + " is not a valid position!");
			}
			catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
		}else{
			slots.add(slot);
		}
	}

	public final void chooseIndex(int slot){
		for(Slot s : slots){
			String id = getIdentifier(slot);
			if(s.getId().toString().equalsIgnoreCase(id)){
				s.select();
			}
		}
	}

	private String getIdentifier(int slot){
		for(Slot s : slots){
			if(s.getIndex() == slot){
				return s.getId().toString();
			}
		}
		return null;
	}

	public abstract void define();


}
