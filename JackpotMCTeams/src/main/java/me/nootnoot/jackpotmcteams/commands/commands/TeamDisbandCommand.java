package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamDisbandCommand extends SubCommand {


	@Override
	public String getName() {
		return "disband";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + " &c/teams disband");
	}

	@Override
	public String GetDescription() {
		return "Disbands a team.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 1){
			p.sendMessage(GetSyntax());
			return;
		}
		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if(team == null){
			p.sendMessage(Utils.c( Utils.getPREFIX() + " &cYou're not in a team!"));
			return;
		}
		if(!team.getOwnerUUID().equals(p.getUniqueId())){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You do not have permission to disband your team."));
			return;
		}

		JackpotMCTeams.getInstance().getTeamManager().disbandTeam(team);
		p.sendMessage(Utils.c(Utils.getPREFIX() + " &aYou have successfully disbanded your team!"));
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		return null;
	}
}
