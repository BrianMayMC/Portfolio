package me.nootnoot.headhunting.listeners;

import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.entities.HHPlayer;
import me.nootnoot.headhunting.entities.Level;
import me.nootnoot.headhunting.managers.HHPlayerManager;
import me.nootnoot.headhunting.managers.LevelManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class SignClick implements Listener {

	@EventHandler
	public void onSignClick(PlayerInteractEvent e){
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

		if(!e.getClickedBlock().hasMetadata("sign")) return;


		Sign sign = (Sign) e.getClickedBlock().getState();

		Player p = e.getPlayer();
		HHPlayer hhP = HHPlayerManager.getInstance().getHHPlayer(p);
		Level level = LevelManager.getInstance().byInt(hhP.getLevel());

		String lines = Arrays.toString(sign.getLines()).toLowerCase();

		int price = 0;
		String command = null;

		for(String path : HeadHunting.getInstance().getConfig().getConfigurationSection("signs.").getKeys(false)){
			String fullpath = "signs." + path;
			if(lines.contains(path)){
				price = HeadHunting.getInstance().getConfig().getInt(fullpath + ".price");
				String commandToReplace = HeadHunting.getInstance().getConfig().getString(fullpath + ".command");
				command = commandToReplace.replace("%player%", p.getName());
			}
		}

		if(command == null){
			p.sendMessage(ChatColor.RED + "Command does not exist. Check the config.yml");
			return;
		}

		ArrayList<String> entities = level.getAcceptedEntities();
		boolean accepted = false;
		for(String s : entities){
			if(lines.contains(s)) {
				accepted = true;
				break;
			}
		}

		if(!accepted) {
			p.sendMessage(ChatColor.RED + "You are not allowed to purchase this spawner.");
			return;
		}

		if(!(HeadHunting.getInstance().getEcon().getBalance(p) >= price)){
			p.sendMessage(ChatColor.RED + "You do not have enough money to buy this.");
			p.sendMessage(ChatColor.RED + "Required amount: $" + price);
			return;
		}

		HeadHunting.getInstance().getEcon().withdrawPlayer(p, price);
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
	}
}
