package me.nootnoot.jackpotmccombat.listeners;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e){

		if(JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(e.getEntity()) == null) return;

		JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayers().remove(JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(e.getEntity()));
	}
}
