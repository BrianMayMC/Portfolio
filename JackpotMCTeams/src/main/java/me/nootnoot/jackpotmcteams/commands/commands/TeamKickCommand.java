package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamKickCommand extends SubCommand {
	@Override
	public String getName() {
		return "kick";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams kick [name]");
	}

	@Override
	public String GetDescription() {
		return "Kicks a player from your team.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 2){
			p.sendMessage(GetSyntax());
			return;
		}
		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if (team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You are not in a team."));
			return;
		}

		String name = args[1];

		if(team.getRole(p) == TeamRoles.MEMBER){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You do not have permission to kick people from your team!"));
			return;
		}


		if(team.getRole(name) == TeamRoles.OWNER){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You cannot kick the owner of your team!"));
			return;
		}

		if(team.KickPlayer(name)){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You have successfully kicked " + args[1] + " from your team!"));
			JackpotMCTeams.getInstance().getTeamManager().getPlayersInTeamChat().remove(Bukkit.getPlayer(name));
		}else{
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Player does not exist or is not in your team."));
		}
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		return null;
	}
}
