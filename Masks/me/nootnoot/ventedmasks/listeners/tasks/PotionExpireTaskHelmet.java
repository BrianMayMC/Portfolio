package me.nootnoot.ventedmasks.listeners.tasks;

import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.ventedmasks.VentedMasks;
import me.nootnoot.ventedmasks.entities.Mask;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PotionExpireTaskHelmet extends BukkitRunnable {
	private final Player p;

	public PotionExpireTaskHelmet(Player p) {
		this.p = p;
	}

	@Override
	public void run() {
		ItemStack helmet = p.getInventory().getHelmet();
		if (helmet == null) {
			new RemoveHealthBoostTask(p).runTaskLater(VentedMasks.getInstance(), 5 * 20L);
			cancel();
			return;
		}

		if (helmet.getType() != Material.SKULL_ITEM) {
			new RemoveHealthBoostTask(p).runTaskLater(VentedMasks.getInstance(), 5 * 20L);
			cancel();
			return;
		}

		NBTItem nbtItemHelmet = new NBTItem(helmet);

		if (!nbtItemHelmet.hasNBTData()) {
			if (p.hasPotionEffect(PotionEffectType.getByName("HEALTH_BOOST"))) {
				p.removePotionEffect(PotionEffectType.getByName("HEALTH_BOOST"));
				cancel();
				return;
			}
		}

		if (nbtItemHelmet.hasKey("mask")) {

			int level = nbtItemHelmet.getInteger("level");
			String id = nbtItemHelmet.getString("id");

			Mask mask = VentedMasks.getMaskManager().byName(id);
			for (PotionEffect potionEffect : mask.getBoosts().get(level).getEffects()) {
				if (!potionEffect.getType().getName().equalsIgnoreCase("HEALTH_BOOST")) {
					p.addPotionEffect(potionEffect, true);
				}
			}
		}
	}
}

