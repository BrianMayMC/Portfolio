package me.nootnoot.ventedmasks.listeners.masks;

import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.ventedmasks.VentedMasks;
import me.nootnoot.ventedmasks.entities.Mask;
import me.nootnoot.ventedmasks.enums.BadPotionsEnum;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;

import java.util.Random;

public class CowMaskListener implements Listener {

	@EventHandler
	public void onPotionThrow(PotionSplashEvent e){

		for(LivingEntity livingEntity : e.getAffectedEntities()){
			Player p = (Player) livingEntity;
			NBTItem nbtHelmet = null;
			NBTItem nbtHand = null;
			Mask helmetMask = null;
			Mask handMask = null;

			Random random = new Random();

			if(p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() != Material.AIR){
				nbtHelmet = new NBTItem(p.getInventory().getHelmet());
				String id = nbtHelmet.getString("id");
				helmetMask = VentedMasks.getMaskManager().byName(id);
			}
			if (p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType() != Material.AIR) {
				nbtHand = new NBTItem(p.getInventory().getItemInHand());
				String id = nbtHand.getString("id");
				handMask = VentedMasks.getMaskManager().byName(id);
			}

			if(nbtHelmet != null){
				if(nbtHelmet.hasKey("mask")) {
					if (nbtHelmet.getItem().getItemMeta().getDisplayName().contains("Cow")) {
						int level = nbtHelmet.getInteger("level");
						ThrownPotion thrownPotion = e.getPotion();
						for(PotionEffect potionEffect : thrownPotion.getEffects()){
							for(BadPotionsEnum badPotions : BadPotionsEnum.values()){
								if(potionEffect.getType().getName().equalsIgnoreCase(badPotions.name())){
									if (level == 1) {
										int randomnumber = random.nextInt(101);
										if (randomnumber <= 20) {
											e.setCancelled(true);
										}
									} else if (level == 2) {
										int randomnumber = random.nextInt(101);
										if (randomnumber <= 40) {
											e.setCancelled(true);
										}
									} else if (level == 3) {
										int randomnumber = random.nextInt(101);
										if (randomnumber <= 60) {
											e.setCancelled(true);
										}
									} else if (level == 4) {
										int randomnumber = random.nextInt(101);
										if (randomnumber <= 80) {
											e.setCancelled(true);
										}
									} else if(level == 5) {
										e.setCancelled(true);
									}
								}
							}
						}
					}
				}
			}
			if(nbtHand != null){
				if(nbtHand.hasKey("mask")) {
					if (nbtHand.getItem().getItemMeta().getDisplayName().contains("Cow")) {
						int level = nbtHand.getInteger("level");
						ThrownPotion thrownPotion = e.getPotion();
						for(PotionEffect potionEffect : thrownPotion.getEffects()){
							for(BadPotionsEnum badPotions : BadPotionsEnum.values()){
								if(potionEffect.getType().getName().equalsIgnoreCase(badPotions.name())){
									if (level == 1) {
										int randomnumber = random.nextInt(101);
										if (randomnumber <= 20) {
											e.setCancelled(true);
										}
									} else if (level == 2) {
										int randomnumber = random.nextInt(101);
										if (randomnumber <= 40) {
											e.setCancelled(true);
										}
									} else if (level == 3) {
										int randomnumber = random.nextInt(101);
										if (randomnumber <= 60) {
											e.setCancelled(true);
										}
									} else if (level == 4) {
										int randomnumber = random.nextInt(101);
										if (randomnumber <= 80) {
											e.setCancelled(true);
										}
									} else if(level == 5) {
										e.setCancelled(true);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
