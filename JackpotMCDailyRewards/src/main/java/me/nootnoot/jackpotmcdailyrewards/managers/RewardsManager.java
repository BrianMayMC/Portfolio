package me.nootnoot.jackpotmcdailyrewards.managers;

import lombok.Getter;
import me.nootnoot.jackpotmcdailyrewards.JackpotMCDailyRewards;
import me.nootnoot.jackpotmcdailyrewards.entities.SimpleItem;
import me.nootnoot.jackpotmcdailyrewards.menus.SpinMenu;
import me.nootnoot.jackpotmcdailyrewards.storage.CooldownStorage;
import me.nootnoot.jackpotmcdailyrewards.storage.RewardsStorage;
import net.minecraft.world.item.Items;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RewardsManager {

	private final RewardsStorage db;
	private final CooldownStorage cDb;

	@Getter private List<SimpleItem> mItems;

	public RewardsManager(RewardsStorage db, CooldownStorage cDb){
		this.db = db;
		this.cDb = cDb;
		mItems = db.get();
		if(mItems == null){
			mItems = new ArrayList<>();
		}
	}


	public void addItem(SimpleItem item){
		mItems.add(item);
		db.add(item);
	}

	public void removeItem(SimpleItem item){
		mItems.remove(item);
		db.remove(item);
	}

	public void startSpin(Player p){
		final LinkedHashMap<Integer, ItemStack>[] random14 = new LinkedHashMap[]{new LinkedHashMap<>()};
		LinkedList<Integer> slots = new LinkedList<>(List.of(11, 12, 13, 14, 15, 24, 33, 42, 41, 40, 39, 38, 29, 20));
		for(int i : slots){
			int random = ThreadLocalRandom.current().nextInt(mItems.size());
			random14[0].put(i, mItems.get(random).getItem());
		}

		p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1F, 1F);
		int ticks = ThreadLocalRandom.current().nextInt(40, 50);
		final int[] done = {0};
		new BukkitRunnable(){
			@Override
			public void run(){
				p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_ELYTRA, 1F, 1F);
				JackpotMCDailyRewards.getInstance().getMenuManager().openInterface(p, new SpinMenu(false, random14[0]));
				if(done[0] == ticks){
					p.getInventory().addItem(getFinalItem(random14[0]));
					random14[0].clear();
					new BukkitRunnable(){
						@Override
						public void run(){
							JackpotMCDailyRewards.getInstance().getMenuManager().closeInterface(p);
						}
					}.runTaskLater(JackpotMCDailyRewards.getInstance(), 60L);
					cancel();
					return;
				}
				random14[0] = switchItems(random14[0]);
				done[0]++;
			}
		}.runTaskTimer(JackpotMCDailyRewards.getInstance(), 2L, 2L);
		cDb.addCooldown(p.getUniqueId().toString());
	}

	public ItemStack getFinalItem(Map<Integer, ItemStack> items){
		for(int i : items.keySet()){
			if(i == 13){
				return items.get(i);
			}
		}
		return null;
	}

	public LinkedHashMap<Integer, ItemStack> switchItems(LinkedHashMap<Integer, ItemStack> items){
		List<Integer> indexes = new ArrayList<>(items.keySet());
		List<ItemStack> stacks = new ArrayList<>(items.values());
		LinkedHashMap<Integer, ItemStack> newItems = new LinkedHashMap<>();
		for(int i = 0; i < 14; i++){
			int index = indexes.get(0);
			if(i + 1 < indexes.size()){
				index = indexes.get(i + 1);
			}
			ItemStack item = stacks.get(i);
			newItems.put(index, item);
		}
		return newItems;
	}


	public String convertSecs(long secs) {
		long h = secs / 3600, i = secs - h * 3600, m = i / 60, s = i - m * 60;
		String timeF = "";

		if (h < 10) {
			timeF = timeF + "0";
		}
		timeF = timeF + h + ":";
		if (m < 10) {
			timeF = timeF + "0";
		}
		timeF = timeF + m + ":";
		if (s < 10) {
			timeF = timeF + "0";
		}
		timeF = timeF + s;

		return timeF;
	}

}
