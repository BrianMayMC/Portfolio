package me.nootnoot.jackpotmccombat.listeners;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnderpearlListener implements Listener {

	private final Map<Player, Integer> playerOnEnderpearlCooldown = new HashMap<>();

	private final List<Player> players = new ArrayList<>();

	@EventHandler
	public void onEnderpearl(ProjectileHitEvent e){
		if(!(e.getEntity().getShooter() instanceof Player p)) return;
		if(!(e.getEntityType() == EntityType.ENDER_PEARL)) return;
		if(JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(p) == null) return;
		//if enderpearl was thrown on border block
		if(e.getHitBlock() != null) {
			System.out.println(Utils.isPvpEnabledAt(e.getHitBlock().getLocation()));
			System.out.println(Utils.isPvPDeactivatedNear(e.getHitBlock().getLocation()));
			if (Utils.isPvpEnabledAt(e.getHitBlock().getLocation()) && Utils.isPvPDeactivatedNear(e.getHitBlock().getLocation())) {
				players.add(p);
				e.setCancelled(true);
				new BukkitRunnable(){
					@Override
					public void run(){
						players.remove(p);
					}
				}.runTaskLater(JackpotMCCombat.getInstance(), 5L);
			}
		}
	}

	@EventHandler
	public void test(PlayerTeleportEvent e){
		if(players.contains(e.getPlayer())) e.setCancelled(true);
	}

	@EventHandler
	public void onThrow(ProjectileLaunchEvent e){
		if(!(e.getEntity().getShooter() instanceof Player p)) return;
		if(!(e.getEntityType() == EntityType.ENDER_PEARL)) return;
		if(!(JackpotMCCombat.getInstance().isPearl())) return;
		if(JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(p) == null) return;

		if(playerOnEnderpearlCooldown.containsKey(p)){
			e.setCancelled(true);
			p.sendMessage(Utils.c(JackpotMCCombat.getInstance().getConfig().getString("enderpearl-cooldown-message").replace("%seconds%", String.valueOf(playerOnEnderpearlCooldown.get(p)))));
			return;
		}
		playerOnEnderpearlCooldown.put(p, Utils.enderpearl_cooldown);
		new BukkitRunnable(){
			@Override
			public void run(){
				if(playerOnEnderpearlCooldown.containsKey(p)){
					if((playerOnEnderpearlCooldown.get(p) - 1) <= 0){
						playerOnEnderpearlCooldown.remove(p);
						cancel();
					}else{
						playerOnEnderpearlCooldown.put(p, playerOnEnderpearlCooldown.get(p) - 1);
						Utils.SendActionBar(p, Utils.c(JackpotMCCombat.getInstance().getConfig().getString("enderpearl-actionbar").replace("%time%", String.valueOf(playerOnEnderpearlCooldown.get(p)))));
					}
				}else{
					cancel();
				}
			}
		}.runTaskTimer(JackpotMCCombat.getInstance(), 20L, 20L);
	}
}
