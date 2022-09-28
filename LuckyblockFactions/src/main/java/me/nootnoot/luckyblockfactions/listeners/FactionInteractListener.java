package me.nootnoot.luckyblockfactions.listeners;

import jdk.jfr.Enabled;
import lombok.extern.jackson.Jacksonized;
import me.nootnoot.framework.registrysystem.Registry;
import me.nootnoot.framework.registrysystem.RegistryType;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

@Registry(type = RegistryType.LISTENER)
public class FactionInteractListener implements Listener {

	@EventHandler
	public void interact(PlayerInteractEvent e){
		if(e.getClickedBlock() == null) return;
		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(e.getClickedBlock().getLocation());
		Faction playerF = LuckyblockFactions.getInstance().getFactionManager().getFaction(e.getPlayer());
		if(f == null) return;
		if(f.getPower() > 0) {
			if(playerF == null){
				e.setCancelled(true);
			}else {
				if (!(f.equals(playerF))) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void br(BlockBreakEvent e){
		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(e.getBlock().getLocation());
		Faction playerF = LuckyblockFactions.getInstance().getFactionManager().getFaction(e.getPlayer());
		if(f == null) return;
		if(f.getPower() > 0) {
			if(playerF == null){
				e.setCancelled(true);
			}else {
				if (!(f.equals(playerF))) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void place(BlockPlaceEvent e){
		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(e.getBlock().getLocation());
		Faction playerF = LuckyblockFactions.getInstance().getFactionManager().getFaction(e.getPlayer());
		if(f == null) return;
		if(f.getPower() > 0) {
			if(playerF == null){
				e.setCancelled(true);
			}else {
				if (!(f.equals(playerF))) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void command(PlayerCommandPreprocessEvent e){
		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(e.getPlayer().getLocation());
		if(f == null) return;
		Faction playerF = LuckyblockFactions.getInstance().getFactionManager().getFaction(e.getPlayer());
		if(playerF != null) {
			if (f.getName().equalsIgnoreCase(playerF.getName())) return;
		}
		List<String> denied = LuckyblockFactions.getInstance().getConfig().getStringList("denied-commands-in-factions");
		for(String s : denied){
			if(e.getMessage().contains(s)){
				e.getPlayer().sendMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.denied-message")));
				e.setCancelled(true);
			}
		}
	}
}
