package me.nootnoot.luckyblockfactions.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionChatCommand implements SubCommand {
	@Override
	public String getName() {
		return "chat";
	}

	@Override
	public int getNeededArgs() {
		return 0;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction chat");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 enters/leaves the faction chat");
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

		final FileConfiguration config = LuckyblockFactions.getInstance().getConfig();
		if(f.getPlayersInChat() != null) {
			if (f.getPlayersInChat().contains(p)) {
				p.sendMessage(Utils.c(config.getString("messages.chat-leave")));
				f.getPlayersInChat().remove(p);
			}
		} else{
			p.sendMessage(Utils.c(config.getString("messages.chat-enter")));
			f.getPlayersInChat().add(p);
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
