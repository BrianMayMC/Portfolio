package me.nootnoot.ventedmasks.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.tr7zw.nbtapi.NBTItem;
import jdk.nashorn.internal.objects.annotations.Getter;
import me.nootnoot.ventedmasks.VentedMasks;
import me.nootnoot.ventedmasks.entities.Mask;
import net.minecraft.server.v1_8_R3.NBTReadLimiter;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

public class GiveMask implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = null;
		ConsoleCommandSender console = null;
		if(sender instanceof Player) {
			p = (Player) sender;
			if(!p.hasPermission("masks.give")) return true;

			if (args.length != 4) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Incorrect command."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCorrect command: /mm give <ign> <maskname> <level>"));
				return true;
			}
			if (!args[0].equalsIgnoreCase("give")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Incorrect command."));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCorrect command: /mm give <ign> <maskname> <level>"));
				return true;
			}
		}else if(sender instanceof ConsoleCommandSender){
			console = (ConsoleCommandSender) sender;

			if (args.length != 4) {
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Incorrect command."));
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCorrect command: /mm give <ign> <maskname> <level>"));
				return true;
			}
			if (!args[0].equalsIgnoreCase("give")) {
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Incorrect command."));
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCorrect command: /mm give <ign> <maskname> <level>"));
				return true;
			}
		}
			Player target = Bukkit.getPlayer(args[1]);
			Mask mask = null;

			for (Mask findmask : VentedMasks.getMaskManager().getMasks()) {
				if (findmask.getConfigname().equalsIgnoreCase(args[2]))
					mask = findmask;
			}

			if (mask == null) {
				if(p != null)
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Mask does not exist."));
				if(console != null)
					console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c Mask does not exist."));
				return true;
			}

			ItemStack is = mask.getMask(Integer.parseInt(args[3]));
			ItemMeta is_meta = is.getItemMeta();

			NBTItem nbtIs = new NBTItem(is);
			int level = nbtIs.getInteger("level");


			String name = is_meta.getDisplayName();

			if (level == 1) {
				name = name.replace("%level%", "I");
			} else if (level == 2) {
				name = name.replace("%level%", "II");
			} else if (level == 3) {
				name = name.replace("%level%", "III");
			} else if (level == 4) {
				name = name.replace("%level%", "IV");
			} else if (level == 5) {
				name = name.replace("%level%", "V");
			}


			is_meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
			is_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

			ArrayList<String> lore = new ArrayList<>();
			for(String s : mask.getLore()){
				if(s.contains("%level%")) {
					if (level == 1) {
						lore.add(s.replace("%level%", "I"));
					} else if (level == 2) {
						lore.add(s.replace("%level%", "II"));
					} else if (level == 3) {
						lore.add(s.replace("%level%", "III"));
					} else if (level == 4) {
						lore.add(s.replace("%level%", "IV"));
					} else if (level == 5) {
						lore.add(s.replace("%level%", "V"));
					}
				}else{
					lore.add(s);
				}
			}
			is_meta.setLore(lore);
			is_meta.setDisplayName(name);
			is.setItemMeta(is_meta);

			target.getInventory().addItem(is);

		return true;
	}
}
