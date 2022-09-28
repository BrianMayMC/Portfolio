package me.nootnoot.luckyblockfactions.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionPlayer;
import me.nootnoot.luckyblockfactions.entities.FactionRole;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionDemoteCommand implements SubCommand {
	@Override
	public String getName() {
		return "demote";
	}

	@Override
	public int getNeededArgs() {
		return 1;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction demote &4[player]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 demote someone in your faction.");
	}

	@Override
	public String getPermission() {
		return "";
	}

	@Override
	public void execute(Player p, String[] args) {
		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(p);
		if(f == null){
			p.sendMessage(Utils.c("&C&l(!)&c You are not in a faction."));
			return;
		}
		FactionPlayer fp = f.getPlayer(p);
		if(fp.getRole() != FactionRole.LEADER){
			p.sendMessage(Utils.c("&C&l(!)&C You need to be Leader to be able to demote players!"));
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if(target == null){
			p.sendMessage(Utils.c("&C&l(!)&c Invalid player."));
			return;
		}

		FactionPlayer tp = f.getPlayer(target);
		if(tp == null){
			p.sendMessage(Utils.c("&C&l(!)&C Player is not in your faction."));
			return;
		}

		if(target.equals(p)){
			p.sendMessage(Utils.c("&C&l(!)&c You cannot demote yourself."));
			return;
		}

		if(tp.getRole() == FactionRole.OFFICER){
			tp.setRole(FactionRole.MEMBER);
			target.sendMessage(Utils.c("&c&l(!)&c You have been demoted to Member!"));
			p.sendMessage(Utils.c("&c&l(!)&a You have successfully demoted " + target.getName() + " to Member."));
		}else if(tp.getRole() == FactionRole.CO_LEADER){
			tp.setRole(FactionRole.OFFICER);
			target.sendMessage(Utils.c("&c&l(!)&c You have been demoted to Officer!"));
			p.sendMessage(Utils.c("&c&l(!)&a You have successfully demoted " + target.getName() + " to Officer."));
		}else if(tp.getRole() == FactionRole.MEMBER){
			p.sendMessage(Utils.c("&C&l(!)&c You cannot demote a Member. Kick them instead."));
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
