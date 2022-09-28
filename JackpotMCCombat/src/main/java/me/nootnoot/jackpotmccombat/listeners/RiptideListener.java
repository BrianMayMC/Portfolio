package me.nootnoot.jackpotmccombat.listeners;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class RiptideListener implements Listener {

	@EventHandler
	public void onRiptideMove(PlayerMoveEvent paramPlayerMoveEvent) {

		Location location1 = paramPlayerMoveEvent.getTo();
		Location location2 = paramPlayerMoveEvent.getFrom();
		if (location1.getBlockX() == location2.getBlockX() &&
				location1.getBlockY() == location2.getBlockY() &&
				location1.getBlockZ() == location2.getBlockZ())
			return;
		if(JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(paramPlayerMoveEvent.getPlayer()) == null) return;
		Player player = paramPlayerMoveEvent.getPlayer();
		if (!player.isRiptiding())
			return;
		paramPlayerMoveEvent.setCancelled(true);
	}
}
