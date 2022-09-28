package me.nootnoot.jackpotmcteams.listeners;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TeamChatListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		if(JackpotMCTeams.getInstance().getTeamManager().getPlayersInTeamChat().contains(e.getPlayer())){
			Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(e.getPlayer());

			e.setCancelled(true);

			String message = null;
			for(TeamPlayer teamPlayer : team.getPlayers()) {
				if (teamPlayer.getPlayer() != null) {
					if(teamPlayer.getPlayer().equals(e.getPlayer())){
						if (teamPlayer.getPlayer().equals(e.getPlayer())) {
							if (teamPlayer.getRole() == TeamRoles.OWNER)
								message = Utils.c("&4&l[Team] &c&l⭐⭐" + e.getPlayer().getName() + ":&d " + e.getMessage());
							else if (teamPlayer.getRole() == TeamRoles.OFFICER) {
								message = Utils.c("&4&l[Team] &c&l⭐" + e.getPlayer().getName() + ":&d " + e.getMessage());
							} else {
								message = Utils.c("&4&l[Team] &c&l" + e.getPlayer().getName() + ":&d " + e.getMessage());
							}
						}
					}
				}
			}

			if(message == null){
				return;
			}
			for(TeamPlayer player : team.getPlayers()){
				if(player.getPlayer() != null){
					player.getPlayer().sendMessage(message);
				}
			}
		}
	}
}
