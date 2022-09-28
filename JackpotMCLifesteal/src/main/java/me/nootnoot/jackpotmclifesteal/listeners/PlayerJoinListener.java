package me.nootnoot.jackpotmclifesteal.listeners;

import me.nootnoot.framework.registrysystem.Registry;
import me.nootnoot.framework.registrysystem.RegistryType;
import me.nootnoot.jackpotmclifesteal.JackpotMCLifesteal;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

@Registry(type = RegistryType.LISTENER)
public class PlayerJoinListener implements Listener {

	@EventHandler
	public void join(PlayerJoinEvent e){
		if(!(e.getPlayer().getPersistentDataContainer().has(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE))){
			e.getPlayer().getPersistentDataContainer().set(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE, 20D);
		}
		Player p = e.getPlayer();
		double amount = e.getPlayer().getPersistentDataContainer().getOrDefault(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE, 20d);
		p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(amount);
		p.setHealthScale(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
	}
}
