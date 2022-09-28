package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamDepositCommand extends SubCommand {
	@Override
	public String getName() {
		return "deposit";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams deposit [amount]");
	}

	@Override
	public String GetDescription() {
		return "Deposits an amount from your balance into your teams balance.";
	}

	@Override
	public void execute(Player p, String[] args) {
		if(args.length != 2){
			p.sendMessage(GetSyntax());
			return;
		}
		Team team = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
		if (team == null){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You are not in a team."));
			return;
		}

		int amount;
		try{
			amount = Integer.parseInt(args[1]);
		}catch(NumberFormatException e){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Invalid number."));
			return;
		}

		if(!(JackpotMCTeams.getInstance().getEco().getBalance(p) > amount)) {
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You do not have enough money for that."));
			return;
		}


		team.setBalance(team.getBalance() + amount);
		JackpotMCTeams.getInstance().getEco().withdrawPlayer(p, amount);
		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You have successfully deposited $" + amount + " to your teams bank!"));
		for(TeamPlayer player : team.getPlayers()){
			if(player.getPlayer() != null)
				player.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + "&a " + p.getName() + " has deposited $" + amount + " to your teams bank!"));
		}

	}

	@Override
	public List<String> GetTabCompletion(Player p, String[] args) {
		if(args.length == 2){
			return List.of("[amount]");
		}
		return null;
	}
}
