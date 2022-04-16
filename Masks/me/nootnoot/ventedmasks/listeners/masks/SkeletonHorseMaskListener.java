package me.nootnoot.ventedmasks.listeners.masks;

import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.ventedmasks.VentedMasks;
import me.nootnoot.ventedmasks.entities.Mask;
import me.nootnoot.ventedmasks.listeners.tasks.SkeletonHorseMaskTask;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.UUID;

public class SkeletonHorseMaskListener implements Listener {

	public static ArrayList<UUID> playersOnCooldown = new ArrayList<>();

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) {

		if (!(e.getDamager() instanceof Player && e.getEntity() instanceof Player)) return;

		Player p = (Player) e.getEntity();

		NBTItem nbtHelmet = null;
		NBTItem nbtHand = null;
		Mask helmetMask = null;
		Mask handMask = null;


		if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() != Material.AIR) {
			nbtHelmet = new NBTItem(p.getInventory().getHelmet());
			String id = nbtHelmet.getString("id");
			helmetMask = VentedMasks.getMaskManager().byName(id);
		}
		if (p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType() != Material.AIR) {
			nbtHand = new NBTItem(p.getInventory().getItemInHand());
			String id = nbtHand.getString("id");
			handMask = VentedMasks.getMaskManager().byName(id);
		}

		if (helmetMask != null) {
			if (nbtHelmet.hasKey("mask")) {
				if (nbtHelmet.getItem().getItemMeta().getDisplayName().contains("Skeleton")) {
					int level = nbtHelmet.getInteger("level");
					if (level == 5) {
						if (playersOnCooldown.contains(p.getUniqueId())) {
							return;
						}

						if (e.getDamage() >= p.getHealth()) {
							e.setCancelled(true);
							p.setHealth(20);
							p.damage(0.5);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 8 * 20, 2));
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', VentedMasks.getInstance().getConfig().getString("skeleton-horse-message")));
							playersOnCooldown.add(p.getUniqueId());
							new SkeletonHorseMaskTask(p).runTaskLater(VentedMasks.getInstance(), 10 * 20L * 60L);
						}
					}
				}
			}
		}
		if (handMask != null) {
			if (nbtHand.hasKey("mask")) {
				if (nbtHand.getItem().getItemMeta().getDisplayName().contains("Skeleton")) {
					int level = nbtHand.getInteger("level");
					if (level == 5) {
						if (playersOnCooldown.contains(p.getUniqueId())) {
							return;
						}

						if (e.getDamage() >= p.getHealth()) {
							e.setCancelled(true);
							p.setHealth(20);
							p.damage(0.5);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 8 * 20, 2));
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', VentedMasks.getInstance().getConfig().getString("skeleton-horse-message")));
							playersOnCooldown.add(p.getUniqueId());
							new SkeletonHorseMaskTask(p).runTaskLater(VentedMasks.getInstance(), 10 * 20L * 60L);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		playersOnCooldown.remove(e.getEntity().getUniqueId());
	}
	@EventHandler
	public void onFall(EntityDamageEvent e) {
		if (e.getCause() != EntityDamageEvent.DamageCause.FALL) return;

		if (!(e.getEntity() instanceof Player)) return;

		Player p = (Player) e.getEntity();

		NBTItem nbtHelmet = null;
		NBTItem nbtHand = null;
		Mask helmetMask = null;
		Mask handMask = null;


		if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() != Material.AIR) {
			nbtHelmet = new NBTItem(p.getInventory().getHelmet());
			String id = nbtHelmet.getString("id");
			helmetMask = VentedMasks.getMaskManager().byName(id);
		}
		if (p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType() != Material.AIR) {
			nbtHand = new NBTItem(p.getInventory().getItemInHand());
			String id = nbtHand.getString("id");
			handMask = VentedMasks.getMaskManager().byName(id);
		}

		if(nbtHelmet != null) {
			if (nbtHelmet.hasKey("mask")) {
				int level = nbtHelmet.getInteger("level");
				if (nbtHelmet.getItem().getItemMeta().getDisplayName().contains("Skeleton")) {

					if (level == 5) {
						if (playersOnCooldown.contains(p.getUniqueId())) {
							return;
						}

						if (e.getDamage() >= p.getHealth()) {
							e.setCancelled(true);
							p.setHealth(20);
							p.damage(0.5);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 8 * 20, 2));
							playersOnCooldown.add(p.getUniqueId());
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', VentedMasks.getInstance().getConfig().getString("skeleton-horse-message")));
							new SkeletonHorseMaskTask(p).runTaskLater(VentedMasks.getInstance(), 10 * 20L * 60L);
						}
					}
				}
			}
		}
		if(nbtHand != null) {
			if (nbtHand.hasKey("mask")) {
				if (nbtHand.getItem().getItemMeta().getDisplayName().contains("Skeleton")) {
					int level = nbtHand.getInteger("level");

					if (level == 5) {
						if (playersOnCooldown.contains(p.getUniqueId())){
							return;
						}

						if (e.getDamage() >= p.getHealth()) {
							e.setCancelled(true);
							p.setHealth(20);
							p.damage(0.5);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 8 * 20, 2));
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', VentedMasks.getInstance().getConfig().getString("skeleton-horse-message")));
							playersOnCooldown.add(p.getUniqueId());
							new SkeletonHorseMaskTask(p).runTaskLater(VentedMasks.getInstance(), 10 * 20L * 60L);
						}
					}
				}
			}
		}
	}
}
