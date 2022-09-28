package me.nootnoot.luckyblockfactions.listeners;

import me.nootnoot.framework.registrysystem.Registry;
import me.nootnoot.framework.registrysystem.RegistryType;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

@Registry(type = RegistryType.LISTENER)
public class FactionEnterListener implements Listener {

	private final Map<Player, Faction> players = new HashMap<>();

	@EventHandler
	public void walk(PlayerMoveEvent e){
		Location t = e.getTo();
		Location from = e.getFrom();
		if (t.getBlockX() == from.getBlockX() && t.getBlockY() == from.getBlockY() &&
				t.getBlockZ() == from.getBlockZ()) {
			return;
		}
		Player p = e.getPlayer();
		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(t);
		if(f == null){
			if (players.get(p) != null) {
				e.getPlayer().sendTitle(Utils.c("&2Wilderness"),
						"", 18, 20, 18);
				players.remove(p);
			}
			return;
		}

		if(players.get(p) != null) {
			if (players.get(p).getName().equalsIgnoreCase(f.getName())) return;
		}
		players.put(p, f);
		final FileConfiguration config = LuckyblockFactions.getInstance().getConfig();
		e.getPlayer().sendTitle(Utils.c(config.getString("messages.enter-title").replace("%faction%", f.getName())),
				Utils.c(config.getString("messages.enter-subtitle").replace("%description%", Utils.c(f.getDescription()))), 18, 20, 18);
	}
}
