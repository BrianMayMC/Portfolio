package me.nootnoot.luckyblockfactions.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Cuboid;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionClaim;
import me.nootnoot.luckyblockfactions.entities.FactionPlayer;
import me.nootnoot.luckyblockfactions.entities.FactionRole;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionUnclaimAllCommand implements SubCommand {
	@Override
	public String getName() {
		return "unclaimall";
	}

	@Override
	public int getNeededArgs() {
		return 0;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction unclaimall");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 unclaim all the land in a faction.");
	}

	@Override
	public String getPermission() {
		return "";
	}

	@Override
	public void execute(Player p, String[] args) {
		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(p);
		if(f == null){
			p.sendMessage(Utils.c("&C&l(!)&c You do not have a faction."));
			return;
		}
		FactionPlayer fp = f.getPlayer(p);
		if(fp.getRole() != FactionRole.LEADER){
			p.sendMessage(Utils.c("&C&l(!)&c You have to be the leader of the faction to claim area."));
			return;
		}

		for(FactionClaim c : f.getAreas()){
			LuckyblockFactions.getInstance().getEcon().depositPlayer(p, c.getPrice());
		}
		f.getAreas().clear();
		p.sendMessage(Utils.c("&a&l(!)&a Successfully unclaimed all your land."));
	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {

	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		return null;
	}
}
