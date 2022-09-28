package me.nootnoot.luckyblockbackpacks.listeners;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.luckyblockbackpacks.LuckyblockBackpacks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class BackpackClose implements Listener {

	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if(LuckyblockBackpacks.getInstance().getBackpackManager().getPlayersInMenu().containsKey((Player)e.getPlayer())){
			LuckyblockBackpacks.getInstance().getBackpackManager().saveBackpack(LuckyblockBackpacks.getInstance().getBackpackManager().getPlayersInMenu().get(e.getPlayer()), e.getInventory());
			LuckyblockBackpacks.getInstance().getBackpackManager().getPlayersInMenu().remove((Player)e.getPlayer());
		}
	}
}
