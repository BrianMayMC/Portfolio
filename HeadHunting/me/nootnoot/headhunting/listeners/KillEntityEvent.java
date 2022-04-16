package me.nootnoot.headhunting.listeners;

import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.entities.Head;
import me.nootnoot.headhunting.entities.Level;
import me.nootnoot.headhunting.managers.LevelManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillEntityEvent implements Listener {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e){
		if(e.getEntity() instanceof Player) return;

		Head head = null;
		for(Head allHeads : HeadHunting.getInstance().getHeadManager().getHeads()){
			if(allHeads.getConfigname().equalsIgnoreCase(e.getEntity().getName().toLowerCase())){
				head = allHeads;
			}
		}

		e.setDroppedExp(0);
		if(head != null)
			e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), head.getHead());

	}
}
