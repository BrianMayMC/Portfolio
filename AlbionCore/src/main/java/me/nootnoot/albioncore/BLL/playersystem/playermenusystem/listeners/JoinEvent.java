package me.nootnoot.albioncore.BLL.playersystem.playermenusystem.listeners;

import me.nootnoot.albioncore.AlbionCore;
import me.nootnoot.albioncore.BLL.playersystem.playermenusystem.PlayerMenuItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		PlayerMenuItem playerMenuItem = new PlayerMenuItem();
		e.getPlayer().getInventory().setItem(8, playerMenuItem.CreateMainItem());
		AlbionCore.getInstance().getAbPlayerManager().RegisterPlayer(e.getPlayer());
	}
}
