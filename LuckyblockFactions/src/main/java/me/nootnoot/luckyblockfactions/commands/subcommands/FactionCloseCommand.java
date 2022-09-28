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

public class FactionCloseCommand implements SubCommand {
	@Override
	public String getName() {
		return "close";
	}

	@Override
	public int getNeededArgs() {
		return 0;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction close");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 close your faction from the public.");
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
			p.sendMessage(Utils.c("&C&l(!)&c You have to be Co-Leader+ to close your faction!"));
			return;
		}

		if(!(f.isOpen())){
			p.sendMessage(Utils.c("&c&l(!)&c Your faction is already open!"));
			return;
		}

		f.setOpen(false);
		LuckyblockFactions.getInstance().getServer().broadcastMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.close").replace("%faction%", f.getName())));
	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {

	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		return null;
	}
}
