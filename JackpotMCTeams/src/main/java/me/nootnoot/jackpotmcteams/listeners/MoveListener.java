package me.nootnoot.jackpotmcteams.listeners;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

	@EventHandler
	public void move(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(!(JackpotMCTeams.getInstance().getPlayerTeleportQueue().contains(p))) return;
		p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Teleport cancelled..."));
		JackpotMCTeams.getInstance().getPlayerTeleportQueue().remove(p);
	}

}
