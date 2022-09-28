package me.nootnoot.luckyblockfactions.commands.subcommands;

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

public class FactionDescriptionCommand implements SubCommand {
	@Override
	public String getName() {
		return "description";
	}

	@Override
	public int getNeededArgs() {
		return 1;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction description &4[description]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 set your factions description");
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
		if(fp.getRole() == FactionRole.MEMBER){
			p.sendMessage(Utils.c("&C&l(!)&C You need to be Officer+ to be change your factions description!"));
			return;
		}
		StringBuilder builder = new StringBuilder();
		for (String arg : args) {
			builder.append(arg);
			builder.append(" ");
		}

		if(builder.toString().length() > 128){
			p.sendMessage(Utils.c("&c&l(!)&c Faction description cannot be longer than 128 characters."));
			return;
		}
		f.setDescription(Utils.c(builder.toString()));
		for(FactionPlayer fpp : f.getPlayers()){
			Player t = Bukkit.getPlayer(fpp.getUuid());
			if(t != null){
				t.sendMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.description").replace("%description%", builder.toString())));
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
