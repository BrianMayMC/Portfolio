package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamAcceptCommand extends SubCommand {
	@Override
	public String getName() {
		return "accept";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() +" &c /teams accept [name]");
	}

	@Override
	public String GetDescription() {
		return "Accepts an invite from a team.";
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
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Team does not exist."));
			return;
		}

		if(!team.getPendingInvites().contains(p.getUniqueId())){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You are not invited to this team."));
			return;
		}

		if(team.getPlayers().size() >= Utils.getTeamSize()){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c This team has the max amount of team members!!"));
			return;
		}

		team.acceptInvite(new TeamPlayer(p.getUniqueId(), TeamRoles.MEMBER, p.getName()));
		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You have successfully joined " + team.getName() + "!"));

		for(TeamPlayer teamPlayer : team.getPlayers()){
			if(teamPlayer.getPlayer() != null) {
				teamPlayer.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + " &a " + p.getName() + " has successfully joined your team!"));
			}
		}

	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if(args.length == 2){
			return List.of("[Team name]");
		}
		return null;
	}
}
