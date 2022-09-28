package me.nootnoot.jackpotmccombat.listeners.grace;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class GracePickupListener implements Listener {

	@EventHandler
	public void onPickup(EntityPickupItemEvent e){
		if(!(e.getEntity() instanceof Player p)) return;
		if(!(JackpotMCCombat.getInstance().isGrace())) return;

		PersistentDataContainer container = p.getPersistentDataContainer();
		if(container.has(Utils.key, PersistentDataType.INTEGER)){
			p.sendMessage(Utils.c("&c&l(!)&c You cannot pick anything up while you're in grace! Do /pvp off to turn grace off! Beware, doing so, will make other players able to kill you!"));
			e.setCancelled(true);
		}
	}
}
