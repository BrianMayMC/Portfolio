package me.nootnoot.jackpotmccombat.tasks;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class GraceTask extends BukkitRunnable {
	@Override
	public void run() {
		if(JackpotMCCombat.getInstance().getCombatPlayerManager().getPlayersOnGrace().isEmpty()) return;
		for(Player p : JackpotMCCombat.getInstance().getCombatPlayerManager().getPlayersOnGrace()){
			PersistentDataContainer container = p.getPersistentDataContainer();
			if(container.has(Utils.key, PersistentDataType.INTEGER)){
				if(container.get(Utils.key, PersistentDataType.INTEGER) > 0){
					container.set(Utils.key, PersistentDataType.INTEGER, (container.get(Utils.key, PersistentDataType.INTEGER) - 1));
				}else{
					container.remove(Utils.key);
				}
			}
		}
	}
}
