package me.nootnoot.luckyblockfactions.listeners;

import me.nootnoot.framework.registrysystem.Registry;
import me.nootnoot.framework.registrysystem.RegistryType;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Registry(type = RegistryType.LISTENER)
public class PlayerJoinListener implements Listener {

	@EventHandler
	public void join(PlayerJoinEvent e){
		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(e.getPlayer());
		if(f == null) return;
		FactionPlayer p = f.getPlayer(e.getPlayer());
		if(!(p.getPlayerName().equalsIgnoreCase(e.getPlayer().getName()))){
			p.setPlayerName(e.getPlayer().getName());
		}

	}
}
