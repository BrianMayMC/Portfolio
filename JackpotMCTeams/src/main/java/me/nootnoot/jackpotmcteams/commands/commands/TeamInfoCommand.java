package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.tasks.UpdateRankTask;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamInfoCommand extends SubCommand {
	@Override
	public String getName() {
		return "info";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams info [team name] &nOR&c /teams info &nOR&c /teams info [player]");
	}

	@Override
	public String GetDescription() {
		return "Views the description of your team or someone else's team.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length < 1){
			p.sendMessage(GetSyntax());
			return;
		}

		new UpdateRankTask().run();
		//checking own team
		if(args.length == 1){
			Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
			if(team == null){
				p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You are not in a team."));
				return;
			}
			System.out.println(team.getPlayers().size());
			for(String s : Utils.cL(team.GetWho())){
				p.sendMessage(s);
			}
		}
		//checking args team
		else if(args.length == 2){
			Team team = JackpotMCTeams.getInstance().getTeamManager().getTeam(args[1]);
			if(team != null){
				for(String s : Utils.cL(team.GetWho())){
					p.sendMessage(s);
				}
			}else{
				String playerName = args[1];
				boolean contains = false;
				for(Team team2 : JackpotMCTeams.getInstance().getTeamStorage().getTeams().values()){
					for(TeamPlayer player : team2.getPlayers()){
						if(player.getOfflinePlayer().getName().equalsIgnoreCase(playerName)){
							contains = true;
							for(String s : Utils.cL(team2.GetWho())){
								p.sendMessage(s);
							}
						}
					}
				}
				if(!contains){
					p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Team/Player does not exist."));
				}
			}
		}
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if(args.length == 2){
			ArrayList<String> names = new ArrayList<>();
			for(Team team : JackpotMCTeams.getInstance().getTeamStorage().getTeams().values()){
				names.add(team.getName());
			}
			for(Player player : Bukkit.getOnlinePlayers()){
				names.add(player.getName());
			}
			return names;
		}
		return null;
	}
}
