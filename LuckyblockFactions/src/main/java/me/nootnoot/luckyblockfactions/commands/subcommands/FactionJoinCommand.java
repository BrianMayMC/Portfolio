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
import org.bukkit.entity.Player;

import java.util.List;

public class FactionJoinCommand implements SubCommand {
	@Override
	public String getName() {
		return "join";
	}

	@Override
	public int getNeededArgs() {
		return 1;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction join &4[faction]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 join a faction.");
	}

	@Override
	public String getPermission() {
		return "";
	}

	@Override
	public void execute(Player p, String[] args) {
		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(p);
		if(f != null){
			p.sendMessage(Utils.c("&C&l(!)&c You are already in a faction! Do /faction leave to leave your current faction!"));
			return;
		}

		Faction tf = LuckyblockFactions.getInstance().getFactionManager().getFactionByName(args[0]);
		Player target = Bukkit.getPlayer(args[0]);
		if(target != null){
			tf = LuckyblockFactions.getInstance().getFactionManager().getFaction(target);
		}
		if(tf == null){
			p.sendMessage(Utils.c("&C&l(!)&c A faction with this name does not exist."));
			return;
		}

		if(!(tf.isOpen())) {
			if (!(tf.getInvites().contains(p.getUniqueId()))) {
				p.sendMessage(Utils.c("&C&l(!)&c You have not been invited to this faction."));
				return;
			}
		}

		if(tf.getPlayers().size() >= tf.getMaxSize()){
			p.sendMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.max-members-join")));
			return;
		}

		tf.getInvites().remove(p.getUniqueId());
		tf.getPlayers().add(new FactionPlayer(p.getUniqueId(), FactionRole.MEMBER, p.getName()));
		for(FactionPlayer fp : tf.getPlayers()){
			Player t = Bukkit.getPlayer(fp.getUuid());
			if(t != null){
				t.sendMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.join-message").replace("%player%", p.getName())));
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
