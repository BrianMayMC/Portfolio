package me.nootnoot.luckyblockfactions.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionPlayer;
import me.nootnoot.luckyblockfactions.entities.FactionRole;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionKickCommand implements SubCommand {
	@Override
	public String getName() {
		return "kick";
	}

	@Override
	public int getNeededArgs() {
		return 1;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction kick &4[player]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 kick a player from your faction.");
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
		OfflinePlayer target = Bukkit.getPlayer(args[0]);
		if(target == null){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid player."));
			return;
		}

		FactionPlayer fp = f.getPlayer(p);
		if(fp.getRole() == FactionRole.MEMBER){
			p.sendMessage(Utils.c("&C&l(!)&C You need to be Officer+ to be able to kick players!"));
			return;
		}

		FactionPlayer tp = f.getPlayer(target.getUniqueId());
		if(tp == null){
			p.sendMessage(Utils.c("&c&l(!)&c This player is not in your faction."));
			return;
		}

		if(p.getUniqueId().equals(target.getUniqueId())){
			p.sendMessage(Utils.c("&C&l(!)&C You cannot kick yourself from the faction."));
			return;
		}

		f.getPlayers().remove(tp);
		f.setPower(Math.min(f.getPower(), f.getMaxPower()));
		if(target.isOnline()){
			target.getPlayer().sendMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.kick")));
		}
		for(FactionPlayer fpp : f.getPlayers()){
			Player t = Bukkit.getPlayer(fpp.getUuid());
			if(t != null){
				t.sendMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.kick-faction").replace("%player%", target.getName())));
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
