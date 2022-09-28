package me.nootnoot.jackpotmccombat.tasks;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.entities.CombatPlayer;
import me.nootnoot.jackpotmccombat.utils.Utils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CombatTask extends BukkitRunnable {

	@Override
	public void run() {
		if(JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayers().isEmpty()) return;

		final List<CombatPlayer> combatPlayers = new CopyOnWriteArrayList<>(JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayers());

		for(CombatPlayer combatPlayer : combatPlayers){
			if((combatPlayer.getCooldown() - 1) <=0){
				JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayers().remove(combatPlayer);
				combatPlayer.getP().sendMessage(Utils.c(JackpotMCCombat.getInstance().getConfig().getString("combat-expired-message")));
				Utils.SendActionBar(combatPlayer.getP(), Utils.c(JackpotMCCombat.getInstance().getConfig().getString("combat-expired-actionbar")));
			}else{
				combatPlayer.setCooldown(combatPlayer.getCooldown() - 1);
				String s = Utils.c(JackpotMCCombat.getInstance().getConfig().getString("combat-actionbar")).replace("%seconds%", String.valueOf(combatPlayer.getCooldown()));
				Utils.SendActionBar(combatPlayer.getP(), s);
			}
		}
	}
}
