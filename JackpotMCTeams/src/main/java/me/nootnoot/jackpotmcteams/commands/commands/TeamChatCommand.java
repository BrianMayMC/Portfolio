package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.managers.TeamManager;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamChatCommand extends SubCommand {

	@Override
	public String getName() {
		return "chat";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /team chat");
	}

	@Override
	public String GetDescription() {
		return "Turn the team chat on/off";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 1){
			p.sendMessage(GetSyntax());
			return;
		}

		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if(team == null){
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.getPREFIX() + "&c You're not in a team!"));
			return;
		}

		if(JackpotMCTeams.getInstance().getTeamManager().getPlayersInTeamChat().contains(p)){
			JackpotMCTeams.getInstance().getTeamManager().getPlayersInTeamChat().remove(p);
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You've successfully turned off team chat!"));
		}else{
			JackpotMCTeams.getInstance().getTeamManager().getPlayersInTeamChat().add(p);
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You've successfully turned on team chat!"));
		}
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		return null;
	}
}
