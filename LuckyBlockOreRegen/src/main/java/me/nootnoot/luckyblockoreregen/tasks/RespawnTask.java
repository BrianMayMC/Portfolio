package me.nootnoot.luckyblockoreregen.tasks;

import me.nootnoot.luckyblockoreregen.LuckyBlockOreRegen;
import me.nootnoot.luckyblockoreregen.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnTask extends BukkitRunnable {

	private final FileConfiguration config = LuckyBlockOreRegen.getInstance().getConfig();
	@Override
	public void run(){
		if(LuckyBlockOreRegen.getInstance().getRegenBlockManager().getMinedBlocks().isEmpty()){
			return;
		}
		LuckyBlockOreRegen.getInstance().getRegenBlockManager().RestoreBlocks();
		for(Player p : Bukkit.getOnlinePlayers()){
			p.sendTitle(Utils.c(config.getString("title-announcement")),
					Utils.c(config.getString("subtitle-announcement")), config.getInt("fade-in"), config.getInt("show-time"), config.getInt("fade-out"));
		    Utils.cL(p, config.getStringList("text-announcement"));
		}
	}
}
