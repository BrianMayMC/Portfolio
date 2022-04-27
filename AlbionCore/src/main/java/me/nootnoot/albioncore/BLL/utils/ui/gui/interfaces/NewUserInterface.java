package me.nootnoot.albioncore.BLL.utils.ui.gui.interfaces;

import me.nootnoot.albioncore.BLL.utils.ui.gui.exceptions.InvalidInventorySize;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class NewUserInterface extends UserInterface implements Listener {
	private int size;
	private String[] layout;
	private ArrayList<Slot> slotArrayList = new ArrayList<>();
	private String name = "New Equipment";
	private ItemStack defaultItemStack = new ItemStack(Material.AIR);
	public NewUserInterface() {
		size = 36;
		layout = new String[size];
		Arrays.fill(layout, "");
	}

	public abstract void define();

	public void setUIName(String name){
		this.name = name;
	}
	public NewUserInterface(int size){
		if(size % 9 != 0){
			try {
				throw new InvalidInventorySize("Equipment size must be divisible by 9!");
			}catch (InvalidInventorySize e){
				e.fillInStackTrace();
				e.printStackTrace();
			}
		}
		this.size = size;
		layout = new String[size];
		Arrays.fill(layout, "");
	}
	@Override
	public final void chooseIndex(int slot) {
		String slotIdentifier = getIdentifier(slot);
		for(Slot s : slotArrayList){
			if(s.useIdentifier()){
				if(s.getID().equalsIgnoreCase(slotIdentifier)){
					s.select();
				}
			}else{
				if(s.getIndex() == slot){
					s.select();
				}
			}
		}
		select(slot);
	}

	private String getIdentifier(int slot){
		try{
			return layout[slot].toLowerCase();
		}catch (IndexOutOfBoundsException e){
			return "";
		}
	}
	protected void clearSlot(int slot){
		Slot sl = null;
		for(Slot s : slotArrayList){
			if(!s.useIdentifier()){
				if(s.getIndex() == slot){
					sl = s;
				}
			}
		}
		if(sl != null) {
			slotArrayList.remove(sl);
		}else{
			layout[slot] = "";
		}
	}
	@Override
	public final Inventory loadInventory(Player citizen) {
		Inventory inv = Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', name));
		ItemStack[] contents =new ItemStack[size];
		Arrays.fill(contents, defaultItemStack);

		for(int index = 0; index < layout.length; index++){
			for(Slot s : slotArrayList){
				if(s.useIdentifier()){
					if(layout[index].equalsIgnoreCase(s.getID())){
						contents[index] = s.getRepresentation();
						continue;
					}
				}else if(index == s.getIndex()){
					contents[index] = s.getRepresentation();
					continue;
				}
			}
		}
       /* for(Slot s : slotArrayList){
            if(s.useIdentifier()) {
                for(int index = 0; index < layout.length; index++) {
                    if(layout[index].equalsIgnoreCase(s.getID())) {
                        contents[index] = s.getRepresentation();
                    }
                }
            }else{
                contents[s.getIndex()] = s.getRepresentation();
            }
        }*/

		inv.setContents(contents);
		setInventory(inv);
		return inv;
	}
	public void setSlot(Slot slot){
		if(slot.useIdentifier()) {
			slotArrayList.add(slot);
		}else{
			if(slot.getIndex() >= layout.length || slot.getIndex() < 0){
				try{
					throw new IndexOutOfBoundsException("Index "+slot.getIndex()+" is not a position in the inventory!");

				}catch (IndexOutOfBoundsException e){
					e.printStackTrace();
				}
			}else{
				slotArrayList.add(slot);
			}
		}
	}
	public void setIdentifierSlots(String id,int... indexes){
		for(int i : indexes){
			if(i >= layout.length || i < 0){
				try{
					throw new IndexOutOfBoundsException("Index "+i+" is not a position in the inventory!");
				}catch (IndexOutOfBoundsException e){
					e.printStackTrace();
				}
			}
			layout[i] = id;
		}
	}

	public void setDefault(ItemStack stack) {
		if(stack != null) {
			this.defaultItemStack = stack;
		}
	}
	public abstract void select(int slot);
}
