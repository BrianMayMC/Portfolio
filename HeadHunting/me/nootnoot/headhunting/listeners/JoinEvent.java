package me.nootnoot.headhunting.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		if(e.getPlayer().getLevel() < 1){
			e.getPlayer().setLevel(1);
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		e.getEntity().setLevel(1);
	}
}
