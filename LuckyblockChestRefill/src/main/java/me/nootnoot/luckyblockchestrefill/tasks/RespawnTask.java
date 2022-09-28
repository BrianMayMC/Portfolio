package me.nootnoot.luckyblockchestrefill.tasks;

import me.nootnoot.luckyblockchestrefill.LuckyblockChestRefill;
import me.nootnoot.luckyblockchestrefill.entities.RefillChest;
import me.nootnoot.luckyblockchestrefill.entities.RefillChestItem;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RespawnTask extends BukkitRunnable {

	@Override
	public void run(){
		for(RefillChest refillChest : LuckyblockChestRefill.getInstance().getChestManager().getChests()) {
			if(refillChest.getChest() == null){
				BlockState state = refillChest.getLocation().getBlock().getState();
				if(state instanceof Chest c){
					refillChest.setChest(c);
				}
			}
			if (refillChest.getChest() != null) {
				List<RefillChestItem> complete = LuckyblockChestRefill.getInstance().getChestManager().getItems(refillChest.getType());
				List<ItemStack> items = new ArrayList<>();
				for (RefillChestItem a : complete) {
					int random = ThreadLocalRandom.current().nextInt(101);
					if (random <= a.getChance()) {
						items.add(a.getItem().getItem());
					}
				}
				refillChest.getChest().getBlockInventory().clear();
				for (ItemStack item : items) {
					refillChest.getChest().getBlockInventory().setItem(getNextSlot(refillChest.getChest().getBlockInventory()), item);
				}
			}
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
