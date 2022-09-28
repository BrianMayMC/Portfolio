package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamJoinCommand extends SubCommand {

	@Override
	public String getName() {
		return "join";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + " &c/teams join [name]");
	}

	@Override
	public String GetDescription() {
		return "Allows you to join a team!";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 2){
			p.sendMessage(GetSyntax());
			return;
		}
		Team currentTeam = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if(currentTeam != null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You are already on a team!"));
			return;
		}
		Team team = JackpotMCTeams.getInstance().getTeamManager().getTeam(args[1]);
		if(team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Team does not exist!"));
			return;
		}

		if(team.getPlayers().size() >= Utils.getTeamSize()){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c This team has the max amount of team members!"));
			return;
		}

		if(team.getPendingInvites().contains(p.getUniqueId()) || team.isOpen()) {
			team.JoinTeam(p);
			return;
		}

		p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You are not invited to this team and/or the team is not open!"));

	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if(args.length == 2){
			ArrayList<String> names = new ArrayList<>();
			for(Team team : JackpotMCTeams.getInstance().getTeamStorage().getTeams().values()){
				names.add(team.getName());
			}
			return names;
		}
		return null;
	}
}
