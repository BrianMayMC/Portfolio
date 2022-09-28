package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamLeaveCommand extends SubCommand {
	@Override
	public String getName() {
		return "leave";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams leave");
	}

	@Override
	public String GetDescription() {
		return "Leaves your current team.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 1){
			p.sendMessage(GetSyntax());
			return;
		}

		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if(team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You are not in a team!"));
			return;
		}

		if(team.getOwnerUUID().equals(p.getUniqueId())){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You cannot leave the team if you're the Owner! Please disband your team!"));
			return;
		}

		team.LeaveTeam(p);
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		return null;
	}
}
