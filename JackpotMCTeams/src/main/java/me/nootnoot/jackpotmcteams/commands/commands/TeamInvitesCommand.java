package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamInvitesCommand extends SubCommand {
	@Override
	public String getName() {
		return "invites";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams invites");
	}

	@Override
	public String GetDescription() {
		return "Shows all teams you're currently invited to.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 1){
			p.sendMessage(GetSyntax());
			return;
		}

		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You are currently invited to: " + JackpotMCTeams.getInstance().getTeamManager().getInvitedTeams(p)));


	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		return null;
	}
}
