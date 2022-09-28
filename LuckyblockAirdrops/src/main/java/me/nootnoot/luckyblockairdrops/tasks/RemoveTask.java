package me.nootnoot.luckyblockairdrops.tasks;

import me.nootnoot.luckyblockairdrops.LuckyblockAirdrops;
import me.nootnoot.luckyblockairdrops.entities.Airdrop;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveTask extends BukkitRunnable {

	private final Airdrop airdrop;

	public RemoveTask(Airdrop airdrop) {
		this.airdrop = airdrop;
	}

	@Override
	public void run(){
		airdrop.getLocation().getBlock().setType(Material.AIR);
		airdrop.getLocation().getBlock().removeMetadata("airdrop", LuckyblockAirdrops.getInstance());
		airdrop.getArmorstand().remove();
		if(airdrop.getMarker() != null){
			airdrop.getMarker().deleteMarker();
		}
		airdrop.getLocation().getWorld().setChunkForceLoaded((int) airdrop.getLocation().getX(), (int) airdrop.getLocation().getZ(), false);

	}
}
