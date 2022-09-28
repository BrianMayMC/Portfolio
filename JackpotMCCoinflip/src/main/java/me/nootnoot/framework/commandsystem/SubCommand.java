package me.nootnoot.framework.commandsystem;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public interface SubCommand {

	String getName();

	int getNeededArgs();

	String getSyntax();

	String getDescription();

	String getPermission();

	void execute(Player p, String[] args);

	void executeConsole(ConsoleCommandSender p, String[] args);

	List<String> getTabCompletion(Player p, String args[]);
}
