package me.nootnoot.luckyblockfactions.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.SimpleLocation;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionPlayer;
import me.nootnoot.luckyblockfactions.entities.FactionRole;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionSetHomeCommand implements SubCommand {
	@Override
	public String getName() {
		return "sethome";
	}

	@Override
	public int getNeededArgs() {
		return 0;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction sethome");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 set the home of your faction");
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
			p.sendMessage(Utils.c("&C&l(!)&c Only the leader of the faction can change the home."));
			return;
		}

		f.setHome(new SimpleLocation(p.getLocation()));
		for(FactionPlayer pp : f.getPlayers()){
			Player b = Bukkit.getPlayer(pp.getUuid());
			if(b != null){
				b.sendMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.sethome")));
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
