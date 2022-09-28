package me.nootnoot.jackpotmccombat.listeners;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.PluginDisableEvent;

public class PvpWallListener implements Listener {

	@EventHandler
	public void shutdown(PluginDisableEvent event) {
		// Do nothing if plugin being disabled isn't CombatTagPlus
		if (event.getPlugin() != JackpotMCCombat.getInstance()) return;

		// Shutdown executor service and clean up threads
		Utils.shutdown();
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void updateViewedBlocks(PlayerMoveEvent event) {
		// Do nothing if player hasn't moved over a whole block
		Location t = event.getTo();
		Location f = event.getFrom();
		if (t.getBlockX() == f.getBlockX() && t.getBlockY() == f.getBlockY() &&
				t.getBlockZ() == f.getBlockZ()) {
			return;
		}

		final Player player = event.getPlayer();

		// send block changes around player
		Utils.executeTask(player);
	}



}
