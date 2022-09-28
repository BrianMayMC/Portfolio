package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamCloseCommand extends SubCommand {
	@Override
	public String getName() {
		return "close";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams close");
	}

	@Override
	public String GetDescription() {
		return "Closes your team.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 1){
			p.sendMessage(GetSyntax());
			return;
		}

		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if(team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You're not in a team!"));
			return;
		}

		team.setOpen(false);
		JackpotMCTeams.getInstance().getServer().broadcastMessage(Utils.c(Utils.getPREFIX() + " &a&l" + team.getName() + "&a has closed their team!"));
		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You've successfully set your team state to CLOSED!"));
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if(args.length == 1){
			return List.of("[close]");
		}
		return null;
	}
}
