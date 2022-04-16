package me.nootnoot.ventedmasks.listeners.masks;

import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.ventedmasks.VentedMasks;
import me.nootnoot.ventedmasks.entities.Mask;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

public class ChickenMaskListener implements Listener {

	@EventHandler
	public void onFallDamage(EntityDamageEvent e) {
		if (e.getCause() != EntityDamageEvent.DamageCause.FALL) return;

		if (!(e.getEntity() instanceof Player)) return;

		Player p = (Player) e.getEntity();

		NBTItem nbtHelmet = null;
		NBTItem nbtHand = null;

		Random randomnumber = new Random();

		if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() != Material.AIR) {
			nbtHelmet = new NBTItem(p.getInventory().getHelmet());
		}
		if (p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType() != Material.AIR) {
			nbtHand = new NBTItem(p.getInventory().getItemInHand());
		}


		if (nbtHelmet != null) {
			if (nbtHelmet.hasKey("mask")) {
				if (nbtHelmet.getItem().getItemMeta().getDisplayName().contains("Chicken")){
					int helmetLevel = nbtHelmet.getInteger("level");
					if (helmetLevel == 1) {
						int random = randomnumber.nextInt(101);
						if (random <= 20) {
							e.setCancelled(true);
						}
					} else if (helmetLevel == 2) {
						int random = randomnumber.nextInt(101);
						if (random <= 40) {
							e.setCancelled(true);
						}
					} else if (helmetLevel == 3) {
						int random = randomnumber.nextInt(101);
						if (random <= 60) {
							e.setCancelled(true);
						}
					} else if (helmetLevel == 4) {
						int random = randomnumber.nextInt(101);
						if (random <= 80) {
							e.setCancelled(true);
						}
					} else if(helmetLevel == 5){
						e.setCancelled(true);
					}
				}
			}
		}
		if (nbtHand != null) {
			if (nbtHand.hasKey("mask")) {
				if (nbtHand.getItem().getItemMeta().getDisplayName().contains("Chicken")){
					int handLevel = nbtHand.getInteger("level");
					if (handLevel == 1) {
						int random = randomnumber.nextInt(101);
						if (random <= 20) {
							e.setCancelled(true);
						}
					} else if (handLevel == 2) {
						int random = randomnumber.nextInt(101);
						if (random <= 40) {
							e.setCancelled(true);
						}
					} else if (handLevel == 3) {
						int random = randomnumber.nextInt(101);
						if (random <= 60) {
							e.setCancelled(true);
						}
					} else if (handLevel == 4) {
						int random = randomnumber.nextInt(101);
						if (random <= 80) {
							e.setCancelled(true);
						}
					} else if(handLevel == 5) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
}
