package me.nootnoot.luckyblockfactions.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionDepositCommand implements SubCommand {
	@Override
	public String getName() {
		return "deposit";
	}

	@Override
	public int getNeededArgs() {
		return 1;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction deposit &4[amount]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 deposit money to your factions balance.");
	}

	@Override
	public String getPermission() {
		return "";
	}

	@Override
	public void execute(Player p, String[] args) {
		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(p);
		if(f == null){
			p.sendMessage(Utils.c("&C&l(!)&C You are not in a faction!"));
			return;
		}

		double amount = 0;
		try{
			amount = Double.parseDouble(args[0]);
		}catch(NumberFormatException e){
			p.sendMessage(Utils.c("&C&l(!)&c Invalid amount."));
			return;
		}

		if(LuckyblockFactions.getInstance().getEcon().getBalance(p) < amount){
			p.sendMessage(Utils.c("&C&l(!)&c You do not have enough money for this."));
			return;
		}

		LuckyblockFactions.getInstance().getEcon().withdrawPlayer(p, amount);
		f.setBalance(f.getBalance() + amount);
		for(FactionPlayer fp : f.getPlayers()){
			Player target = Bukkit.getPlayer(fp.getUuid());
			if(target != null){
				target.sendMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.deposit").replace("%player%", p.getName())
						.replace("%amount%", Utils.formatCurrency(amount))));
			}
		}
	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {

	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		return null;
	}
}
