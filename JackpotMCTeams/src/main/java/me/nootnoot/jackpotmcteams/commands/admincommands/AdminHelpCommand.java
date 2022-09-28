package me.nootnoot.jackpotmcteams.commands.admincommands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class AdminHelpCommand extends SubCommand {
	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + " &c/teamsadmin help [page]");
	}

	@Override
	public String GetDescription() {
		return "Displays the help menu for admin commands.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 2){
			for(String s : JackpotMCTeams.getInstance().getAdminCommandManager().GetHelpByPage(1)){
				p.sendMessage(s);
			}
			return;
		}
		int page;
		try{
			page = Integer.parseInt(args[1]);
		}catch(NumberFormatException e){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Invalid page number"));
			return;
		}
		if(page < 4){
			for(String s : JackpotMCTeams.getInstance().getAdminCommandManager().GetHelpByPage(page)){
				p.sendMessage(s);
			}
		}else{
			for(String s : JackpotMCTeams.getInstance().getAdminCommandManager().GetHelpByPage(3)){
				p.sendMessage(s);
			}
		}

	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if(args.length == 2){
			return List.of("[page]");
		}
		return null;
	}
}
