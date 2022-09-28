package me.nootnoot.jackpotmccombat.listeners;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.entities.CombatPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKillListener implements Listener {

	@EventHandler
	public void onKill(PlayerDeathEvent e){
		if(e.getEntity().getKiller() == null) return;

		CombatPlayer p = JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(e.getEntity().getKiller());
		if(p != null){
			JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayers().remove(p);
		}

		e.getEntity().getWorld().strikeLightningEffect(e.getEntity().getLocation());
	}
}
