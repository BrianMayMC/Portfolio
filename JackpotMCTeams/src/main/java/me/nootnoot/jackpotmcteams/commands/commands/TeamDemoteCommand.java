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

public class TeamDemoteCommand extends SubCommand {
	@Override
	public String getName() {
		return "demote";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams demote [player]");
	}

	@Override
	public String GetDescription() {
		return "Demotes a member of your team back to member.";
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

		Player target = Bukkit.getPlayer(args[1]);
		if(target == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Player does not exist."));
			return;
		}


		TeamPlayer player = team.getPlayer(p);
		if(player.getRole() != TeamRoles.OWNER){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Invalid permission!"));
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You have to be owner of this team to demote people!"));
			return;
		}

		TeamPlayer targetPlayer = team.getPlayer(target);
		if(targetPlayer == null){
			p.sendMessage(Utils.c( Utils.getPREFIX() + "&c Player is not in your team."));
			return;
		}

		if(targetPlayer.getRole() == TeamRoles.MEMBER){
			p.sendMessage(Utils.c( Utils.getPREFIX() + " &c Player is already Member!"));
			return;
		}
		else if(targetPlayer.getRole() == TeamRoles.OWNER){
			p.sendMessage(Utils.c( Utils.getPREFIX() + " &c You cannot demote yourself!"));
			p.sendMessage(Utils.c( Utils.getPREFIX() + " &c If you wish to transfer your team,"));
			p.sendMessage(Utils.c( Utils.getPREFIX() + " &c do /team makeleader [player]"));
			return;
		}

		team.demote(targetPlayer);
		p.sendMessage(Utils.c(Utils.getPREFIX() + " &a You have successfully Demoted &l&n" + target.getName() + "&a to Member!"));
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		return null;
	}
}
