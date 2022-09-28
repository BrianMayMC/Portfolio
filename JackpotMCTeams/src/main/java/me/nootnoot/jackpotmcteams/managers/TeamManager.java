package me.nootnoot.jackpotmcteams.managers;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.storage.TeamStorage;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.*;

public class TeamManager {

	private final TeamStorage db;

	@Getter@Setter
	private ArrayList<Player> playersInTeamChat;

	public TeamManager(){
		playersInTeamChat = new ArrayList<>();
		db = JackpotMCTeams.getInstance().getTeamStorage();
	}

	public Team getTeam(String name){
		for(Team team : db.getTeams().values()){
			if(team.getName().equalsIgnoreCase(name)){
				return team;
			}
		}
		return null;
	}

	public Team hasTeam(Player p){
		for(Team team : db.getTeams().values()){
			for(TeamPlayer teamPlayer : team.getPlayers()){
				if(teamPlayer.getPlayerUUID().equals(p.getUniqueId())){
					return team;
				}
			}
		}
		return null;
	}

	public Team hasTeamOffline(String name){
		for(Team team : db.getTeams().values()){
			for(TeamPlayer teamPlayer : team.getPlayers()){
				if(teamPlayer.getPlayerName().equalsIgnoreCase(name)){
					return team;
				}
			}
		}
		return null;
	}

	public int GetNextRank(){
		Collection<Team> teams = db.getOrderedTeams().values();
		if(teams.isEmpty()){
			return 1;
		}else{
			return teams.size() + 1;
		}
	}

	public void createTeam(String name, Player owner){
		JackpotMCTeams.getInstance().getTeamStorage().createTeam(name, owner, GetNextRank());
	}

	public void disbandTeam(Team team){
		for(TeamPlayer teamPlayer : team.getPlayers()){
			if(teamPlayer.getPlayer() != null){
				JackpotMCTeams.getInstance().getTeamManager().getPlayersInTeamChat().remove(teamPlayer.getPlayer());
				teamPlayer.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + "&c Your team has been disbanded."));
			}
		}
		JackpotMCTeams.getInstance().getTeamStorage().RemoveTeam(team);
		JackpotMCTeams.getInstance().getTeamStorage().deleteTeam(team);
	}

	public String getInvitedTeams(Player p){
		StringBuilder string = new StringBuilder();
		for(Team team : db.getTeams().values()){
			if(team.getPendingInvites().contains(p.getUniqueId())){
				string.append(team.getName());
				string.append(", ");
			}
		}
		return string.toString();
	}

	public void clear(){
		db.getTeams().values().clear();
	}

	public ArrayList<String> GetTop10Message(){
		ArrayList<String> message = new ArrayList<>();
		message.add("        &6⛨ Team Kills Leaderboard &7(&c❤s stolen&7)");
		for(Team team : db.getOrderedTeams().values()){
			if(message.size() == 11){
				break;
			}
			message.add("&6#" + team.getRank() + "   &7⛨" + team.getName() + "   &c❤ " +  team.getKills());
		}
		return message;
	}
}
