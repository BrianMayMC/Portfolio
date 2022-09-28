package me.nootnoot.jackpotmcteams.commands.commands;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.commands.SubCommand;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.entities.TeamPlayer;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamWithdrawCommand extends SubCommand {


	@Override
	public String getName() {
		return "withdraw";
	}

	@Override
	public String GetSyntax() {
		return Utils.c(Utils.getPREFIX() + "&c /teams withdraw [amount]");
	}

	@Override
	public String GetDescription() {
		return "Withdraws money from your teams balance.";
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

		if(team.getBalance() < amount) {
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c Your team does not have enough money for that."));
			return;
		}

		if(team.getRole(p) != TeamRoles.OFFICER && team.getRole(p) != TeamRoles.OWNER){
			p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You do not have permission to withdraw money from your teams balance."));
			return;
		}


		team.setBalance(team.getBalance() - amount);
		JackpotMCTeams.getInstance().getEco().depositPlayer(p, amount);
		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You have successfully withdrawn $" + amount + " from your teams bank!"));
		for(TeamPlayer player : team.getPlayers()){
			if(player.getPlayer() != null) {
				player.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + "&a " + p.getName() + " has withdrawn $" + amount + " from your teams bank!"));
			}
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
