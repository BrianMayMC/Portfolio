package me.nootnoot.luckyblockfactions.listeners;

import me.nootnoot.framework.registrysystem.Registry;
import me.nootnoot.framework.registrysystem.RegistryType;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@Registry(type = RegistryType.LISTENER)
public class FactionDamageListener implements Listener {


	@EventHandler
	public void hit(EntityDamageByEntityEvent e){
		if(!(e.getEntity() instanceof Player p)) return;
		if(!(e.getDamager() instanceof Player target)) return;

		Faction fP = LuckyblockFactions.getInstance().getFactionManager().getFaction(p);
		if(fP == null) return;
		Faction fT = LuckyblockFactions.getInstance().getFactionManager().getFaction(target);
		if(fT == null) return;
		if(fP.getName().equalsIgnoreCase(fT.getName())){
			e.setCancelled(true);
		}
	}
}
