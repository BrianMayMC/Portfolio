package me.nootnoot.jackpotmccrashgambling.commands;

import me.nootnoot.jackpotmccrashgambling.JackpotMCCrashGambling;
import me.nootnoot.jackpotmccrashgambling.menus.MainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class CrashCommand implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player p)) return true;

		JackpotMCCrashGambling.getInstance().getMenuManager().openInterface(p, new MainMenu());

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return null;
	}
}
