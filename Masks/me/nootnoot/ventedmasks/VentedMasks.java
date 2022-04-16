package me.nootnoot.ventedmasks;

import lombok.Getter;
import me.nootnoot.ventedmasks.commands.GiveMask;
import me.nootnoot.ventedmasks.commands.GiveUpgrade;
import me.nootnoot.ventedmasks.entities.MaskManager;
import me.nootnoot.ventedmasks.entities.UpgradeItem;
import me.nootnoot.ventedmasks.listeners.PlayerLeaveListener;
import me.nootnoot.ventedmasks.listeners.masks.*;
import me.nootnoot.ventedmasks.listeners.EquipListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class VentedMasks extends JavaPlugin {

	@Getter
	private static VentedMasks instance;

	@Getter
	private static MaskManager maskManager;


	@Override
	public void onEnable() {
		// Plugin startup logic
		instance = this;
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		maskManager = new MaskManager();

		getCommand("mm").setExecutor(new GiveMask());
		getCommand("giveupgrade").setExecutor(new GiveUpgrade());

		getServer().getPluginManager().registerEvents(new ChickenMaskListener(), this);
		getServer().getPluginManager().registerEvents(new EndermiteMaskListener(), this);
		getServer().getPluginManager().registerEvents(new EquipListener(), this);
		getServer().getPluginManager().registerEvents(new CowMaskListener(), this);
		getServer().getPluginManager().registerEvents(new UndeadMaskListener(), this);
		getServer().getPluginManager().registerEvents(new SilverfishMaskListener(), this);
		getServer().getPluginManager().registerEvents(new SkeletonHorseMaskListener(), this);
		getServer().getPluginManager().registerEvents(new SquidMaskListener(), this);
		getServer().getPluginManager().registerEvents(new UpgradeItem(), this);

		getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), this);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.hasPotionEffect(PotionEffectType.getByName("HEALTH_BOOST"))){
					p.removePotionEffect(PotionEffectType.getByName("HEALTH_BOOST"));
				}
				if(p.getInventory().contains(EquipListener.returnPickaxe())){
					p.getInventory().remove(EquipListener.returnPickaxe());
				}
				SilverfishMaskListener.removePickaxe(p);
			}

			Bukkit.getScheduler().cancelAllTasks();
		}
}
