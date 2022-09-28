package me.nootnoot.jackpotmcdropparty.menus;


import me.nootnoot.jackpotmcdropparty.JackpotMCDropParty;
import me.nootnoot.jackpotmcdropparty.entities.SimpleItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DropPartyMenu implements identifier {

	private final Inventory inventory;
	private final Player p;
	public DropPartyMenu(Player p){
		inventory = Bukkit.createInventory(p, 54, "Current Drop Party Items");
		this.p = p;
		define();
	}

	public void define() {
		for(SimpleItem item : JackpotMCDropParty.getInstance().getMenuStorage().getMenuItems()){
			inventory.setItem(inventory.firstEmpty(), item.getItem());
		}
	}

	public void open(){
		p.openInventory(inventory);
	}

}
