package me.nootnoot.luckyblockfactions.listeners;

import com.sk89q.worldguard.bukkit.listener.RegionFlagsListener;
import com.sk89q.worldguard.bukkit.listener.WorldGuardPlayerListener;
import me.nootnoot.framework.registrysystem.Registry;
import me.nootnoot.framework.registrysystem.RegistryType;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

@Registry(type = RegistryType.LISTENER)
public class FactionTeleportListener implements Listener {

	@EventHandler
	public void teleport(PlayerTeleportEvent e){
		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(e.getTo());
		Faction p = LuckyblockFactions.getInstance().getFactionManager().getFaction(e.getPlayer());
		if(f == null) return;
		if(f.getName().equalsIgnoreCase(p.getName())) return;

		e.getPlayer().sendMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.teleport-into-faction")));
		e.setCancelled(true);
	}
}
