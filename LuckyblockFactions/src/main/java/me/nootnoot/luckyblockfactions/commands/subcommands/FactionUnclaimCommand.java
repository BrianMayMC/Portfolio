package me.nootnoot.luckyblockfactions.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Cuboid;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionPlayer;
import me.nootnoot.luckyblockfactions.entities.FactionRole;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionUnclaimCommand implements SubCommand {
	@Override
	public String getName() {
		return "unclaim";
	}

	@Override
	public int getNeededArgs() {
		return 0;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction unclaim");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 unclaim current land from your faction.");
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
			p.sendMessage(Utils.c("&C&l(!)&c You have to be the leader of the faction to unclaim area."));
			return;
		}

		Faction at = LuckyblockFactions.getInstance().getFactionManager().getFaction(p.getLocation());
		if(at == null){
			p.sendMessage(Utils.c("&c&l(!)&c There is no faction at this land."));
			return;
		}

		if(!(f.getName().equalsIgnoreCase(at.getName()))){
			p.sendMessage(Utils.c("&C&l(!)&c Your faction does not currently own this land."));
			return;
		}

		f.removeArea(p.getLocation(), p);
		p.sendMessage(Utils.c("&a&l(!)&a Successfully unclaimed this piece of land."));
	}



	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {

	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		return null;
	}
}
