package me.nootnoot.jackpotmcteams.commands.commands;

import com.google.common.base.Preconditions;
import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.tasks.UpdateRankTask;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TeamListCommand extends SubCommand {
	@Override
	public String getName() {
		return "list";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams list [page]");
	}

	@Override
	public String GetDescription() {
		return "Shows you a list of all the teams";
	}

	@Override
	public void execute(Player p, String[] args) {
		int page;
		if (args.length > 2) {
			p.sendMessage(GetSyntax());
			return;
		}
		try {
			if(args.length == 2)
				page = Integer.parseInt(args[1]);
			else page = 1;
		} catch (NumberFormatException e) {
			p.sendMessage(Utils.c("&c&l(!)&c Please provide a valid number!"));
			return;
		}
		if(Utils.GetPageNumber(page) > JackpotMCTeams.getInstance().getTeamStorage().getTeams().size()){
			page = 1;
		}

		int pageNumber = Utils.GetPageNumber(page);
		ArrayList<String> message = new ArrayList<>();
		message.add(Utils.c("         &6⛨ Team List&r"));

		new UpdateRankTask().run();

		List<Team> teams = JackpotMCTeams.getInstance().getTeamStorage().getOrderedByOnlinePlayers().values().stream().toList();

		int j = 0;
		for (int i = pageNumber; i <= pageNumber + 10; i++) {
			if (message.size() == 11) {
				break;
			}
			if(j > teams.size() - 1) break;
			Team team = teams.get(j);
			message.add(Utils.c("&6&l»&r &f" + team.getName() + "&r &7(&a● &7" + team.GetOnlinePlayers().size() + "/" + team.getPlayers().size() + "&7)"));
			j++;
		}

		for (String s : message) {
			p.sendMessage(s);
		}
		Preconditions.checkArgument(p != null, "player cannot be null");
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		return null;
	}
}
