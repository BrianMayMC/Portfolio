package me.nootnoot.luckyblockfactions.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionWhoCommand implements SubCommand {

	@Override
	public String getName() {
		return "who";
	}

	@Override
	public int getNeededArgs() {
		return 0;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction info &4[faction]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 view information about a faction.");
	}

	@Override
	public String getPermission() {
		return "";
	}

	public String getStatus(Faction f){
		if(LuckyblockFactions.getInstance().getFactionManager().getFrozenFactions().containsKey(f)){
			return Utils.c("&c■");
		}else if(f.getPower() == f.getMaxPower()){
			return Utils.c("&a■");
		}else{
			return Utils.c("&a▲");
		}
	}

	@Override
	public void execute(Player p, String[] args) {
		LuckyblockFactions.getInstance().getFactionManager().updateRanksByKills();
		if(args.length == 0){
			Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(p);
			if(f == null){
				p.sendMessage(Utils.c("&c&l(!)&c You are not in a faction."));
				return;
			}
			if(LuckyblockFactions.getInstance().getFactionManager().getFrozenFactions().containsKey(f)){
				for(String s : LuckyblockFactions.getInstance().getConfig().getStringList("messages.info-frozen")){
					p.sendMessage(Utils.c(s.replace("%faction%", f.getName())
							.replace("%rank%", String.valueOf(f.getRank()))
							.replace("%leader%", f.getLeaderString())
							.replace("%coleader%", f.getCoLeaders())
							.replace("%officers%", f.getOfficers())
							.replace("%members%", f.getMembers())
							.replace("%description%", f.getDescription())
							.replace("%power%", String.valueOf(f.getPower()))
							.replace("%balance%", String.valueOf(f.getBalance()))
							.replace("%status%", getStatus(f))
							.replace("%frozentime%", Utils.formatTime(LuckyblockFactions.getInstance().getFactionManager().getFrozenFactions().getOrDefault(f, 0L)))));
				}
			}else {
				for (String s : LuckyblockFactions.getInstance().getConfig().getStringList("messages.info")) {
					p.sendMessage(Utils.c(s.replace("%faction%", f.getName())
							.replace("%rank%", String.valueOf(f.getRank()))
							.replace("%leader%", f.getLeaderString())
							.replace("%coleader%", f.getCoLeaders())
							.replace("%officers%", f.getOfficers())
							.replace("%members%", f.getMembers())
							.replace("%description%", f.getDescription())
							.replace("%power%", String.valueOf(f.getPower()))
							.replace("%balance%", String.valueOf(f.getBalance()))
							.replace("%status%", getStatus(f))));
				}
			}
		}else if(args.length == 1) {
			Faction f = LuckyblockFactions.getInstance().getFactionManager().getFactionByName(args[0]);
			Player target = Bukkit.getPlayer(args[0]);
			if (f != null) {
				for (String s : LuckyblockFactions.getInstance().getConfig().getStringList("messages.info")) {
					p.sendMessage(Utils.c(s.replace("%faction%", f.getName())
							.replace("%rank%", String.valueOf(f.getRank()))
							.replace("%leader%", f.getLeaderString())
							.replace("%coleader%", f.getCoLeaders())
							.replace("%officers%", f.getOfficers())
							.replace("%members%", f.getMembers())
							.replace("%description%", f.getDescription())
							.replace("%power%", String.valueOf(f.getPower()))
							.replace("%balance%", String.valueOf(f.getBalance()))
							.replace("%status%", getStatus(f))
							.replace("%frozentime%", Utils.formatTime(LuckyblockFactions.getInstance().getFactionManager().getFrozenFactions().getOrDefault(f, 0L)))));
				}
			} else if (target != null) {
				f = LuckyblockFactions.getInstance().getFactionManager().getFaction(target);
				if (f == null) {
					p.sendMessage(Utils.c("&C&l(!)&c Player is not in a faction."));
					return;
				}
				for (String s : LuckyblockFactions.getInstance().getConfig().getStringList("messages.info")) {
					p.sendMessage(Utils.c(s.replace("%faction%", f.getName())
							.replace("%rank%", String.valueOf(f.getRank()))
							.replace("%leader%", f.getLeaderString())
							.replace("%coleader%", f.getCoLeaders())
							.replace("%officers%", f.getOfficers())
							.replace("%members%", f.getMembers())
							.replace("%description%", f.getDescription())
							.replace("%power%", String.valueOf(f.getPower()))
							.replace("%balance%", String.valueOf(f.getBalance()))
							.replace("%status%", getStatus(f))
							.replace("%frozentime%", Utils.formatTime(LuckyblockFactions.getInstance().getFactionManager().getFrozenFactions().getOrDefault(f, 0L)))));
				}
			} else {
				p.sendMessage(Utils.c("&C&l(!)&c A faction / Player with that name does not exist."));
			}
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
