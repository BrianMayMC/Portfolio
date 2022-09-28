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

public class FactionPromoteCommand implements SubCommand {
	@Override
	public String getName() {
		return "promote";
	}

	@Override
	public int getNeededArgs() {
		return 1;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction promote &4[player]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 promote someone in your faction.");
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
			p.sendMessage(Utils.c("&C&l(!)&C You need to be Leader to be able to promote players!"));
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
			p.sendMessage(Utils.c("&C&l(!)&c You cannot promote yourself."));
			return;
		}

		if(tp.getRole() == FactionRole.MEMBER){
			tp.setRole(FactionRole.OFFICER);
			target.sendMessage(Utils.c("&a&l(!)&a You have been promoted to Officer!"));
			p.sendMessage(Utils.c("&a&l(!)&a You have successfully promoted " + target.getName() + " to Officer."));
		}else if(tp.getRole() == FactionRole.OFFICER){
			tp.setRole(FactionRole.CO_LEADER);
			target.sendMessage(Utils.c("&a&l(!)&a You have been promoted to Co-Leader!"));
			p.sendMessage(Utils.c("&a&l(!)&a You have successfully promoted " + target.getName() + " to Co-Leader."));
		}else if(tp.getRole() == FactionRole.CO_LEADER){
			p.sendMessage(Utils.c("&C&l(!)&c You cannot promote a Co-Leader. Transfer the faction instead."));
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
