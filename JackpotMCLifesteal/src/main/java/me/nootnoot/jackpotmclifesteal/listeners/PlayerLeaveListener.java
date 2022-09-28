package me.nootnoot.jackpotmclifesteal.listeners;

import me.nootnoot.framework.registrysystem.Registry;
import me.nootnoot.framework.registrysystem.RegistryType;
import me.nootnoot.jackpotmclifesteal.JackpotMCLifesteal;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

@Registry(type = RegistryType.LISTENER)
public class PlayerLeaveListener implements Listener {

	@EventHandler
	public void leave(PlayerQuitEvent e){
		e.getPlayer().getPersistentDataContainer().set(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE, e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		new BukkitRunnable(){
			@Override
			public void run(){
				JackpotMCLifesteal.getInstance().getPlayersBanning().remove(e.getPlayer());
			}
		}.runTaskLater(JackpotMCLifesteal.getInstance(), 20L);
	}
}
