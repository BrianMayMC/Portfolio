package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamBalanceCommand extends SubCommand {
	@Override
	public String getName() {
		return "balance";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams balance");
	}

	@Override
	public String GetDescription() {
		return "Displays your teams balance.";
	}

	@Override
	public void execute(Player p, String[] args) {
		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if (team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You are not in a team."));
			return;
		}

		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a Your team currently has $" + team.getBalance()));
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		return null;
	}
}
