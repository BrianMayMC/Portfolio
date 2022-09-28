package me.nootnoot.luckyblockfactions.commands.subcommands;

import lombok.extern.jackson.Jacksonized;
import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionPlayer;
import me.nootnoot.luckyblockfactions.entities.FactionRole;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionCreateCommand implements SubCommand {
	@Override
	public String getName() {
		return "create";
	}

	@Override
	public int getNeededArgs() {
		return 1;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction create &4[name]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 creates a faction");
	}

	@Override
	public String getPermission() {
		return "";
	}

	@Override
	public void execute(Player p, String[] args) {

		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(p);
		if(f != null){
			p.sendMessage(Utils.c("&C&l(!)&C You are already in a faction!"));
			return;
		}

		String name = args[0];
		if(name.length() > 16){
			p.sendMessage(Utils.c("&C&l(!)&c The length of your faction name cannot be longer than 16 characters."));
			return;
		}
		if(name.length() < 3) {
			p.sendMessage(Utils.c("&C&l(!)&c The length of your faction name cannot be shorter than 3 characters."));
			return;
		}

		Faction nameCheck = LuckyblockFactions.getInstance().getFactionManager().getFactionByName(name);
		if(nameCheck != null){
			p.sendMessage(Utils.c("&C&l(!)&c A faction with this name already exists."));
			return;
		}

		LuckyblockFactions.getInstance().getFactionManager().createFaction(new Faction(new FactionPlayer(p.getUniqueId(), FactionRole.LEADER, p.getName()), name));

		final FileConfiguration config = LuckyblockFactions.getInstance().getConfig();
		LuckyblockFactions.getInstance().getServer().broadcastMessage(Utils.c(config.getString("messages.create-broadcast").replace("%faction%", name)));
		p.sendMessage(Utils.c(config.getString("messages.create-message")));

	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {

	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 1){
			return List.of("[name]");
		}
		return null;
	}
}
