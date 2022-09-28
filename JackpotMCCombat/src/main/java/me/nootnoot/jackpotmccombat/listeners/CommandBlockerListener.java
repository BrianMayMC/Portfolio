package me.nootnoot.jackpotmccombat.listeners;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandBlockerListener implements Listener {

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		if(e.getPlayer().isOp()) return;

		if(JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(e.getPlayer()) == null) return;

		final List<String> blockedcommands = JackpotMCCombat.getInstance().getConfig().getStringList("allowed-commands");
		boolean allowed = false;
		for (String command : blockedcommands) {
			if (e.getMessage().split(" ")[0].equalsIgnoreCase(command)) {
				allowed = true;
			}
		}
		if(!allowed){
			e.setCancelled(true);
			e.getPlayer().sendMessage(Utils.c(JackpotMCCombat.getInstance().getConfig().getString("blocked-message")));
			return;
		}
		final List<String> blockedcontains = JackpotMCCombat.getInstance().getConfig().getStringList("allowed-contains");
		boolean contains = false;
		for (String contain : blockedcontains) {
			if (e.getMessage().toLowerCase().contains(contain)) {
				contains = true;
			}
		}
		if(!contains){
			e.setCancelled(true);
			e.getPlayer().sendMessage(Utils.c(JackpotMCCombat.getInstance().getConfig().getString("blocked-message")));
		}
	}


}
