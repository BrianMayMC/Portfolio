package me.nootnoot.luckyblockfactions.commands.subcommands;

import lombok.extern.jackson.Jacksonized;
import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionPlayer;
import me.nootnoot.luckyblockfactions.entities.FactionRole;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionDisbandCommand implements SubCommand {
	@Override
	public String getName() {
		return "disband";
	}

	@Override
	public int getNeededArgs() {
		return 0;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction disband");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 disband a faction");
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

		FactionPlayer player = f.getPlayer(p);
		if(!(player.getRole().equals(FactionRole.LEADER))){
			p.sendMessage(Utils.c("&C&l(!)&c You have to be the owner of the faction to disband it!"));
			return;
		}

		LuckyblockFactions.getInstance().getFactionManager().disbandFaction(f);

		final FileConfiguration config = LuckyblockFactions.getInstance().getConfig();
		LuckyblockFactions.getInstance().getServer().broadcastMessage(Utils.c(config.getString("messages.disband-broadcast").replace("%faction%", f.getName())));
		for(FactionPlayer fp : f.getPlayers()){
			Player m = Bukkit.getPlayer(fp.getUuid());
			if(m != null){
				m.sendMessage(Utils.c(config.getString("messages.disband-message")));
			}
		}
		f.getPlayers().clear();
		if(f.getPlayersInChat() != null) f.getPlayersInChat().clear();
	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {

	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		return null;
	}
}
