package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class TeamMakeLeaderCommand extends SubCommand {


	@Override
	public String getName() {
		return "makeleader";
	}

	@Override
	public String GetSyntax() {
		return ChatColor.translateAlternateColorCodes('&', Utils.getPREFIX() + "&c /teams makeleader [player]");
	}

	@Override
	public String GetDescription() {
		return "Transfers leadership of your team to a different player.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 2){
			p.sendMessage(GetSyntax());
			return;
		}
		Player target = Bukkit.getPlayer(args[1]);
		if(target == null){
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.getPREFIX() + "&c Player does not exist."));
			return;
		}

		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if(team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You must have a team to transfer leadership!"));
			return;
		}

		if(!team.getOwnerUUID().equals(p.getUniqueId())){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You have to be the owner of the team to transfer leadership!"));
			return;
		}

		TeamPlayer teamPlayer = team.getPlayer(target);
		if(teamPlayer == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c The player must be on your team!"));
			return;
		}

		team.makeLeader(teamPlayer, p);
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if(args.length == 2){
			ArrayList<String> names = new ArrayList<>();
			Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
			if(team != null){
				for(TeamPlayer player : team.getPlayers()){
					names.add(player.getOfflinePlayer().getName());
				}
			}else{
				return List.of("[You're not in a team]");
			}
		}
		return null;
	}
}
