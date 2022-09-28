package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamInviteCommand extends SubCommand {

	@Override
	public String getName() {
		return "invite";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams invite [player]") ;
	}

	@Override
	public String GetDescription() {
		return "Invites a player to your team.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 2){
			p.sendMessage(GetSyntax());
			return;
		}

		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if(team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You're not in a team!"));
			return;
		}


		if(team.getRole(p) == TeamRoles.MEMBER){
			p.sendMessage(Utils.c(Utils.getPREFIX() + " &cYou do not have permission to invite people to your team."));
			return;
		}

		Player target = Bukkit.getPlayer(args[1]);
		if(target == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Player does not exist."));
			return;
		}

		if(target == p){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You cannot invite yourself!"));
			return;
		}

		if(team.getPlayers().size() >= Utils.getTeamSize()){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You already have the max amount of team members!"));
			return;
		}

		for(TeamPlayer player : team.getPlayers()){
			if(player.getPlayerUUID().equals(target.getUniqueId())){
				p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You cannot invite someone that is already on your team!"));
				return;
			}
		}
		if(team.getPendingInvites().contains(target.getUniqueId())){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Player is already invited to your team!"));
			return;
		}

		team.getPendingInvites().add(target.getUniqueId());

		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You have successfully invited &l&n" + target.getName() + "&a to your team!"));
		target.sendMessage(Utils.c(Utils.getPREFIX() + "&a &l&n" + p.getName() + "&a has invited you to &l&n" + team.getName()));

	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if(args.length == 2){
			ArrayList<String> names = new ArrayList<>();
			for(Player player : Bukkit.getOnlinePlayers()){
				names.add(player.getName());
			}
			return names;
		}
		return null;
	}
}
