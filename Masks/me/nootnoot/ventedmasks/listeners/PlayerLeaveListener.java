package me.nootnoot.ventedmasks.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerLeaveListener implements Listener {

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e){
		if(e.getPlayer().hasPotionEffect(PotionEffectType.getByName("HEALTH_BOOST"))){
			e.getPlayer().removePotionEffect(PotionEffectType.getByName("HEALTH_BOOST"));
		}
	}
}
