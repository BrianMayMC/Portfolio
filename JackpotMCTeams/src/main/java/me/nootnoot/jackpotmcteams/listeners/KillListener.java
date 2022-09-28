package me.nootnoot.jackpotmcteams.listeners;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.entities.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class KillListener implements Listener {

	private final ArrayList<String[]> playersOnCooldown = new ArrayList<>();

	@EventHandler
	public void onKill(PlayerDeathEvent e){
		Player p = e.getEntity();
		if(e.getEntity().getKiller() == null) return;
		Player target = e.getEntity().getKiller();

		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(target);
		if(team == null){
			return;
		}

		String[] players = new String[]{p.getName(), target.getName()};

		if(playersOnCooldown.contains(players)){
			return;
		}
		playersOnCooldown.add(players);
		new BukkitRunnable(){
			@Override
			public void run(){
				if(playersOnCooldown.contains(players)){
					playersOnCooldown.remove(players);
				}else{
					cancel();
				}
			}
		}.runTaskLater(JackpotMCTeams.getInstance(), 60L * 20L);

		team.AddKills(1);
	}
}
