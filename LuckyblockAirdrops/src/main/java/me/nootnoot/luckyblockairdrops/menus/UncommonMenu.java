package me.nootnoot.luckyblockairdrops.menus;

import lombok.extern.jackson.Jacksonized;
import me.nootnoot.luckyblockairdrops.LuckyblockAirdrops;
import me.nootnoot.luckyblockairdrops.enums.Rarity;
import me.nootnoot.luckyblockairdrops.menus.menusystem.MenuInterface;
import me.nootnoot.luckyblockairdrops.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UncommonMenu extends MenuInterface implements Listener {
	public UncommonMenu() {
		super(Utils.c("&7Uncommon Airdrop Items Menu"), 27);
		id = 2;
	}

	@Override
	public void define() {
		List<ItemStack> items = LuckyblockAirdrops.getInstance().getAirdropManager().loadItems(Rarity.UNCOMMON);
		for(int i = 0; i < items.size(); i++){
			if(items.get(i) != null){
				inventory.setItem(i, items.get(i));
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if(LuckyblockAirdrops.getInstance().getMenuManager().getCurrentMenu((Player)e.getPlayer()) == null) return;
		if((LuckyblockAirdrops.getInstance().getMenuManager().getCurrentMenu((Player)e.getPlayer()).getId() == 2)){
			LuckyblockAirdrops.getInstance().getAirdropManager().saveItems(Rarity.UNCOMMON, Arrays.stream(e.getInventory().getContents()).toList());
		}
	}
}
