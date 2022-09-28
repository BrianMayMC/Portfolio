package me.nootnoot.luckyblockfactions.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionPlayer;
import me.nootnoot.luckyblockfactions.entities.FactionRole;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionOpenCommand implements SubCommand {
	@Override
	public String getName() {
		return "open";
	}

	@Override
	public int getNeededArgs() {
		return 0;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction open");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 open a faction for anyone to join");
	}

	@Override
	public String getPermission() {
		return "";
	}

	@Override
	public void execute(Player p, String[] args) {
		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(p);
		if(f == null){
			p.sendMessage(Utils.c("&C&l(!)&C You are not in a faction!"));
			return;
		}

		FactionPlayer fp = f.getPlayer(p);
		if(!(fp.getRole().equals(FactionRole.CO_LEADER)) && !(fp.getRole().equals(FactionRole.LEADER))){
			p.sendMessage(Utils.c("&C&l(!)&c You have to be Co-Leader+ to open your faction!"));
			return;
		}

		if(f.isOpen()){
			p.sendMessage(Utils.c("&c&l(!)&c Your faction is already open!"));
			return;
		}

		f.setOpen(true);
		LuckyblockFactions.getInstance().getServer().broadcastMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.open").replace("%faction%", f.getName())));
	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {

	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		return null;
	}
}
