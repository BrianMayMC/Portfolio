package me.nootnoot.jackpotmcdailyrewards;

import lombok.Getter;
import me.nootnoot.framework.basesystem.BasePlugin;
import me.nootnoot.framework.utils.Cooldown;
import me.nootnoot.jackpotmcdailyrewards.commands.AddItemCommand;
import me.nootnoot.jackpotmcdailyrewards.entities.RewardCooldown;
import me.nootnoot.jackpotmcdailyrewards.managers.RewardsManager;
import me.nootnoot.jackpotmcdailyrewards.storage.CooldownStorage;
import me.nootnoot.jackpotmcdailyrewards.storage.RewardsStorage;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public final class JackpotMCDailyRewards extends BasePlugin {

	@Getter private static JackpotMCDailyRewards instance;

	@Getter private RewardsManager rewardsManager;
	@Getter private RewardsStorage rewardsStorage;
	@Getter private CooldownStorage cooldownStorage;


	@Override
	public void onEnable() {
		// Plugin startup logic
		super.onEnable();
		instance = this;

		rewardsStorage = new RewardsStorage();
		cooldownStorage = new CooldownStorage();
		rewardsManager = new RewardsManager(rewardsStorage, cooldownStorage);

		new BukkitRunnable(){
			@Override
			public void run(){
				if(cooldownStorage.getCooldowns() == null) return;
				for(RewardCooldown rewardCooldown : cooldownStorage.getCooldowns()){
					cooldownStorage.minCooldown(rewardCooldown.getUUID());
				}
			}
		}.runTaskTimer(this, 20L, 20L);

		getCommand("dailyrewards").setExecutor(new AddItemCommand());
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		rewardsStorage.save();
		cooldownStorage.save();
	}
}
