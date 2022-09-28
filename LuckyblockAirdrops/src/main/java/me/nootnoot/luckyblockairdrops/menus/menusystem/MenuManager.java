package me.nootnoot.luckyblockairdrops.menus.menusystem;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class MenuManager {


	private final HashMap<Player, MenuInterface> playersInMenu = new HashMap<>();

	public boolean isInInterface(Player p){
		return playersInMenu.containsKey(p);
	}
	public MenuInterface getCurrentMenu(Player p){
		for(Player player : playersInMenu.keySet()){
			if(player == p){
				return playersInMenu.get(p);
			}
		}
		return null;
	}

	public void openInterface(Player p, MenuInterface menu){
		if(isInInterface(p)){
			RemoveFromMap(p);
		}
		menu.setOwner(p);
		menu.define();
		menu.inventory = menu.loadInventory();
		p.openInventory(menu.inventory);
		playersInMenu.put(p, menu);
	}

	public boolean CloseInterface(Player p){
		if(isInInterface(p)){
			p.closeInventory();
			playersInMenu.remove(p);
			return true;
		}
		return false;
	}

	public void clear(){
		playersInMenu.clear();
	}

	public void RemoveFromMap(Player p){
		playersInMenu.remove(p);
	}

	public void selectSlot(Player p, int slot){
		playersInMenu.get(p).chooseIndex(slot);
	}

}
