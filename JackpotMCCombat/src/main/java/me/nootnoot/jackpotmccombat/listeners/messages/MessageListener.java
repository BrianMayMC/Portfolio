package me.nootnoot.jackpotmccombat.listeners.messages;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MessageListener implements Listener {

	private final FileConfiguration c = JackpotMCCombat.getInstance().getConfig();

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		String n = e.getEntity().getName();
		ItemStack h = null;
		if(e.getEntity().getKiller() != null){
			h = e.getEntity().getKiller().getInventory().getItemInMainHand();
		}

		EntityDamageEvent.DamageCause cause = e.getEntity().getLastDamageCause().getCause();

		if(cause.equals(EntityDamageEvent.DamageCause.VOID)){
			if(e.getEntity().getKiller() != null){
				e.setDeathMessage(Utils.c(c.getString("death-messages.void-player").replace("%killer%", e.getEntity().getKiller().getName()).replace("%player%", n).replace("%killer%", e.getEntity().getKiller().getName())));
			}else{
				e.setDeathMessage(Utils.c(c.getString("death-messages.void").replace("%player%", n)));
			}
		}else if(cause.equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
			if(e.getEntity().getKiller() == null){
				e.setDeathMessage(Utils.c(c.getString("death-messages.none").replace("%player%", n)));
			}
			else if(h!=null) {
				if (h.hasItemMeta()) {
					if (h.getItemMeta().hasDisplayName()) {
						e.setDeathMessage(Utils.c(c.getString("death-messages.shoot").replace("%killer%", e.getEntity().getKiller().getName()).replace("%player%", n).replace("%item_name%", h.getItemMeta().getDisplayName())));
					} else {
						e.setDeathMessage(Utils.c(c.getString("death-messages.shoot").replace("%killer%", e.getEntity().getKiller().getName()).replace("%player%", n).replace("%item_name%", Utils.convertItemName(h.getType()))));
					}
				} else {
					e.setDeathMessage(Utils.c(c.getString("death-messages.shoot").replace("%killer%", e.getEntity().getKiller().getName()).replace("%player%", n).replace("%item_name%", Utils.convertItemName(h.getType()))));
				}
			}
		}else if(cause.equals(EntityDamageEvent.DamageCause.DROWNING)){
			e.setDeathMessage(Utils.c(c.getString("death-messages.drown").replace("%player%", n)));
		}else if(cause.equals(EntityDamageEvent.DamageCause.LAVA)){
			e.setDeathMessage(Utils.c(c.getString("death-messages.lava").replace("%player%", n)));
		}else if(cause.equals(EntityDamageEvent.DamageCause.FIRE)){
			e.setDeathMessage(Utils.c(c.getString("death-messages.fire").replace("%player%", n)));
		}else if(cause.equals(EntityDamageEvent.DamageCause.FIRE_TICK)){
			e.setDeathMessage(Utils.c(c.getString("death-messages.fire").replace("%player%", n)));
		}else if(cause.equals(EntityDamageEvent.DamageCause.MAGIC)){
			if(e.getEntity().getKiller() == null){
				e.setDeathMessage(Utils.c(c.getString("death-messages.magic").replace("%player%", n)));
			}else {
				if (h != null) {
					if (h.hasItemMeta()) {
						if (h.getItemMeta().hasDisplayName()) {
							e.setDeathMessage(Utils.c(c.getString("death-messages.magic-player").replace("%killer%", e.getEntity().getKiller().getName()).replace("%player%", n).replace("%item_name%", h.getItemMeta().getDisplayName())));
						} else {
							e.setDeathMessage(Utils.c(c.getString("death-messages.magic-player").replace("%killer%", e.getEntity().getKiller().getName()).replace("%player%", n).replace("%item_name%", Utils.convertItemName(h.getType()))));
						}
					} else {
						e.setDeathMessage(Utils.c(c.getString("death-messages.magic-player").replace("%killer%", e.getEntity().getKiller().getName()).replace("%player%", n).replace("%item_name%", Utils.convertItemName(h.getType()))));
					}
				}
			}
		}else if(cause.equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) || cause.equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)){
			if(e.getEntity().getKiller() == null){
				e.setDeathMessage(Utils.c(c.getString("death-messages.none").replace("%player%", n)));
			}else {
				if (h != null) {
					if (h.hasItemMeta()) {
						if (h.getItemMeta().hasDisplayName()) {
							e.setDeathMessage(Utils.c(c.getString("death-messages.explosion-player").replace("%killer%", e.getEntity().getKiller().getName()).replace("%player%", n).replace("%item_name%", h.getItemMeta().getDisplayName())));
						}
					} else {
						e.setDeathMessage(Utils.c(c.getString("death-messages.explosion-player").replace("%killer%", e.getEntity().getKiller().getName()).replace("%player%", n).replace("%item_name%", Utils.convertItemName(h.getType()))));
					}
				}
			}
		}else if(cause.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) || cause.equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)){
			if(h != null) {
				if (h.hasItemMeta()) {
					if (h.getItemMeta().hasDisplayName()) {
						e.setDeathMessage(Utils.c(c.getString("death-messages.sword-kill").replace("%item_name%", h.getItemMeta().getDisplayName()).replace("%killer%", e.getEntity().getKiller().getName()).replace("%player%", n)));
					} else {
						e.setDeathMessage(Utils.c(c.getString("death-messages.sword-kill").replace("%item_name%", Utils.convertItemName(h.getType())).replace("%killer%", e.getEntity().getKiller().getName()).replace("%player%", n)));
					}
				} else {
					e.setDeathMessage(Utils.c(c.getString("death-messages.sword-kill").replace("%item_name%", Utils.convertItemName(h.getType())).replace("%killer%", e.getEntity().getKiller().getName()).replace("%player%", n)));
				}
			}
		}
		else{
			e.setDeathMessage(Utils.c(c.getString("death-messages.none").replace("%player%", n)));
		}
	}
}
