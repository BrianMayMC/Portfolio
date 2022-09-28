package me.nootnoot.jackpotmccombat.listeners;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;

public class ElytraListener implements Listener {

	@EventHandler
	public void onElytra(EntityToggleGlideEvent e){
		if(!(e.getEntity() instanceof Player p)) return;
		if(JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(p) == null) return;

		ItemStack elytra = p.getInventory().getChestplate();

		if(elytra == null) return;

		if(elytra.getType() != Material.ELYTRA) return;

		p.getInventory().setChestplate(new ItemStack(Material.AIR));

		if(p.getInventory().firstEmpty() == -1){
			p.getWorld().dropItemNaturally(p.getLocation(), elytra);
			return;
		}

		p.getInventory().addItem(elytra);

		e.setCancelled(true);
	}
}
