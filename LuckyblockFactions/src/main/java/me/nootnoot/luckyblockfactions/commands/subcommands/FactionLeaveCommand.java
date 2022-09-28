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

public class FactionLeaveCommand implements SubCommand {
	@Override
	public String getName() {
		return "leave";
	}

	@Override
	public int getNeededArgs() {
		return 0;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction leave");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 leave a faction");
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
		if(fp.getRole() == FactionRole.LEADER){
			p.sendMessage(Utils.c("&C&l(!)&c You can't leave your own faction! Disband it instead."));
			return;
		}

		if(f.getPower() < 0){
			p.sendMessage(Utils.c("&C&l(!)&c You can't leave a faction with negative power!"));
			return;
		}

		f.getPlayers().remove(fp);
		f.setPower(Math.min(f.getPower(), f.getMaxPower()));
		if(f.getPlayersInChat() != null) f.getPlayersInChat().remove(p);

		final FileConfiguration config = LuckyblockFactions.getInstance().getConfig();
		p.sendMessage(Utils.c(config.getString("messages.leave-message-player").replace("%faction%", f.getName())));

		for(FactionPlayer ff : f.getPlayers()){
			Player t = Bukkit.getPlayer(ff.getUuid());
			if(t != null){
				t.sendMessage(Utils.c(config.getString("messages.leave-message-faction").replace("%player%", p.getName())));
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
