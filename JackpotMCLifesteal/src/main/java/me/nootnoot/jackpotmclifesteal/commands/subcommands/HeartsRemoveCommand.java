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

import java.util.ArrayList;
import java.util.List;

public class HeartsRemoveCommand implements SubCommand {
	@Override
	public String getName() {
		return "remove";
	}

	@Override
	public int getNeededArgs() {
		return 2;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/hearts remove &4[player] [amount]");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 remove hearts from someone");
	}

	@Override
	public String getPermission() {
		return "jackpotmclifesteal.hearts.remove";
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
		double hearts = target.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE);

		if(hearts - amount < 1){
			p.sendMessage(Utils.c("&c&l(!)&c This amount is too low!"));
			return;
		}

		double current = target.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE);
		if(amount + hearts / 2 >= current){
			p.sendMessage(Utils.c("&C&l(!)&C This amount is too big!"));
			return;
		}

		target.getPersistentDataContainer().set(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE, target.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE) - amount * 2);
		AttributeInstance attribute = target.getAttribute(Attribute.GENERIC_MAX_HEALTH);

		attribute.setBaseValue(target.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE));
		target.setHealthScale(attribute.getBaseValue());
		target.setHealth(target.getMaxHealth());
		target.sendMessage(Utils.c("&c&l(!)&c " + amount / 2 + " hearts have been removed from you!"));
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

		if(amount < 1){
			p.sendMessage(Utils.c("&c&l(!)&c This amount is too low!"));
			return;
		}
		double current = target.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE);
		if(amount >= current){
			p.sendMessage(Utils.c("&C&l(!)&C This amount is too big!"));
			return;
		}
		target.getPersistentDataContainer().set(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE, target.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE) - amount * 2);
		AttributeInstance attribute = target.getAttribute(Attribute.GENERIC_MAX_HEALTH);

		attribute.setBaseValue(target.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE));
		target.setHealthScale(attribute.getBaseValue());
		target.setHealth(target.getMaxHealth());
		target.sendMessage(Utils.c("&c&l(!)&c " + amount / 2 + " hearts have been removed from you!"));


	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 2){
			return List.of("[amount]");
		}
		return null;
	}
}
