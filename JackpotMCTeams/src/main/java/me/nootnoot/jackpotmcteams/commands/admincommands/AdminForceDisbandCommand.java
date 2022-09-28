package me.nootnoot.jackpotmcteams.commands.admincommands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminForceDisbandCommand extends SubCommand {
	@Override
	public String getName() {
		return "forcedisband";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teamsadmin forcedisband [team] OR /teamsadmin forcedisband [player]");
	}

	@Override
	public String GetDescription() {
		return "Forces a team to be disbanded.";
	}

	@Override
	public void execute(Player p, String[] args) {

		if(args.length != 2){
			p.sendMessage(GetSyntax());
			return;
		}

		Team team = JackpotMCTeams.getInstance().getTeamManager().getTeam(args[1]);
		if(team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c " + args[1] + " is not a valid team"));
			return;
		}

		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You have successfully disbanded &l&n" + team.getName()));
		JackpotMCTeams.getInstance().getTeamManager().disbandTeam(team);
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if(args.length == 2){
			List<String> names = new ArrayList<>();
			for(Team team : JackpotMCTeams.getInstance().getTeamStorage().getTeams().values()){
				names.add(team.getName());
			}
			return names;
		}
		return null;
	}
}