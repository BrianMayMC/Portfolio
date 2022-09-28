package me.nootnoot.luckyblockfactions.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FactionTopCommand implements SubCommand {
	@Override
	public String getName() {
		return "top";
	}

	@Override
	public int getNeededArgs() {
		return 0;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction top");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 view the top 10 factions.");
	}

	@Override
	public String getPermission() {
		return "";
	}

	@Override
	public void execute(Player p, String[] args) {
		final FileConfiguration config = LuckyblockFactions.getInstance().getConfig();
		List<String> message = new ArrayList<>();
		message.add(config.getString("messages.top-header"));
		LuckyblockFactions.getInstance().getFactionManager().updateRanksByKills();
		for(Faction f : LuckyblockFactions.getInstance().getFactionManager().getFactionsOrderedKills()){
			if(message.size() == 11){
				break;
			}
			message.add(config.getString("messages.top-template").replace("%rank%", String.valueOf(f.getRank()))
					.replace("%name%", f.getName()).replace("%kills%", String.valueOf(f.getKills())));
		}

		for(String s : message){
			p.sendMessage(Utils.c(s));
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
