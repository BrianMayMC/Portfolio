package me.nootnoot.luckyblockbackpacks.listeners;

import me.nootnoot.luckyblockbackpacks.LuckyblockBackpacks;
import me.nootnoot.luckyblockbackpacks.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class BackpackRespawn implements Listener {

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		e.getPlayer().getInventory().addItem(Utils.getBackpackItem(e.getPlayer()));
	}
}
