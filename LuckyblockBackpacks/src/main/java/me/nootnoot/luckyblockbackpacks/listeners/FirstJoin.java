package me.nootnoot.luckyblockbackpacks.listeners;

import me.nootnoot.luckyblockbackpacks.LuckyblockBackpacks;
import me.nootnoot.luckyblockbackpacks.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FirstJoin implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		if(e.getPlayer().hasPlayedBefore()) return;

		e.getPlayer().getInventory().setItem(LuckyblockBackpacks.getInstance().getConfig().getInt("first-join-slot"), Utils.getBackpackItem(e.getPlayer()));
	}
}
