package me.nootnoot.jackpotmcteams.commands.admincommands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class AdminReloadCommand extends SubCommand {
	@Override
	public String getName() {
		return "reload";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teamsadmin reload");
	}

	@Override
	public String GetDescription() {
		return "Reloads the config file for the plugin.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 1){
			p.sendMessage(GetSyntax());
			return;
		}

		JackpotMCTeams.getInstance().reload();
		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You have successfully reloaded the config.yml file."));
	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		return null;
	}
}
