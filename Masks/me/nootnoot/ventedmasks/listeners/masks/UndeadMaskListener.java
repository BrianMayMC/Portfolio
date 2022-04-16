package me.nootnoot.ventedmasks.listeners.masks;

import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.ventedmasks.VentedMasks;
import me.nootnoot.ventedmasks.entities.Mask;
import me.nootnoot.ventedmasks.enums.GoodPotionsEnum;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

public class UndeadMaskListener implements Listener {

	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player attacker = (Player) e.getDamager();
			Player target = (Player) e.getEntity();

			NBTItem nbtHelmet = null;
			NBTItem nbtHand = null;
			Mask helmetMask = null;
			Mask handMask = null;

			Random randomnumber = new Random();

			if (attacker.getInventory().getHelmet() != null && attacker.getInventory().getHelmet().getType() != Material.AIR) {
				nbtHelmet = new NBTItem(attacker.getInventory().getHelmet());
				String id = nbtHelmet.getString("id");
				helmetMask = VentedMasks.getMaskManager().byName(id);
			}
			if (attacker.getInventory().getItemInHand() != null && attacker.getInventory().getItemInHand().getType() != Material.AIR) {
				nbtHand = new NBTItem(attacker.getInventory().getItemInHand());
				String id = nbtHand.getString("id");
				handMask = VentedMasks.getMaskManager().byName(id);
			}

			int numberOfActivePotions = 0;
			ArrayList<PotionEffect> activePotionEffects = new ArrayList<>();
			for (PotionEffect potioneffect : target.getActivePotionEffects()) {
				numberOfActivePotions++;
				activePotionEffects.add(potioneffect);
			}

			if (nbtHand != null) {
				if (nbtHand.hasKey("mask")) {
					if(nbtHand.getItem().getItemMeta().getDisplayName().contains("Undead")){
						ArrayList<String> goodPotionEffects = new ArrayList<>();
						goodPotionEffects.add("ABSORPTION");
						goodPotionEffects.add("DAMAGE_RESISTANCE");
						goodPotionEffects.add("WATER_BREATHING");
						goodPotionEffects.add("REGENERATION");
						goodPotionEffects.add("FIRE_RESISTANCE");
						goodPotionEffects.add("INCREASE_DAMAGE");
						goodPotionEffects.add("FAST_DIGGING");
						int randomNumber = randomnumber.nextInt(numberOfActivePotions);

						if (goodPotionEffects.contains(activePotionEffects.get(randomNumber).getType().getName())) {
							e.getEntity().sendMessage(activePotionEffects.toString());

							int number = randomnumber.nextInt(501);
							int level = nbtHand.getInteger("level");
							if (level == 5){
								if(number <= 1){
									target.removePotionEffect(activePotionEffects.get(randomNumber).getType());
								}
							}
						}
					}
				}
			}
			if (nbtHelmet != null) {
				if (nbtHelmet.hasKey("mask")) {
					if(nbtHelmet.getItem().getItemMeta().getDisplayName().contains("Undead")){
						ArrayList<String> goodPotionEffects = new ArrayList<>();
						goodPotionEffects.add("ABSORPTION");
						goodPotionEffects.add("DAMAGE_RESISTANCE");
						goodPotionEffects.add("WATER_BREATHING");
						goodPotionEffects.add("REGENERATION");
						goodPotionEffects.add("FIRE_RESISTANCE");
						goodPotionEffects.add("INCREASE_DAMAGE");
						goodPotionEffects.add("FAST_DIGGING");
						int randomNumber = randomnumber.nextInt(numberOfActivePotions);

						if (goodPotionEffects.contains(activePotionEffects.get(randomNumber).getType().getName())) {
							e.getEntity().sendMessage(activePotionEffects.toString());

							int number = randomnumber.nextInt(501);
							int level = nbtHelmet.getInteger("level");
							if (level == 5){
								if(number <= 1){
									target.removePotionEffect(activePotionEffects.get(randomNumber).getType());
								}
							}
						}
					}
				}
			}
		}
	}
}