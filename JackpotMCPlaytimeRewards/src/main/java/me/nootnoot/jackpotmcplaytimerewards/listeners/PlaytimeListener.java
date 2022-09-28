package me.nootnoot.jackpotmcplaytimerewards.listeners;

import me.nootnoot.framework.registrysystem.Registry;
import me.nootnoot.framework.registrysystem.RegistryType;
import me.nootnoot.jackpotmcplaytimerewards.JackpotMCPlaytimeRewards;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;

@Registry(type = RegistryType.LISTENER)
public class PlaytimeListener implements Listener {

	@EventHandler
	public void join(PlayerJoinEvent e){
		JackpotMCPlaytimeRewards.getInstance().getLevelManager().getPlaytimePlayers().add(e.getPlayer());
	}
}
