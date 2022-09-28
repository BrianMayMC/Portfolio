package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamPromoteCommand extends SubCommand {

	@Override
	public String getName() {
		return "promote";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams promote [player]");
	}

	@Override
	public String GetDescription() {
		return "Promotes a player in your team.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 2){
			p.sendMessage(GetSyntax());
			return;
		}
		if(!args[0].equalsIgnoreCase("promote")) return;


		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if(team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You're not in a team!"));
			return;
		}

		Player target = Bukkit.getPlayer(args[1]);
		if(target == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Player does not exist."));
			return;
		}


		TeamPlayer player = team.getPlayer(p);
		if(player == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Player is not in your team!"));
			return;
		}

		if(player.getRole() != TeamRoles.OWNER){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Invalid permission!"));
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You have to be owner of this team to promote people!"));
			return;
		}

		TeamPlayer targetPlayer = team.getPlayer(target);
		if(targetPlayer == null){
			p.sendMessage(Utils.c( Utils.getPREFIX() + "&c Player is not in your team."));
			return;
		}

		if(targetPlayer.getRole() == TeamRoles.OFFICER){
			p.sendMessage(Utils.c( Utils.getPREFIX() + " &c Player is already Officer!"));
			p.sendMessage(Utils.c( "&cif you wish to transfer leadership,"));
			p.sendMessage(Utils.c( "&cdo /teams makeleader [player]"));
			return;
		}
		else if(targetPlayer.getRole() == TeamRoles.OWNER){
			p.sendMessage(Utils.c( Utils.getPREFIX() + " &c Player is already Owner!"));
			return;
		}

		team.promote(targetPlayer);
		p.sendMessage(Utils.c(Utils.getPREFIX() + " &a You have successfully promoted &l&n" + target.getName() + "&a to Officer!"));
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if(args.length == 2){
			List<String> names = new ArrayList<>();
			Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
			if(team != null){
				for(TeamPlayer player : team.getPlayers()){
					names.add(player.getOfflinePlayer().getName());
				}
				return names;
			}else{
				return List.of("[You're not in a team]");
			}
		}
		return null;
	}
}
