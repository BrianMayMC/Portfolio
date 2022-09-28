package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamDescriptionCommand extends SubCommand {
	@Override
	public String getName() {
		return "description";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams description [new description]");
	}

	@Override
	public String GetDescription() {
		return "Sets a description for your team!";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length < 1){
			p.sendMessage(GetSyntax());
			return;
		}

		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if(team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You're not in a team!"));
			return;
		}

		if(!p.getUniqueId().equals(team.getOwnerUUID())){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You have to be owner of the team to update the description!"));
		}

		StringBuilder builder = new StringBuilder();
		for(int i = 1; i < args.length; i++){
			builder.append(args[i]);
			builder.append(" ");
		}

		if(builder.toString().length() > 128){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Description cannot be longer than 128 characters!"));
			return;
		}
		team.UpdateDescription(builder.toString(), p);
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if (args.length > 1){
			return List.of("[your description]");
		}
		return null;
	}
}
