package me.nootnoot.jackpotmcteams.listeners;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class PvpListener implements Listener {


	@EventHandler
	public void onHit(EntityDamageByEntityEvent e){
		if(!(e.getDamager() instanceof Player p)) return;
		if(!(e.getEntity() instanceof Player target)) return;

		if(JackpotMCTeams.getInstance().getPlayerTeleportQueue().contains(target)){
			target.sendMessage(Utils.c(Utils.getPREFIX() + "&c Teleport cancelled..."));
			JackpotMCTeams.getInstance().getPlayerTeleportQueue().remove(target);
		}
		if(JackpotMCTeams.getInstance().getPlayerTeleportQueue().contains(p)){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Teleport cancelled..."));
			JackpotMCTeams.getInstance().getPlayerTeleportQueue().remove(p);
		}
		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);

		if(team == null){
			return;
		}

		for(TeamPlayer player : team.getPlayers()){
			if(player.getPlayerUUID().equals(target.getUniqueId())){
				e.setCancelled(true);
				return;
			}
		}

	}
}
