package me.nootnoot.jackpotmcitems.commands;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.interfaces.CustomItem;
import me.nootnoot.jackpotmcitems.utils.Utils;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainCommand implements TabExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender instanceof Player p) {
			if(!p.hasPermission("jackpotmcitems.admin")){
				p.sendMessage("Unknown command. Type /help for help.");
				return true;
			}
			/*
			Command executed: /customitems reload or /customitems
			 */
			if (args.length == 1) {
				if(args[0].equalsIgnoreCase("reload")){
					try {
						JackpotMCItems.getInstance().reload();
					} catch (IOException | InvalidConfigurationException e) {
						e.printStackTrace();
					}
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSuccessfully reloaded the config."));
				}else{
					p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("INVALID_ARGS").replace("%command%", "/customitems give [player] [item]")));
				}
				return true;
			}
			/*
			Command executed: /customitems give [item]
			(This gives the item to yourself)
			 */
			else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("give")) {
					CustomItem customItem = JackpotMCItems.getInstance().getItemManager().getItem(args[1]);
					if (customItem != null) {
						p.getInventory().addItem(customItem.getItem());
					} else {
						p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("INVALID_ITEM").replace("%item%", args[1])));
					}
					return true;
				}
			}
			/*
			Command executed /customitems give [player] [item]
			(This gives the item to the player mentioned)
			 */
			else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("give")) {
					Player target = Bukkit.getPlayer(args[1]);
					if (target == null) {
						p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("INVALID_PLAYER").replace("%player%", args[1])));
						return true;
					}
					CustomItem customItem = JackpotMCItems.getInstance().getItemManager().getItem(args[2]);
					if (customItem != null) {
						target.getInventory().addItem(customItem.getItem());
						p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("SUCCESSFULLY_GIVEN")
										.replace("%player%", target.getName())
										.replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))
										.replace("%amount%", String.valueOf(1))));
						target.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("SUCCESSFULLY_RECEIVED")
								.replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))
								.replace("%amount%", String.valueOf(1))));

						return true;
					} else {
						p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("INVALID_ITEM").replace("%item%", args[2])));
						return true;
					}
				} else {
					p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("INVALID_ARGS").replace("%command%", "/customitems give [player] [item]")));
					return true;
				}
		}
			/*
			Command executed /customitems give [player] [item] [amount]
			(This gives the item to the player mentioned)
			 */
			else if (args.length == 4) {
				if (args[0].equalsIgnoreCase("give")) {
					Player target = Bukkit.getPlayer(args[1]);
					if (target == null) {
						p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("INVALID_PLAYER").replace("%player%", args[1])));
						return true;
					}
					CustomItem customItem = JackpotMCItems.getInstance().getItemManager().getItem(args[2]);
					if (customItem != null) {
						int amount;
						try{
							amount = Integer.parseInt(args[3]);
						}catch(NumberFormatException e){
							p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("INVALID_NUMBER").replace("%number%", args[3])));
							return true;
						}
						if(amount < 1){
							p.sendMessage(Utils.c("&c&l(!)&c Amount cannot be less than 0."));
							return true;
						}

						for(int i = 0; i < amount; i++){
							target.getInventory().addItem(customItem.getItem());
						}

						p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("SUCCESSFULLY_GIVEN")
										.replace("%player%", target.getName())
										.replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))
										.replace("%amount%", String.valueOf(amount))));
						target.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("SUCCESSFULLY_RECEIVED")
										.replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))
										.replace("%amount%", String.valueOf(amount))));
					} else {
						p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("INVALID_ITEM").replace("%item%", args[2])));
					}
				} else {
					p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("INVALID_ARGS").replace("%command%", "/customitems give [player] [item]")));
				}
				return true;
			}else {
			p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("INVALID_ARGS").replace("%command%", "/customitems give [player] [item]")));
			return true;
		}
	}
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		if(!sender.hasPermission("jackpotmcitems.admin")) return null;
		if(args.length == 1){
			return List.of("give", "reload");
		}else if(args.length == 2){
			List<String> names = new ArrayList<>();
			for(Player p : Bukkit.getOnlinePlayers()){
				names.add(p.getName());
			}
			return names;
		}else if(args.length == 3){
			List<String> itemNames = new ArrayList<>();
			for(CustomItem item : JackpotMCItems.getInstance().getItemManager().getCustomItems()){
				itemNames.add(item.getName());
			}
			return itemNames;
		}
		else if(args.length == 4){
			return List.of("[amount]");
		}
		return null;
	}
}
