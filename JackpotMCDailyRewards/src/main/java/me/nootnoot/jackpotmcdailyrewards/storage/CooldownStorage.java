package me.nootnoot.jackpotmcdailyrewards.storage;

import com.google.common.reflect.TypeToken;
import me.nootnoot.framework.storagesystem.GsonFactory;
import me.nootnoot.jackpotmcdailyrewards.JackpotMCDailyRewards;
import me.nootnoot.jackpotmcdailyrewards.entities.RewardCooldown;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CooldownStorage {
	private ConcurrentMap<String, RewardCooldown> cooldowns = new ConcurrentHashMap<>();

	public CooldownStorage(){
		try {
			load();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void save() {
		File f = new File(JackpotMCDailyRewards.getInstance().getDataFolder(), "cooldowns.json");
		try (FileWriter fileWriter = new FileWriter(f)) {
			fileWriter.write(GsonFactory.getPrettyGson().toJson(cooldowns));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void load() throws IOException {
		File f = new File(JackpotMCDailyRewards.getInstance().getDataFolder(), "cooldowns.json");
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		} else try (BufferedReader fileReader = new BufferedReader(new FileReader(f))) {
			ConcurrentHashMap<String, RewardCooldown> objectMap = GsonFactory.getCompactGson().fromJson(fileReader, new TypeToken<ConcurrentHashMap<String, RewardCooldown>>() {
			}.getType());
			cooldowns = Objects.requireNonNullElseGet(objectMap, ConcurrentHashMap::new);
		}
	}

	public long getCooldown(String uuid){
		for(RewardCooldown rewardCooldown : cooldowns.values()){
			if(rewardCooldown.getUUID().equalsIgnoreCase(uuid)){
				return rewardCooldown.getCooldown();
			}
		}
		return 0;
	}

	public void addCooldown(String uuid){
		String id = UUID.randomUUID().toString();
		RewardCooldown rewardCooldown = new RewardCooldown(uuid, id, 60*60*24L);
		cooldowns.put(id, rewardCooldown);
	}

	public RewardCooldown getRewardCooldown(String uuid) {
		for (RewardCooldown rewardCooldown : cooldowns.values()) {
			if (rewardCooldown.getUUID().equalsIgnoreCase(uuid)) {
				return rewardCooldown;
			}
		}
		return null;
	}

	public void minCooldown(String uuid){
		if(getCooldown(uuid) - 1 == 0){
			cooldowns.remove(getRewardCooldown(uuid).getId());
			return;
		}
		getRewardCooldown(uuid).setCooldown(getRewardCooldown(uuid).getCooldown() - 1);
	}

	public List<RewardCooldown> getCooldowns(){
		return cooldowns.values().stream().toList();
	}

}
