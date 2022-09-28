package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.tasks.UpdateRankTask;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamTopCommand extends SubCommand {


	@Override
	public String getName() {
		return "top";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams top");
	}

	@Override
	public String GetDescription() {
		return "Displays the top 10 teams!";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 1){
			p.sendMessage(GetSyntax());
			return;
		}
		new UpdateRankTask().run();
		for(String s : Utils.cL(JackpotMCTeams.getInstance().getTeamManager().GetTop10Message())){
			p.sendMessage(s);
		}

	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		return null;
	}
}
