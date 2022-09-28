package me.nootnoot.jackpotmccombat.listeners.blocked;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.raid.RaidSpawnWaveEvent;
import org.bukkit.event.raid.RaidTriggerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockItemsListener implements Listener {


	@EventHandler
	public void craft(CraftItemEvent e){
		if(e.getRecipe().getResult().getType() == Material.END_CRYSTAL || e.getRecipe().getResult().getType() == Material.RESPAWN_ANCHOR){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void use(PlayerInteractEvent e){
		if(e.getClickedBlock() == null) return;
		Block b = e.getClickedBlock();
		if(b.getType() == Material.END_CRYSTAL || b.getType() == Material.RESPAWN_ANCHOR){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void arrow(ProjectileHitEvent e){
		if(!(e.getEntity() instanceof Arrow a)) return;
		if(!(e.getEntity().getShooter() instanceof Player p)) return;
		if(a.getCustomEffects().isEmpty()) return;
		if(JackpotMCCombat.getInstance().getListContainer().getArrowPlayers().containsKey(p)){
			a.getCustomEffects().clear();
		}else{
			JackpotMCCombat.getInstance().getListContainer().getArrowPlayers().put(p, 5L);
			new BukkitRunnable(){
				@Override
				public void run(){
					JackpotMCCombat.getInstance().getListContainer().getArrowPlayers().remove(p);
				}
			}.runTaskLater(JackpotMCCombat.getInstance(), 100L);
		}
	}

	@EventHandler
	public void chorus(PlayerTeleportEvent e){
		if(e.getCause() == PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void totem(EntityResurrectEvent e){
		if(!(e.getEntity() instanceof Player p)) return;
		if(JackpotMCCombat.getInstance().getListContainer().getTotemPlayers().containsKey(p)){
			e.setCancelled(true);
		}else{
			JackpotMCCombat.getInstance().getListContainer().getTotemPlayers().put(p, 5L);
			new BukkitRunnable(){
				@Override
				public void run(){
					JackpotMCCombat.getInstance().getListContainer().getTotemPlayers().remove(p);
				}
			}.runTaskLater(JackpotMCCombat.getInstance(), 600L);
		}
	}

	@EventHandler
	public void raid(RaidTriggerEvent e){
		e.setCancelled(true);
	}

	@EventHandler
	public void death(PlayerDeathEvent e){
		if(e.getEntity().getKiller() == null) return;
		Player p = e.getEntity();
		Player killer = e.getEntity().getKiller();
		System.out.println(JackpotMCCombat.getInstance().getEcon().getBalance(p));
		JackpotMCCombat.getInstance().getEcon().depositPlayer(killer, JackpotMCCombat.getInstance().getEcon().getBalance(p));
		JackpotMCCombat.getInstance().getEcon().withdrawPlayer(p, JackpotMCCombat.getInstance().getEcon().getBalance(p));
	}

	@EventHandler
	public void boom(BlockExplodeEvent e){
		e.setCancelled(true);
	}

	@EventHandler
	public void boom2(ExplosionPrimeEvent e){
		if(e.getEntity().getType() == EntityType.CREEPER)return;
		e.setCancelled(true);
	}

	@EventHandler
	public void boom3(EntityExplodeEvent e){
		if(e.getEntity().getType() == EntityType.CREEPER)return;
		e.setCancelled(true);
	}

	@EventHandler
	public void craft2(CraftItemEvent e){
		for(ItemStack item : e.getInventory().getMatrix()){
			if(item.getType() == Material.END_CRYSTAL){
				e.setCancelled(true);
			}
		}
	}
}
