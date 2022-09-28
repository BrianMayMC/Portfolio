package me.nootnoot.jackpotmcteams.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.entities.Team;
import org.bukkit.OfflinePlayer;

public class Placeholders extends PlaceholderExpansion {

	@Override
	public String getIdentifier() {
		return "teams";
	}

	@Override
	public String getAuthor() {
		return "noot noot";
	}

	@Override
	public String getVersion() {
		return JackpotMCTeams.getInstance().getDescription().getVersion();
	}

	@Override
	public String onRequest(OfflinePlayer player, String params) {
		if (params.equalsIgnoreCase("kills")) {
			return String.valueOf(JackpotMCTeams.getInstance().getTeamManager().hasTeamOffline(player.getName()).getKills());
		}else if (params.equalsIgnoreCase("name")) {
			Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeamOffline(player.getName());
			if(team != null){
				return team.getName();
			}
			return "";
		}
		return null;
	}

}
