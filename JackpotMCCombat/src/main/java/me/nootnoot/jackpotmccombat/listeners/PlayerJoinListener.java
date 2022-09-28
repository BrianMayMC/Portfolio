package me.nootnoot.jackpotmccombat.listeners;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		PersistentDataContainer container = e.getPlayer().getPersistentDataContainer();

		if (!e.getPlayer().hasPlayedBefore()) {
			container.set(Utils.key, PersistentDataType.INTEGER, JackpotMCCombat.getInstance().getConfig().getInt("grace-duration") * 60);
		}

		if (container.has(Utils.key, PersistentDataType.INTEGER)) {
			JackpotMCCombat.getInstance().getCombatPlayerManager().getPlayersOnGrace().add(e.getPlayer());
		}
	}
}
