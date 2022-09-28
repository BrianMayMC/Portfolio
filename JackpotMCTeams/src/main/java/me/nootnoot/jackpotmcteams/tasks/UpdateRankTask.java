package me.nootnoot.jackpotmcteams.tasks;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.entities.Team;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicInteger;

public class UpdateRankTask extends BukkitRunnable {

	private final AtomicInteger counter = new AtomicInteger();

	@Override
	public synchronized void run() {
		JackpotMCTeams.getInstance().getTeamStorage().updateOrderedTeams();
		JackpotMCTeams.getInstance().getTeamStorage().updateOrderedOnlineTeams();
		counter.setRelease(1);

		for(Team team : JackpotMCTeams.getInstance().getTeamStorage().getOrderedTeams().values()){
			team.setRank(counter.getAndIncrement());
		}
		counter.setRelease(1);
	}
}
