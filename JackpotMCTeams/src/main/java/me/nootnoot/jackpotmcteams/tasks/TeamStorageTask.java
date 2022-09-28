package me.nootnoot.jackpotmcteams.tasks;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import org.bukkit.scheduler.BukkitRunnable;

public class TeamStorageTask extends BukkitRunnable {

	@Override
	public void run() {
		JackpotMCTeams.getInstance().getTeamStorage().save();
	}
}
