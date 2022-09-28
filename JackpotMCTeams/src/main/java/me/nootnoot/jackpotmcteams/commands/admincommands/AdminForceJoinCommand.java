package me.nootnoot.jackpotmcteams.commands.admincommands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminForceJoinCommand extends SubCommand {
	@Override
	public String getName() {
		return "forcejoin";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teamsadmin forcejoin [team] OR /teamsadmin forcejoin [player] [team]");
	}

	@Override
	public String GetDescription() {
		return "Forces you or someone else to join a team.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length < 2 || args.length > 3){
			p.sendMessage(GetSyntax());
			return;
		}

		if(args.length == 2){
			Team currentTeam = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
			if(currentTeam != null){
				currentTeam.LeaveTeam(p);
			}

			Team team = JackpotMCTeams.getInstance().getTeamManager().getTeam(args[1]);
			if(team == null){
				p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Team does not exist."));
				return;
			}

			team.JoinTeam(p);
		}else {
			Player target = Bukkit.getPlayer(args[1]);
			if(target == null){
				p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Player does not exist."));
				return;
			}

			Team currentTeam = JackpotMCTeams.getInstance().getTeamManager().hasTeam(target);
			if(currentTeam != null){
				currentTeam.LeaveTeam(target);
			}

			Team team = JackpotMCTeams.getInstance().getTeamManager().getTeam(args[2]);
			if(team == null){
				p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Team does not exist."));
				return;
			}

			TeamPlayer teamPlayer = null;
			for(TeamPlayer player : team.getPlayers()){
				if(player.getPlayer() != null){
					if(player.getPlayer().equals(p)){
						teamPlayer = player;
					}
				}
			}
			if(teamPlayer == null){
				p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Something went wrong."));
				return;
			}

			if(teamPlayer.getRole() == TeamRoles.OWNER){
				p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You cannot kick the Owner of a team!"));
				return;
			}

			p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You've successfully made " + target.getName() + " join the team &n" + team.getName()));


			team.JoinTeam(target);
		}

	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if(args.length == 2){
			return List.of("[player]", "[team name]");
		}else if(args.length == 3){
			ArrayList<String> names = new ArrayList<>();
			for(Team team : JackpotMCTeams.getInstance().getTeamStorage().getTeams().values()){
				names.add(team.getName());
			}
			return names;
		}
		return null;
	}
}
