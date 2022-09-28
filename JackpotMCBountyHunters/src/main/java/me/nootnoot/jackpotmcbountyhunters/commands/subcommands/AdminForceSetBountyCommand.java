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

public class AdminForceSetBountyCommand implements SubCommand {
	@Override
	public String getName() {
		return "forceset";
	}

	@Override
	public int getNeededArgs() {
		return 2;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&e/bounty forceset &6[player] [amount]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 forcefully set someone's bounty (override current)");
	}

	@Override
	public String getPermission() {
		return "jackpotmc.bountyhunters.forceset";
	}

	@Override
	public void execute(Player p, String[] args) {
		Player target = Bukkit.getPlayer(args[0]);
		if(target == null){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid player."));
			return;
		}

		double amount;
		try{
			amount = Double.parseDouble(args[1]);
		}catch(NumberFormatException e){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid amount."));
			return;
		}

		target.getPersistentDataContainer().set(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"), PersistentDataType.DOUBLE, amount);

		final FileConfiguration config = JackpotMCBountyHunters.getInstance().getConfig();
		p.sendMessage(Utils.c(config.getString("bounty-force-set").replace("%player%", target.getName()).replace("%prefix%", config.getString("prefix"))
				.replace("%amount%", CurrencyUtils.prettyMoney(amount, true, false))));
		target.playSound(target.getLocation(), Sound.valueOf(config.getString("bounty-sound")), 1, 1);
		target.sendMessage(Utils.c(config.getString("bounty-set").replace("%prefix%", config.getString("prefix")).replace("%amount%", CurrencyUtils.prettyMoney(amount, true, false))));

	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {
		Player target = Bukkit.getPlayer(args[0]);
		if(target == null){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid player."));
			return;
		}

		double amount;
		try{
			amount = Double.parseDouble(args[1]);
		}catch(NumberFormatException e){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid amount."));
			return;
		}

		target.getPersistentDataContainer().set(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"), PersistentDataType.DOUBLE, amount);

		final FileConfiguration config = JackpotMCBountyHunters.getInstance().getConfig();

		p.sendMessage(Utils.c(config.getString("bounty-force-set").replace("%player%", target.getName()).replace("%prefix%", config.getString("prefix"))
				.replace("%amount%", CurrencyUtils.prettyMoney(amount, true, false))));

		target.playSound(target.getLocation(), Sound.valueOf(config.getString("bounty-sound")), 1, 1);
		target.sendMessage(Utils.c(config.getString("bounty-set").replace("%prefix%", config.getString("prefix")).replace("%amount%", CurrencyUtils.prettyMoney(amount, true, false))));
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
