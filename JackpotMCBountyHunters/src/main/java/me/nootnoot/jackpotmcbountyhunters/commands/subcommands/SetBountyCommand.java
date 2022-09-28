package me.nootnoot.jackpotmcbountyhunters.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.CurrencyUtils;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmcbountyhunters.JackpotMCBountyHunters;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class SetBountyCommand implements SubCommand {
	@Override
	public String getName() {
		return "set";
	}

	@Override
	public int getNeededArgs() {
		return 2;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&e/bounty set &6[player] [amount]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 set someones bounty.");
	}

	@Override
	public String getPermission() {
		return "";
	}

	@Override
	public void execute(Player p, String[] args) {
		Player target = Bukkit.getPlayer(args[0]);
		if(target == null){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid player."));
			return;
		}

		if(target == p){
			p.sendMessage(Utils.c("&c&l(!)&c You cannot set a bounty on yourself!"));
			return;
		}

		double amount;
		try{
			amount = Double.parseDouble(args[1]);
		}catch(NumberFormatException e){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid amount."));
			return;
		}

		if(JackpotMCBountyHunters.getInstance().getEcon().getBalance(p) < amount){
			p.sendMessage(Utils.c("&c&l(!)&c You do not have enough money for this!"));
			return;
		}

		JackpotMCBountyHunters.getInstance().getEcon().withdrawPlayer(p, amount);

		double current = target.getPersistentDataContainer().getOrDefault(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"), PersistentDataType.DOUBLE, 0d);
		target.getPersistentDataContainer().set(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"), PersistentDataType.DOUBLE, current + amount);

		final FileConfiguration config = JackpotMCBountyHunters.getInstance().getConfig();
		Bukkit.broadcastMessage(Utils.c(config.getString("broadcast-set")
				.replace("%player%", target.getName())
				.replace("%amount%", CurrencyUtils.prettyMoney(current + amount, true, false))
				.replace("%prefix%", config.getString("prefix"))));

		target.playSound(target.getLocation(), Sound.valueOf(config.getString("bounty-sound")), 1, 1);

	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {
		p.sendMessage(Utils.c("&c&l(!)&c You cannot set a bounty as console!"));
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 1){
			List<String> players = new ArrayList<>();
			Bukkit.getOnlinePlayers().forEach(player -> players.add(player.getName()));
			return players;
		}else if (args.length == 2) return List.of("[amount]");
		return null;
	}
}
