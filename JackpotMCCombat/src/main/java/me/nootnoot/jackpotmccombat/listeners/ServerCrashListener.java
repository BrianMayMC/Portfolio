package me.nootnoot.jackpotmccombat.listeners;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class ServerCrashListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onServerRestart(PluginDisableEvent e){
		if(e.getPlugin() != JackpotMCCombat.getInstance()) return;

		JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayers().clear();
		//JackpotMCCombat.getInstance().getCombatPlayerManager().getPlayersOnGrace().clear();
	}

}
