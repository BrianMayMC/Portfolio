package me.nootnoot.luckyblockfactions.commands.subcommands;

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

public class FactionRevokeCommand implements SubCommand {
	@Override
	public String getName() {
		return "revoke";
	}

	@Override
	public int getNeededArgs() {
		return 1;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction revoke &4[player]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 revoke someones invite to your faction");
	}

	@Override
	public String getPermission() {
		return "";
	}

	@Override
	public void execute(Player p, String[] args) {
		Player target = Bukkit.getPlayer(args[0]);
		if(target == null){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid player."));
			return;
		}

		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(p);
		if(f == null){
			p.sendMessage(Utils.c("&C&l(!)&c You are not in a faction."));
			return;
		}
		FactionPlayer fp = f.getPlayer(p);
		if(fp.getRole() == FactionRole.MEMBER){
			p.sendMessage(Utils.c("&C&l(!)&C You need to be Officer+ to be able to revoke players' invites!"));
			return;
		}

		if(!(f.getInvites().contains(target.getUniqueId()))){
			p.sendMessage(Utils.c("&C&l(!)&c Player has not been invited to your faction."));
		}

		f.getInvites().remove(target.getUniqueId());

		final FileConfiguration config = LuckyblockFactions.getInstance().getConfig();
		p.sendMessage(Utils.c(config.getString("messages.revoke-message-player").replace("%player%", target.getName())));
		target.sendMessage(Utils.c(config.getString("messages.revoke-message-target").replace("%faction%", f.getName())));
	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {

	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		return null;
	}
}
