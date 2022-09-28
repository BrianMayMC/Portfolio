package me.nootnoot.jackpotmccombat.listeners;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.entities.CombatPlayer;
import me.nootnoot.jackpotmclifesteal.JackpotMCLifesteal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

	@EventHandler
	public void onDisconnect(PlayerQuitEvent e){
		JackpotMCCombat.getInstance().getCombatPlayerManager().getPlayersOnGrace().remove(e.getPlayer());

		CombatPlayer player = JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(e.getPlayer());
		if(player == null) return;


		JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayers().remove(player);

		if(JackpotMCLifesteal.getInstance().getPlayersBanning().contains(e.getPlayer())) return;
		player.getP().setHealth(0);
	}
}
