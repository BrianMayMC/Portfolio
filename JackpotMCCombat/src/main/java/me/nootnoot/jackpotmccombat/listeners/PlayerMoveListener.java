package me.nootnoot.jackpotmccombat.listeners;

import lombok.Getter;
import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.utils.Utils;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class PlayerMoveListener implements Listener {
	@Getter private static final List<Player> players = new ArrayList<>();

	@EventHandler
	public void move(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Location from = e.getFrom();
		Location to = e.getTo();
		if(players.contains(p)) return;
		if (JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(p) == null) return;
		if (Utils.isPvpEnabledAt(p.getLocation()) && Utils.isPvPDeactivatedNear(p.getLocation())) {


			Vector direction = null;
			if(from.getY() != to.getY()){
				direction = new Vector(from.getX() - to.getX(), from.getY() - to.getY(), from.getZ() - to.getZ()).multiply(6).setY(0.8).normalize();
			}else{
				direction = new Vector(from.getX() - to.getX(), from.getY() - to.getY(), from.getZ() - to.getZ()).multiply(6).setY(0.8);
			}


			if(from.getX() < 0 && to.getX() < 0 && from.getZ() < 0 && to.getZ() < 0){
				if(from.getX() - to.getX() == 0 && from.getZ() - to.getZ() == 0){
					return;
				}
				if(to.getX() == from.getX() || to.getZ() == from.getZ()){
					return;
				}
			}else if(from.getX() < 0 && to.getX() < 0 && from.getZ() > 0 && to.getZ() > 0){
				if(from.getX() - to.getX() == 0 && from.getZ() - to.getZ() == 0){
					return;
				}
				if(to.getX() == from.getX() || to.getZ() == from.getZ()){
					return;
				}
			}else if(from.getX() > 0 && to.getX() > 0 && from.getZ() < 0 && to.getZ() < 0){
				if(from.getX() - to.getX() == 0 && from.getZ() - to.getZ() == 0){
					return;
				}
				if(to.getX() == from.getX() || to.getZ() == from.getZ()){
					return;
				}
			}else{
				if(from.getX() - to.getX() == 0 && from.getZ() - to.getZ() == 0){
					return;
				}
				if(to.getX() == from.getX() || to.getZ() == from.getZ()){
					return;
				}
			}

			p.setVelocity(direction);
			players.add(p);

			new BukkitRunnable(){
				@Override
				public void run(){
					players.remove(p);
				}
			}.runTaskLater(JackpotMCCombat.getInstance(), 20L);

		}
	}
}
