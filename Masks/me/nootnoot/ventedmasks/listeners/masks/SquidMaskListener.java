package me.nootnoot.ventedmasks.listeners.masks;

import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.ventedmasks.VentedMasks;
import me.nootnoot.ventedmasks.entities.Mask;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class SquidMaskListener implements Listener {

	@EventHandler
	public void onHit(EntityDamageByEntityEvent e){

		if (!(e.getDamager() instanceof Player && e.getEntity() instanceof Player)) return;

		Player p = (Player) e.getDamager();
		Player target = (Player) e.getEntity();

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


		Random random = new Random();

		if (helmetMask != null) {
			if (nbtHelmet.hasKey("mask")) {
				if (nbtHelmet.getItem().getItemMeta().getDisplayName().contains("Squid")) {
					int level = nbtHelmet.getInteger("level");
					int number = random.nextInt(500);
					if(level == 3){
						if(number <= 5){
							target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 8 * 20, 1), true);
							target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 8 * 20, 1), true);
						}
					}else if(level == 4){
						if (number <= 7){
							target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 8 * 20, 1), true);
							target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 8 * 20, 1), true);
						}
					}else if(level == 5){
						if(number <= 10){
							target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 8 * 20, 1), true);
							target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 8 * 20, 1), true);
						}
					}
				}
			}
		}
		if (handMask != null) {
			if (nbtHand.hasKey("mask")) {
				if (nbtHand.getItem().getItemMeta().getDisplayName().contains("Squid")) {
					int level = nbtHand.getInteger("level");
					int number = random.nextInt(1001);
					if(level == 3){
						if(number <= 50){
							target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 8 * 20, 1), true);
							target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 8 * 20, 1), true);
						}
					}else if(level == 4){
						if (number <= 100){
							target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 8 * 20, 1), true);
							target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 8 * 20, 1), true);
						}
					}else if(level == 5){
						if(number <= 150){
							target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 8 * 20, 1), true);
							target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 8 * 20, 1), true);
						}
					}
				}
			}
		}
	}
}
