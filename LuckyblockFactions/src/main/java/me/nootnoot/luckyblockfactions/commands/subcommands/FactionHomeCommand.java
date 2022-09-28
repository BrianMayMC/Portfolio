package me.nootnoot.luckyblockfactions.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class FactionHomeCommand implements SubCommand {
	@Override
	public String getName() {
		return "home";
	}

	@Override
	public int getNeededArgs() {
		return 0;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction home");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 teleport to your faction home");
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

		if(f.getHome() == null){
			p.sendMessage(Utils.c("&C&l(!)&c Your faction does not have a home set."));
			return;
		}
		LuckyblockFactions.getInstance().getFactionManager().getPlayerTeleporting().add(p);

		p.sendMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.home")));
		new BukkitRunnable(){
			@Override
			public void run(){
				if(LuckyblockFactions.getInstance().getFactionManager().getPlayerTeleporting().contains(p)){
					p.teleport(f.getHome().toLocationFull());
					LuckyblockFactions.getInstance().getFactionManager().getPlayerTeleporting().remove(p);
				}
			}
		}.runTaskLater(LuckyblockFactions.getInstance(), LuckyblockFactions.getInstance().getConfig().getLong("home-cooldown") * 20L);

	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {

	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		return null;
	}
}
