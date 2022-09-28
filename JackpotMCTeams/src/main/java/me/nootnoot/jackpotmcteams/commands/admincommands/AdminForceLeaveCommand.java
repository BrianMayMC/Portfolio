package me.nootnoot.jackpotmcteams.commands.admincommands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminForceLeaveCommand extends SubCommand {
	@Override
	public String getName() {
		return "forceleave";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teamsadmin forceleave [player]");
	}

	@Override
	public String GetDescription() {
		return "Forces someone to leave a team.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 2){
			p.sendMessage(GetSyntax());
			return;
		}


		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeamOffline(args[1]);
		if(team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Player is not in a team."));
			return;
		}

		if(team.KickPlayer(args[1])){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You have successfully kicked " + args[1] + " from &l" + team.getName() + "&a!"));
		}
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
