package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamSetHomeCommand extends SubCommand {
	@Override
	public String getName() {
		return "sethome";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams sethome");
	}

	@Override
	public String GetDescription() {
		return "Sets the home of your team to your current location.";
	}

	@Override
	public void execute(Player p, String[] args) {
		Location location = p.getLocation();

		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if(team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You must be in a team to set your home!"));
			return;
		}


		if(team.getRole(p) != TeamRoles.OWNER){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You do not have permission to set your teams home."));
			return;
		}
		team.setTeamHome(location);
		for(TeamPlayer player : team.getPlayers()){
			if(player.getPlayer() != null)
				player.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + "&a " + p.getName() + " has set your team's home to: x:"  + Math.round(location.getX()) + " y: " + Math.round(location.getY()) + " z: " + Math.round(location.getZ())));
		}
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		return null;
	}
}
