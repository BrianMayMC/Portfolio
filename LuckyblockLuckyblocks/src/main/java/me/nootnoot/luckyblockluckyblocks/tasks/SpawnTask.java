package me.nootnoot.luckyblockluckyblocks.tasks;

import me.nootnoot.luckyblockluckyblocks.LuckyblockLuckyblocks;
import me.nootnoot.luckyblockluckyblocks.commands.LuckyblockCommand;
import me.nootnoot.luckyblockluckyblocks.entities.Luckyblock;
import me.nootnoot.luckyblockluckyblocks.enums.Rarity;
import me.nootnoot.luckyblockluckyblocks.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnTask extends BukkitRunnable {

	private final Luckyblock luckyblock;

	public SpawnTask(Luckyblock luckyblock) {
		this.luckyblock = luckyblock;
	}

	@Override
	public void run(){

		new BukkitRunnable(){
			@Override
			public void run(){
				luckyblock.getLocation().getBlock().setType(Material.SPONGE);
				luckyblock.getLocation().getBlock().setMetadata("luckyblock", new FixedMetadataValue(LuckyblockLuckyblocks.getInstance(), true));
			}
		}.runTaskLater(LuckyblockLuckyblocks.getInstance(), 5L);

		String hologram = "&eDefault Hologram";

		switch(luckyblock.getRarity()){
			case COMMON -> hologram	= LuckyblockLuckyblocks.getInstance().getConfig().getString("tiers.common.name");
			case UNCOMMON -> hologram = LuckyblockLuckyblocks.getInstance().getConfig().getString("tiers.uncommon.name");
			case RARE -> hologram = LuckyblockLuckyblocks.getInstance().getConfig().getString("tiers.rare.name");
		}

		Location location = new Location(luckyblock.getLocation().getWorld(), luckyblock.getLocation().getX() + 0.5, luckyblock.getLocation().getY(), luckyblock.getLocation().getZ() + 0.5);
		ArmorStand armorstand = (ArmorStand)luckyblock.getLocation().getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
		armorstand.setInvisible(true);
		armorstand.setInvulnerable(true);
		armorstand.setGravity(false);
		armorstand.setCustomNameVisible(true);
		armorstand.setCustomName(Utils.c(hologram));

		luckyblock.setHologram(armorstand);

		LuckyblockLuckyblocks.getInstance().getManager().addLuckyblock(luckyblock);

		System.out.println(luckyblock.getLocation().getBlock().getType());
	}
}
