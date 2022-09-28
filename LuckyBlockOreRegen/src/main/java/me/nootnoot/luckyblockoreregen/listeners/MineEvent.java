package me.nootnoot.luckyblockoreregen.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import de.tr7zw.nbtapi.NBTBlock;
import me.nootnoot.luckyblockoreregen.LuckyBlockOreRegen;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MineEvent implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent e){
		NBTBlock block = new NBTBlock(e.getBlock());
		if(block.getData().hasKey("customblock")){
			e.setCancelled(true);

			LuckyBlockOreRegen.getInstance().getRegenBlockManager().getMinedBlocks().put(e.getBlock().getLocation(), e.getBlock().getType());

			e.getPlayer().giveExp(e.getExpToDrop());
			e.getPlayer().getInventory().addItem(e.getBlock().getDrops(e.getPlayer().getInventory().getItemInMainHand()).toArray(new ItemStack[0]));
			e.getBlock().setType(Material.BEDROCK);
			e.getBlock().getWorld().playSound(e.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
		}
	}

	@EventHandler
	public void br(BlockBreakEvent e){
		ApplicableRegionSet regions = getRegions(e.getPlayer().getLocation());
		List<String> config = LuckyBlockOreRegen.getInstance().getConfig().getStringList("regions");
		for(ProtectedRegion s : regions){
			if(config.contains(s.getId())){
				e.setCancelled(true);
			}
		}
	}


	private ApplicableRegionSet getRegions(Location loc){
		com.sk89q.worldedit.util.Location loc1 = new com.sk89q.worldedit.util.Location(BukkitAdapter.adapt(loc.getWorld()), loc.getX(), loc.getY(), loc.getZ(), 0, 0);
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionQuery query = container.createQuery();
		return query.getApplicableRegions(loc1);
	}
}

