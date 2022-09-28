package me.nootnoot.luckyblockfactions.listeners;

import lombok.extern.jackson.Jacksonized;
import me.nootnoot.framework.registrysystem.Registry;
import me.nootnoot.framework.registrysystem.RegistryType;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

@Registry(type = RegistryType.LISTENER)
public class PlayerKillListener implements Listener {

	@EventHandler
	public void kill(PlayerDeathEvent e){
		Player p = e.getEntity();

		Faction playerF = LuckyblockFactions.getInstance().getFactionManager().getFaction(p);
		if(playerF == null) return;


		playerF.setPower(Math.max(Double.parseDouble(new DecimalFormat("#.#").format(playerF.getPower()- LuckyblockFactions.getInstance().getConfig().getDouble("power-lose-per-death"))), Double.parseDouble(new DecimalFormat("#.#").format(LuckyblockFactions.getInstance().getConfig().getDouble("min-power-per-faction")))));
		LuckyblockFactions.getInstance().getFactionManager().getFrozenFactions().put(playerF, LuckyblockFactions.getInstance().getConfig().getLong("frozen-duration") * 60L);
		new BukkitRunnable(){
			@Override
			public void run(){
				if(!(LuckyblockFactions.getInstance().getFactionManager().getFrozenFactions().containsKey(playerF))){
					cancel();
					return;
				}
				LuckyblockFactions.getInstance().getFactionManager().getFrozenFactions().put(playerF, LuckyblockFactions.getInstance().getFactionManager().getFrozenFactions().get(playerF) - 1);
				if(LuckyblockFactions.getInstance().getFactionManager().getFrozenFactions().get(playerF) == 0){
					LuckyblockFactions.getInstance().getFactionManager().getFrozenFactions().remove(playerF);
					cancel();
				}
			}
		}.runTaskTimer(LuckyblockFactions.getInstance(), 20L, 20L);


		if(e.getEntity().getKiller() == null) return;
		Player killer = e.getEntity().getKiller();

		Faction killerF = LuckyblockFactions.getInstance().getFactionManager().getFaction(killer);


		if(killerF != null) {
			if (killerF.getName().equalsIgnoreCase(playerF.getName())) return;
		}
		if(killerF != null) {
			killerF.setKills(killerF.getKills() + 1);
		}
	}
}
