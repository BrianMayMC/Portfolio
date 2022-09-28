package me.nootnoot.jackpotmccoinflip.commands;

import me.nootnoot.framework.commandsystem.CommandManager;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmccoinflip.JackpotMCCoinflip;
import me.nootnoot.jackpotmccoinflip.entities.Coinflip;
import me.nootnoot.jackpotmccoinflip.menus.MainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class CoinflipCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player player)) return true;

		if(args.length == 2 && args[0].equalsIgnoreCase("create")){
			if(JackpotMCCoinflip.getInstance().getCoinflipManager().hasCoinflip(player.getUniqueId())){
				player.sendMessage(Utils.c("&c&l(!)&c You already have an active coinflip!"));
				return true;
			}
			double bet;
			try{
				bet = Double.parseDouble(args[1]);
			}catch(NumberFormatException e){
				player.sendMessage(Utils.c("&c&l(!)&c Invalid amount."));
				return true;
			}

			if(bet > JackpotMCCoinflip.getInstance().getEcon().getBalance(player)){
				player.sendMessage(Utils.c("&c&l(!)&c You do not have enough money for this."));
				return true;
			}

			if(bet < JackpotMCCoinflip.getInstance().getConfig().getDouble("min-amount")){
				player.sendMessage(Utils.c("&c&l(!)&c You need at least &l&n$" + Utils.prettyMoney(JackpotMCCoinflip.getInstance().getConfig().getDouble("min-amount"), true, false) + "&c to make a coinflip!"));
				return true;
			}

			if(bet > JackpotMCCoinflip.getInstance().getConfig().getDouble("max-amount")){
				player.sendMessage(Utils.c("&c&l(!)&c The maximum amount for a coinflip is &l&n$" + Utils.prettyMoney(JackpotMCCoinflip.getInstance().getConfig().getDouble("max-amount"), true, false) + "&c!"));
				return true;
			}

			player.sendMessage(Utils.c("&a&l(!)&a Successfully created coinflip."));
			JackpotMCCoinflip.getInstance().getEcon().withdrawPlayer(player, bet);
			JackpotMCCoinflip.getInstance().getCoinflipManager().createCoinflip(new Coinflip(player.getUniqueId(), bet));
			return true;
		}

		JackpotMCCoinflip.getInstance().getMenuManager().openInterface(player, new MainMenu());

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1) return List.of("create");
		else if(args.length == 2) return List.of("[amount]");
		return null;
	}
}
