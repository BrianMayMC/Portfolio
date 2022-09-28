package me.nootnoot.jackpotmcteams.listeners;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(e.getPlayer());
		if(team == null) return;
		TeamPlayer teamPlayer = team.getPlayer(e.getPlayer());
		if(teamPlayer != null) {
			if (!(teamPlayer.getPlayerName().equalsIgnoreCase(e.getPlayer().getName()))) {
				teamPlayer.setPlayerName(e.getPlayer().getName());
			}
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		JackpotMCTeams.getInstance().getTeamManager().getPlayersInTeamChat().remove(e.getPlayer());
	}
}
