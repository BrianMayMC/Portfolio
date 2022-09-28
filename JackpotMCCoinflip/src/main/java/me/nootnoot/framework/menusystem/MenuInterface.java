package me.nootnoot.framework.menusystem;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.nootnoot.framework.menusystem.entities.Slot;
import me.nootnoot.framework.menusystem.exceptions.InvalidInventorySize;
import me.nootnoot.framework.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class MenuInterface {

	protected String name;
	protected int size;
	@Setter(AccessLevel.PROTECTED)@Getter(AccessLevel.PROTECTED)
	protected Player owner;

	@Setter protected boolean filler = true;
	@Setter protected ItemStack fillerItem;

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
	@Getter @Setter protected InventoryClickEvent e;


	public final Inventory loadInventory(){
		setOwner(owner);
		SetItems();
		if(filler) setFillers();
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

	public void setFillers(){
		if(fillerItem == null) {
			fillerItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
			ItemMeta meta = fillerItem.getItemMeta();
			meta.setDisplayName(Utils.c("&7"));
			fillerItem.setItemMeta(meta);
		}
		for(int i = 0; i < inventory.getSize(); i++){
			if(inventory.getItem(i) == null) {
				inventory.setItem(i, fillerItem);
			}
		}
	}



	public abstract void define();


}
