package me.nootnoot.jackpotmclifesteal.commands.subcommands;

import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmclifesteal.JackpotMCLifesteal;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class HeartsSetCommand implements SubCommand {
	@Override
	public String getName() {
		return "set";
	}

	@Override
	public int getNeededArgs() {
		return 2;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/hearts set &4[player] [amount]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 set someones hearts.");
	}

	@Override
	public String getPermission() {
		return "jackpotmclifesteal.hearts.set";
	}

	@Override
	public void execute(Player p, String[] args) {
		Player target = Bukkit.getPlayer(args[0]);
		if(target == null){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid player."));
			return;
		}

		double amount;
		try{
			amount = Double.parseDouble(args[1]);
		}catch(NumberFormatException e){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid amount."));
			return;
		}

		if(amount > JackpotMCLifesteal.getInstance().getConfig().getDouble("max-hearts")){
			p.sendMessage(Utils.c("&c&l(!)&c This amount would exceed the max amount of hearts!"));
			return;
		}

		target.getPersistentDataContainer().set(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE, amount * 2);
		AttributeInstance attribute = target.getAttribute(Attribute.GENERIC_MAX_HEALTH);

		attribute.setBaseValue(target.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE));
		target.setHealthScale(attribute.getBaseValue());
		target.setHealth(target.getMaxHealth());

		p.sendMessage(Utils.c("&a&l(!)&a Successfully set " + target.getName() + "'s hearts to " + amount + "."));
		target.sendMessage(Utils.c("&c&l(!)&c Your hearts have been set to " + amount + "!"));
	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {
		Player target = Bukkit.getPlayer(args[0]);
		if(target == null){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid player."));
			return;
		}

		double amount;
		try{
			amount = Double.parseDouble(args[1]);
		}catch(NumberFormatException e){
			p.sendMessage(Utils.c("&c&l(!)&c Invalid amount."));
			return;
		}

		if(amount > JackpotMCLifesteal.getInstance().getConfig().getDouble("max-hearts")){
			p.sendMessage(Utils.c("&c&l(!)&c This amount would exceed the max amount of hearts!"));
			return;
		}

		target.getPersistentDataContainer().set(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE, amount * 2);
		AttributeInstance attribute = target.getAttribute(Attribute.GENERIC_MAX_HEALTH);

		attribute.setBaseValue(target.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE));
		target.setHealthScale(attribute.getBaseValue());
		target.setHealth(target.getMaxHealth());

		p.sendMessage(Utils.c("&a&l(!)&a Successfully set " + target.getName() + "'s hearts to " + amount + "."));
		target.sendMessage(Utils.c("&c&l(!)&c Your hearts have been set to " + amount + "!"));
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 2){
			return List.of("[amount]");
		}
		return null;
	}
}
