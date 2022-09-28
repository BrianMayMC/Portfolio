package me.nootnoot.luckyblockluckyblocks.listeners;

import me.nootnoot.luckyblockluckyblocks.LuckyblockLuckyblocks;
import me.nootnoot.luckyblockluckyblocks.entities.Luckyblock;
import me.nootnoot.luckyblockluckyblocks.entities.LuckyblockItem;
import me.nootnoot.luckyblockluckyblocks.enums.Rarity;
import me.nootnoot.luckyblockluckyblocks.tasks.SpawnTask;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LuckyblockMine implements Listener {


	@EventHandler
	public void onMine(BlockBreakEvent e){
		if(!(e.getBlock().getLocation().getY() <= LuckyblockLuckyblocks.getInstance().getConfig().getInt("y-level"))) return;
		if(e.getBlock().hasMetadata("luckyblock")) return;


		double commonper = LuckyblockLuckyblocks.getInstance().getConfig().getDouble("tiers.common.spawn-chance");
		double uncommonper = LuckyblockLuckyblocks.getInstance().getConfig().getDouble("tiers.uncommon.spawn-chance");
		double rareper = LuckyblockLuckyblocks.getInstance().getConfig().getDouble("tiers.rare.spawn-chance");

		int random = ThreadLocalRandom.current().nextInt(101);
		if(random < rareper){
			new SpawnTask(new Luckyblock(Rarity.RARE, e.getBlock().getLocation())).run();
		}else if(random < uncommonper){
			new SpawnTask(new Luckyblock(Rarity.UNCOMMON, e.getBlock().getLocation())).run();
		}else if(random < commonper){
			new SpawnTask(new Luckyblock(Rarity.COMMON, e.getBlock().getLocation())).run();
		}
	}

	@EventHandler
	public void onLuckyblockMine(BlockBreakEvent e){
		if(!(e.getBlock().hasMetadata("luckyblock"))) return;

		e.setCancelled(true);
		e.getBlock().setType(Material.AIR);

		Luckyblock l = LuckyblockLuckyblocks.getInstance().getManager().getLuckyblockAtLocation(e.getBlock().getLocation());
		List<LuckyblockItem> items = LuckyblockLuckyblocks.getInstance().getManager().getItems(l.getRarity());

		for(LuckyblockItem item : items){
			int random = ThreadLocalRandom.current().nextInt(101);
			if(random < item.getChance()){
				e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), item.getItem());
			}
		}
		e.getBlock().getLocation().getWorld().playSound(e.getBlock().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 4, 1);
		e.getBlock().getLocation().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, e.getBlock().getLocation(), 100);
		l.getHologram().remove();
		LuckyblockLuckyblocks.getInstance().getManager().getLuckyblocks().remove(l);
		e.getBlock().removeMetadata("luckyblock", LuckyblockLuckyblocks.getInstance());
	}
}
