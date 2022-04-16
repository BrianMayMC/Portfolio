package me.nootnoot.headhunting.listeners;

import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import me.nootnoot.headhunting.HeadHunting;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerKillEvent implements Listener {


	@EventHandler
	public void onPlayerKill(EntityDeathEvent e){
		if(!(e.getEntity() instanceof Player)) return;

		Player deadP = (Player) e.getEntity();

		ItemStack pHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta pHeadMeta = (SkullMeta) pHead.getItemMeta();

		double balance = ((HeadHunting.getInstance().getEcon().getBalance(deadP) / 100) * 20);
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.translateAlternateColorCodes('&', "&cThis is a player head!"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&cRight click to get 20% of the owners balance!"));
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&cAmount: &a$" + balance));
		pHeadMeta.setLore(lore);
		pHeadMeta.setOwner(deadP.getName());
		pHead.setItemMeta(pHeadMeta);

		NBTItem nbtHead = new NBTItem(pHead);
		nbtHead.setBoolean("playerhead", true);
		nbtHead.setString("name", deadP.getName());
		nbtHead.setString("uuid", deadP.getUniqueId().toString());
		nbtHead.setDouble("balance", balance);
		nbtHead.setString("id", String.valueOf(UUID.randomUUID()));

		deadP.getWorld().dropItem(deadP.getLocation(), nbtHead.getItem());
		deadP.setLevel(deadP.getLevel());
		deadP.setExp(deadP.getExp());
	}
}
