package me.nootnoot.jackpotmcdropparty.tasks;

import me.nootnoot.jackpotmcdropparty.JackpotMCDropParty;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class SpawnTask extends BukkitRunnable {

	private final Location location;

	public SpawnTask(Location location) {
		this.location = location.add(-0.5, 0, -0.5);
	}

	@Override
	public void run() {
		if(JackpotMCDropParty.getInstance().getPartyItemManager().getPlacedDropParties().contains(location)){

			double bound = JackpotMCDropParty.getInstance().getConfig().getDouble("item-fly-intensity");
			double randomX = (ThreadLocalRandom.current().nextDouble(bound) - ThreadLocalRandom.current().nextDouble(bound));
			double randomY = JackpotMCDropParty.getInstance().getConfig().getLong("item-fly-height");
			double randomZ = (ThreadLocalRandom.current().nextDouble(bound) - ThreadLocalRandom.current().nextDouble(bound));

			Vector vector = new Vector(randomX, randomY, randomZ);

			Item item = location.getWorld().dropItemNaturally(location, JackpotMCDropParty.getInstance().getPartyItemManager().getRandomItem().getItem());
			item.setVelocity(vector);
			item.setPickupDelay(50);


		}else{
			cancel();
		}
	}
}
