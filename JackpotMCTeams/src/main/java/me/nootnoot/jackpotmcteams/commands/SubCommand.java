package me.nootnoot.jackpotmcteams.commands;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

	public abstract String getName();

	public abstract String GetSyntax();

	public abstract String GetDescription();

	public abstract void execute(Player p, String[] args);

	public abstract List<String> GetTabCompletion(Player p, String args[]);
}
