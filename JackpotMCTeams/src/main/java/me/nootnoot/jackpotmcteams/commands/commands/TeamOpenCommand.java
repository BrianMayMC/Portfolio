package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamOpenCommand extends SubCommand {
	@Override
	public String getName() {
		return "open";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + " &c/teams open");
	}

	@Override
	public String GetDescription() {
		return "Opens your team for everyone to join without an invite.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 1){
			p.sendMessage(GetSyntax());
			return;
		}

		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if(team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You're not in a team!"));
			return;
		}

		if(team.getRole(p) != TeamRoles.OWNER){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You do not have permission to open your team!"));
			return;
		}

		if(team.isOpen()){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Your team is already opened to the public!"));
			return;
		}

		team.setOpen(true);
		JackpotMCTeams.getInstance().getServer().broadcastMessage(Utils.c(Utils.getPREFIX() + " &a&l" + team.getName() + "&a has opened their team!"));
		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You've successfully set your team state to OPEN!"));
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if(args.length == 1){
			return List.of("[open]");
		}
		return null;
	}
}
