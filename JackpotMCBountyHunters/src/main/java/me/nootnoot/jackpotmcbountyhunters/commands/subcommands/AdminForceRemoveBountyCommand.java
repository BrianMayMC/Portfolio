package me.nootnoot.jackpotmcbountyhunters.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
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

public class AdminForceRemoveBountyCommand implements SubCommand {
	@Override
	public String getName() {
		return "forceremove";
	}

	@Override
	public int getNeededArgs() {
		return 1;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&e/bounty forceremove &6[player]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 Forcefully remove someone's bounty.");
	}

	@Override
	public String getPermission() {
		return "jackpotmc.bountyhunters.forceremove";
	}

	@Override
	public void execute(Player p, String[] args) {
		Player target = Bukkit.getPlayer(args[0]);
		if(target == null){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid player."));
			return;
		}
		final FileConfiguration config = JackpotMCBountyHunters.getInstance().getConfig();


		if(!(target.getPersistentDataContainer().has(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"), PersistentDataType.DOUBLE))){
			p.sendMessage(Utils.c(config.getString("no-bounty-view").replace("%player%", target.getName()).replace("%prefix%", config.getString("prefix"))));
			return;
		}
		target.getPersistentDataContainer().remove(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"));


		target.sendMessage(Utils.c(config.getString("bounty-remove").replace("%prefix%", config.getString("prefix"))));
		p.sendMessage(Utils.c(config.getString("bounty-force-remove").replace("%player%", target.getName()).replace("%prefix%", config.getString("prefix"))));
		target.playSound(target.getLocation(), Sound.valueOf(config.getString("bounty-sound")), 1, 1);
	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {
		Player target = Bukkit.getPlayer(args[0]);
		if(target == null){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid player."));
			return;
		}
		final FileConfiguration config = JackpotMCBountyHunters.getInstance().getConfig();

		if(!(target.getPersistentDataContainer().has(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"), PersistentDataType.DOUBLE))){
			p.sendMessage(Utils.c(config.getString("no-bounty-view").replace("%player%", target.getName()).replace("%prefix%", config.getString("prefix"))));
			return;
		}

		target.getPersistentDataContainer().remove(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"));

		target.sendMessage(Utils.c(config.getString("bounty-remove").replace("%prefix%", config.getString("prefix"))));
		p.sendMessage(Utils.c(config.getString("%player%", target.getName()).replace("%prefix%", config.getString("prefix"))));
		target.playSound(target.getLocation(), Sound.valueOf(config.getString("bounty-sound")), 1, 1);
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 1) {
			List<String> players = new ArrayList<>();
			Bukkit.getOnlinePlayers().forEach(player -> players.add(player.getName()));
			return players;
		}
		return null;
	}
}
