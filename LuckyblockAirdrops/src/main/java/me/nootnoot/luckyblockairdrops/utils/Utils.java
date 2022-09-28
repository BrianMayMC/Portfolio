package me.nootnoot.luckyblockairdrops.utils;

import me.nootnoot.luckyblockairdrops.LuckyblockAirdrops;
import me.nootnoot.luckyblockairdrops.enums.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

	public static String c(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public static List<String> cL(List<String> s){
		List<String> newList = new ArrayList<>();
		for(String string : s){
			newList.add(c(string));
		}
		return newList;
	}

	public static Location getRandomLocation() {
		int i = 0;
		Location loc = null;
		while (i < 1) {
			World world = LuckyblockAirdrops.getInstance().getServer().getWorld(LuckyblockAirdrops.getInstance().getConfig().getString("airdrops.spawn-radius.world"));
			int x = LuckyblockAirdrops.getInstance().getConfig().getInt("airdrops.spawn-radius.x");
			int z = LuckyblockAirdrops.getInstance().getConfig().getInt("airdrops.spawn-radius.z");
			int randomX = ThreadLocalRandom.current().nextInt(x);
			int randomZ = ThreadLocalRandom.current().nextInt(z);

			int nnX = ThreadLocalRandom.current().nextInt(2);
			if (nnX == 1) {
				x = -x;
			}
			int nnZ = ThreadLocalRandom.current().nextInt(2);
			if (nnZ == 1) {
				z = -z;
			}
			loc = new Location(world, randomX, 0, randomZ);
			loc.setY(world.getHighestBlockYAt(x, z) + 1);
			if(!(loc.getBlock().isLiquid())){
				return loc;
			}
			i++;
		}
		return loc.add(0,30,0);
	}

	public static Rarity getRandomRarity(){
		int commonChance = LuckyblockAirdrops.getInstance().getConfig().getInt("airdrops.COMMON.spawn-chance");
		int uncommonChance = LuckyblockAirdrops.getInstance().getConfig().getInt("airdrops.UNCOMMON.spawn-chance") + commonChance;

		int random = ThreadLocalRandom.current().nextInt(101);
		if(random < commonChance){
			return Rarity.COMMON;
		}else if(random > commonChance && random < uncommonChance){
			return Rarity.UNCOMMON;
		}else{
			return Rarity.RARE;
		}
	}
}
