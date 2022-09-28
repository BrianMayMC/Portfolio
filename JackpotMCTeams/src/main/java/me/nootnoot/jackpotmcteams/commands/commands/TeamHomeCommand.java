package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TeamHomeCommand extends SubCommand {
	@Override
	public String getName() {
		return "home";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams home");
	}

	@Override
	public String GetDescription() {
		return "Teleports you to your teams home.";
	}

	@Override
	public void execute(Player p, String[] args) {
		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if(team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You are not in a team."));
			return;
		}

		if(team.getTeamHome() == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Your team does not have a home set."));
			return;
		}
		if(JackpotMCTeams.getInstance().getPlayerTeleportQueue().contains(p)){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You already have a queued teleportation active!"));
			return;
		}
		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a Teleporting in 10 seconds..."));
		JackpotMCTeams.getInstance().getPlayerTeleportQueue().add(p);
		new BukkitRunnable(){
			@Override
			public void run(){
				if(JackpotMCTeams.getInstance().getPlayerTeleportQueue().contains(p)){
					p.sendMessage(Utils.c(Utils.getPREFIX() + "&a Teleporting you now..."));
					JackpotMCTeams.getInstance().getPlayerTeleportQueue().remove(p);
					p.teleport(team.getTeamHome());
				}
			}
		}.runTaskLater(JackpotMCTeams.getInstance(), 10L * 20L);

	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		return null;
	}
}
