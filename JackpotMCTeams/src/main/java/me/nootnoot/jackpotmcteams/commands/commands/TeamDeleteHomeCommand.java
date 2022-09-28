package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamDeleteHomeCommand extends SubCommand {


	@Override
	public String getName() {
		return "delhome";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams delhome");
	}

	@Override
	public String GetDescription() {
		return "Deletes your teams home.";
	}

	@Override
	public void execute(Player p, String[] args) {
		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if(team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You must be in a team to set your home!"));
			return;
		}
		if(team.getTeamHome() == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Your team does not have a home set."));
		}

		if(team.getRole(p) != TeamRoles.OWNER){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You do not have permission to set your teams home."));
			return;
		}

		team.setTeamHome(null);

		for(TeamPlayer player : team.getPlayers()){
			player.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + "&a " + p.getName() + " has deleted your teams home."));
		}
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		return null;
	}
}
