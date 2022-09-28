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

public class FactionTransferCommand implements SubCommand {
	@Override
	public String getName() {
		return "transfer";
	}

	@Override
	public int getNeededArgs() {
		return 1;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction transfer &4[player]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 transfer the factions leadership to a player in your faction");
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

		Player target = Bukkit.getPlayer(args[0]);
		if(target == null){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid player."));
			return;
		}

		FactionPlayer tp = f.getPlayer(target);
		if(tp == null){
			p.sendMessage(Utils.c("&C&l(!)&c Player is not in your faction."));
			return;
		}

		FactionPlayer fp = f.getPlayer(p);
		if(!(fp.getRole().equals(FactionRole.LEADER))){
			p.sendMessage(Utils.c("&C&l(!)&c Only the leader of the faction can transfer ownership."));
			return;
		}

		tp.setRole(FactionRole.LEADER);
		fp.setRole(FactionRole.CO_LEADER);
		for(FactionPlayer pp : f.getPlayers()){
			Player t = Bukkit.getPlayer(pp.getUuid());
			if(t != null){
				t.sendMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.transfer").replace("%player%", target.getName())));
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
