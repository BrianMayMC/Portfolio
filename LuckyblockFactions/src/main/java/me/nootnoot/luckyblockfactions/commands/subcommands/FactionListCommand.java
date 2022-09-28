package me.nootnoot.luckyblockfactions.commands.subcommands;

import com.mysql.cj.exceptions.NumberOutOfRange;
import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class FactionListCommand implements SubCommand {
	@Override
	public String getName() {
		return "list";
	}

	@Override
	public int getNeededArgs() {
		return 0;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction list [page]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 view a sorted list of factions (ordered by online players)");
	}

	@Override
	public String getPermission() {
		return "";
	}

	@Override
	public void execute(Player p, String[] args) {
		int page;
		if(args.length == 1) {
			try {
				page = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				p.sendMessage(Utils.c("&C&l(!)&c Invalid amount."));
				return;
			}
		}else{
			page = 1;
		}
		LuckyblockFactions.getInstance().getFactionManager().updateFactionsByOnlinePlayers();
		if(getPageNumber(page) > LuckyblockFactions.getInstance().getFactionManager().getFactionsOrderedOnlinePlayers().size()){
			page = 1;
		}


		final FileConfiguration config = LuckyblockFactions.getInstance().getConfig();
		int pageNumber = getPageNumber(page);
		List<String> message = new ArrayList<>();
		message.add(config.getString("messages.list-header"));

		int j = 0;
		for (int i = pageNumber; i <= pageNumber + 10; i++) {
			if (message.size() == 11) {
				break;
			}
			if(j > LuckyblockFactions.getInstance().getFactionManager().getFactionsOrderedOnlinePlayers().size() - 1) break;
			Faction f = LuckyblockFactions.getInstance().getFactionManager().getFactionsOrderedOnlinePlayers().get(j);
			message.add(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.list-template").replace("%name%", f.getName())
					.replace("%online%", String.valueOf(f.getAmountOfOnlinePlayers()))
					.replace("%size%", String.valueOf(f.getPlayers().size()))));
			j++;
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

	public int getPageNumber(int commandparam){
		return switch (commandparam) {
			case 2 -> 10;
			case 3 -> 20;
			case 4 -> 30;
			case 5 -> 40;
			case 6 -> 50;
			case 7 -> 60;
			case 8 -> 70;
			case 9 -> 80;
			case 10 -> 90;
			case 11 -> 100;
			case 12 -> 110;
			case 13 -> 120;
			case 14 -> 130;
			case 15 -> 140;
			case 16 -> 150;
			case 17 -> 160;
			case 18 -> 170;
			case 19 -> 180;
			case 20 -> 190;
			case 21 -> 200;
			case 22 -> 210;
			case 23 -> 220;
			case 24 -> 230;
			case 25 -> 240;
			default -> 1;
		};
	}
}
