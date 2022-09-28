package me.nootnoot.jackpotmcteams.commands.admincommands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminRemoveKillsCommand extends SubCommand {
	@Override
	public String getName() {
		return "removekills";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teamsadmin removekills [team] [amount]");
	}

	@Override
	public String GetDescription() {
		return "Forcefully removes kills from a team.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 3){
			p.sendMessage(GetSyntax());
			return;
		}

		Team team = JackpotMCTeams.getInstance().getTeamManager().getTeam(args[1]);
		if(team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Team does not exist."));
			return;
		}

		int amount;
		try{
			amount = Integer.parseInt(args[2]);
		}catch(NumberFormatException e){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Invalid amount."));
			return;
		}

		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You've successfully removed ❖" + amount + " from the team &n" + team.getName()));

		team.RemoveKills(amount);

	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if(args.length == 2){
			ArrayList<String> names = new ArrayList<>();
			for(Team team : JackpotMCTeams.getInstance().getTeamStorage().getTeams().values()){
				names.add(team.getName());
			}
			return names;
		}else if(args.length == 3){
			return List.of("[amount]");
		}
		return null;
	}
}
