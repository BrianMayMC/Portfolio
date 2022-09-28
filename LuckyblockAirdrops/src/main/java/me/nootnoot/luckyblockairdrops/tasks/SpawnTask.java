package me.nootnoot.luckyblockairdrops.tasks;

import me.nootnoot.luckyblockairdrops.LuckyblockAirdrops;
import me.nootnoot.luckyblockairdrops.entities.Airdrop;
import me.nootnoot.luckyblockairdrops.enums.Rarity;
import me.nootnoot.luckyblockairdrops.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.dynmap.markers.AreaMarker;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class SpawnTask extends BukkitRunnable implements Listener {

	private final int amount;
	private Rarity rarity;

	public SpawnTask(int amount) {
		this.amount = amount;
	}

	public SpawnTask(Rarity rarity, int amount) {
		this.rarity = rarity;
		this.amount = amount;
	}


	@Override
	public void run() {
		for(int i = 0; i < amount; i++) {
			if(rarity == null){
				rarity = Utils.getRandomRarity();
			}
			Location location = Utils.getRandomLocation();
			location.getWorld().setChunkForceLoaded((int) location.getX(), (int) location.getZ(), true);
			Airdrop airdrop = new Airdrop(rarity, location, LuckyblockAirdrops.getInstance().getAirdropManager().loadItems(rarity), null);
			LuckyblockAirdrops.getInstance().getAirdropManager().getAirdrops().add(airdrop);

			AreaMarker marker = LuckyblockAirdrops.getInstance().getMarker().createAreaMarker(UUID.randomUUID().toString(), rarity + " Airdrop", true, location.getWorld().getName(), new double[]{location.getX() - 8, location.getX() + 8}, new double[]{location.getZ() - 8, location.getZ() + 8}, false);
			airdrop.setMarker(marker);
			LuckyblockAirdrops.getInstance().getAirdropManager().getMarkers().add(marker);

			ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getX(), location.getY() + 80, location.getZ()), EntityType.ARMOR_STAND);
			stand.setInvulnerable(true);
			stand.setInvisible(true);
			stand.setGravity(true);
			stand.setVisualFire(true);
			stand.setCustomName(Utils.c(LuckyblockAirdrops.getInstance().getConfig().getString("airdrops." + rarity + ".name")));
			stand.setCustomNameVisible(true);
			stand.setHelmet(new ItemStack(Material.CHEST));
			stand.setMetadata("airdrop", new FixedMetadataValue(LuckyblockAirdrops.getInstance(), true));
			new BukkitRunnable() {
				@Override
				public void run() {
					if (stand.getLocation().getY() < location.getWorld().getHighestBlockYAt((int) location.getX(), (int) location.getZ()) + 5) {
						stand.remove();
						new BukkitRunnable() {
							@Override
							public void run() {
								Location loc = new Location(location.getWorld(), (int) location.getX(), location.getWorld().getHighestBlockYAt((int) location.getX(), (int) location.getZ()) + 1, (int) location.getZ());
								loc.getBlock().setType(Material.CHEST);
								loc.getBlock().getLocation().getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
								loc.getBlock().getLocation().getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 20);
								Chest chest = (Chest) loc.getBlock().getState();
								chest.getBlockInventory().clear();
								for (ItemStack item : airdrop.getItems()) {
									chest.getBlockInventory().setItem(getNextSlot(chest.getBlockInventory()), item);
								}
								chest.setMetadata("airdrop", new FixedMetadataValue(LuckyblockAirdrops.getInstance(), airdrop.getRarity()));
								airdrop.setLocation(loc);
								new BukkitRunnable() {
									@Override
									public void run() {
										ArmorStand stand = (ArmorStand) loc.getBlock().getWorld().spawnEntity(new Location(loc.getWorld(), loc.getX() + 0.5, loc.getY() - 1, loc.getZ() + 0.5), EntityType.ARMOR_STAND);
										stand.setInvulnerable(true);
										stand.setInvisible(true);
										stand.setGravity(false);
										stand.setCustomName(Utils.c(LuckyblockAirdrops.getInstance().getConfig().getString("airdrops." + airdrop.getRarity() + ".name")));
										stand.setCustomNameVisible(true);
										airdrop.setArmorstand(stand);
										new RemoveTask(airdrop).runTaskLater(LuckyblockAirdrops.getInstance(), LuckyblockAirdrops.getInstance().getConfig().getInt("remove-time") * 20L * 60L);
									}
								}.runTaskLater(LuckyblockAirdrops.getInstance(), 1L);
								cancel();
							}
						}.runTaskLater(LuckyblockAirdrops.getInstance(), 5L);
						cancel();
					}
				}
			}.runTaskTimer(LuckyblockAirdrops.getInstance(), 10L, 10L);
			rarity = null;
		}
	}

	public int getNextSlot(Inventory inventory){
		int random = ThreadLocalRandom.current().nextInt(inventory.getSize());
		int i = 0;
		while(inventory.getItem(random) != null && inventory.getItem(random).getType() != Material.AIR){
			if(i == 100){
				break;
			}
			random = ThreadLocalRandom.current().nextInt(inventory.getSize());
			i++;
		}
		return random;
	}
}


