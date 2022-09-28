package me.nootnoot.jackpotmclifesteal.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmclifesteal.JackpotMCLifesteal;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class HeartsResetCommand implements SubCommand {
	@Override
	public String getName() {
		return "reset";
	}

	@Override
	public int getNeededArgs() {
		return 1;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/lifesteal reset &4[player]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 reset someones hearts");
	}

	@Override
	public String getPermission() {
		return "jackpotmclifesteal.hearts.reset";
	}

	@Override
	public void execute(Player p, String[] args) {
		Player target = Bukkit.getPlayer(args[0]);
		if(target == null){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid player."));
			return;
		}

		target.getPersistentDataContainer().set(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE, 20d);
		AttributeInstance attribute = target.getAttribute(Attribute.GENERIC_MAX_HEALTH);

		attribute.setBaseValue(target.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE));
		target.setHealthScale(attribute.getBaseValue());
		target.setHealth(20);

		p.sendMessage(Utils.c("&a&l(!)&a Successfully reset " + target.getName() + "'s hearts."));
		target.sendMessage(Utils.c("&c&l(!)&c Your hearts have been reset!"));
	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {
		Player target = Bukkit.getPlayer(args[0]);
		if(target == null){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid player."));
			return;
		}

		target.getPersistentDataContainer().set(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE, 20d);
		AttributeInstance attribute = target.getAttribute(Attribute.GENERIC_MAX_HEALTH);

		attribute.setBaseValue(target.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE));
		target.setHealthScale(attribute.getBaseValue());
		target.setHealth(20);

		p.sendMessage(Utils.c("&a&l(!)&a Successfully reset " + target.getName() + "'s hearts."));
		target.sendMessage(Utils.c("&c&l(!)&c Your hearts have been reset!"));
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		return null;
	}
}
