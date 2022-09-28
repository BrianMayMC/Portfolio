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

import javax.crypto.spec.PBEParameterSpec;
import java.util.List;

public class ViewBountyCommand implements SubCommand {
	@Override
	public String getName() {
		return "view";
	}

	@Override
	public int getNeededArgs() {
		return 1;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&e/bounty view &6[player]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 view a player's bounty");
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

		final FileConfiguration config = JackpotMCBountyHunters.getInstance().getConfig();
		double amount = 0;
		if(target.getPersistentDataContainer().has(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"), PersistentDataType.DOUBLE)){
			amount = target.getPersistentDataContainer().get(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"), PersistentDataType.DOUBLE);
		}
		if(amount == 0){
			p.sendMessage(Utils.c(config.getString("no-bounty-view")
					.replace("%prefix%", config.getString("prefix"))
					.replace("%player%", target.getName())));
			return;
		}
		p.sendMessage(Utils.c(config.getString("bounty-view")
				.replace("%prefix%", config.getString("prefix"))
				.replace("%amount%", CurrencyUtils.prettyMoney(amount, true, false))
				.replace("%player%", target.getName())));

		p.playSound(target.getLocation(), Sound.valueOf(config.getString("bounty-sound")), 1, 1);
	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {
		Player target = Bukkit.getPlayer(args[0]);
		if(target == null){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid player."));
			return;
		}

		final FileConfiguration config = JackpotMCBountyHunters.getInstance().getConfig();
		double amount = 0;
		if(target.getPersistentDataContainer().has(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"), PersistentDataType.DOUBLE)){
			amount = target.getPersistentDataContainer().get(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"), PersistentDataType.DOUBLE);
		}
		if(amount == 0){
			p.sendMessage(Utils.c(config.getString("no-bounty-view")
					.replace("%prefix%", config.getString("prefix"))));
			return;
		}
		p.sendMessage(Utils.c(config.getString("bounty-view")
				.replace("%prefix%", config.getString("prefix"))
				.replace("%amount%", CurrencyUtils.prettyMoney(amount, true, false))));
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		return null;
	}
}
