package me.nootnoot.luckyblockairdrops.menus;

import me.nootnoot.luckyblockairdrops.LuckyblockAirdrops;
import me.nootnoot.luckyblockairdrops.enums.Rarity;
import me.nootnoot.luckyblockairdrops.menus.menusystem.MenuInterface;
import me.nootnoot.luckyblockairdrops.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CommonMenu extends MenuInterface implements Listener {

	public CommonMenu() {
		super(Utils.c("&7Common Airdrop Items Menu"), 27);
		id = 1;
	}

	@Override
	public void define() {
		List<ItemStack> items = LuckyblockAirdrops.getInstance().getAirdropManager().loadItems(Rarity.COMMON);
		for(int i = 0; i < items.size(); i++){
			if(items.get(i) != null){
				inventory.setItem(i, items.get(i));
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if(LuckyblockAirdrops.getInstance().getMenuManager().getCurrentMenu((Player)e.getPlayer()) == null) return;
		if((LuckyblockAirdrops.getInstance().getMenuManager().getCurrentMenu((Player)e.getPlayer()).getId() == 1)){
			LuckyblockAirdrops.getInstance().getAirdropManager().saveItems(Rarity.COMMON, Arrays.stream(e.getInventory().getContents()).toList());
		}
	}
}
