package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamCreateCommand extends SubCommand {

	@Override
	public void execute(Player p, String[] args){
		if(args.length != 2){
			p.sendMessage(GetSyntax());
			return;
		}

		if(args[1].length() > 16) {
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Team name can not be longer than 16 characters."));
			return;
		}
		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if(team != null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You already have a team!"));
			return;
		}

		for(Team existingTeam : JackpotMCTeams.getInstance().getTeamStorage().getTeams().values()){
			if(existingTeam.getName().equalsIgnoreCase(args[1])){
				p.sendMessage(Utils.c(Utils.getPREFIX() + "&c A team with that name already exists!"));
				return;
			}
		}
		JackpotMCTeams.getInstance().getTeamManager().createTeam(args[1], p);
		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You have successfully created your team!"));
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if(args.length == 2){
			return List.of("[name]");
		}
		return null;
	}


	@Override
	public String getName() {
		return "create";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams create [name]");
	}

	@Override
	public String GetDescription() {
		return "Creates a team.";
	}
}
