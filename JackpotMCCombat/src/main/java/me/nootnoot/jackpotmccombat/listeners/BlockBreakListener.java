package me.nootnoot.jackpotmccombat.listeners;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.utils.Utils;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class BlockBreakListener implements Listener {


	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){

		if(JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(e.getPlayer()) == null) return;

		Set<Location> locations = Utils.getPreviousUpdates().get(e.getPlayer().getUniqueId());

		if(!locations.contains(e.getBlock().getLocation())) return;

		Location location = new Location(e.getPlayer().getWorld(), 0,0,0);
		location.setYaw(e.getPlayer().getLocation().getYaw() + 180);
		e.getPlayer().teleport(location);

		new BukkitRunnable(){
			@Override
			public void run(){
				Utils.executeTask(e.getPlayer());
			}
		}.runTaskLater(JackpotMCCombat.getInstance(), 10L);
	}

	@EventHandler
	public void blockInteract(PlayerInteractEvent e){

		if(JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(e.getPlayer()) == null) return;

		if(e.getClickedBlock() == null) return;


		Set<Location> locations = Utils.getPreviousUpdates().get(e.getPlayer().getUniqueId());

		if(!locations.contains(e.getClickedBlock().getLocation())) return;

		Location location = new Location(e.getPlayer().getWorld(), e.getPlayer().getLocation().getX(), e.getPlayer().getLocation().getY(), e.getPlayer().getLocation().getZ());
		location.setYaw(e.getPlayer().getLocation().getYaw() + 180);
		e.getPlayer().teleport(location);

		new BukkitRunnable(){
			@Override
			public void run(){
				Utils.executeTask(e.getPlayer());
			}
		}.runTaskLater(JackpotMCCombat.getInstance(), 10L);

	}
}
