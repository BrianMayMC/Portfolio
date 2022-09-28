package me.nootnoot.luckyblockfactions.listeners;

import me.nootnoot.framework.registrysystem.Registry;
import me.nootnoot.framework.registrysystem.RegistryType;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionPlayer;
import me.nootnoot.luckyblockfactions.entities.FactionRole;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Registry(type = RegistryType.LISTENER)
public class PlayerChatListener implements Listener {

	@EventHandler
	public void chat(AsyncPlayerChatEvent e){
		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(e.getPlayer());
		if(f == null) return;
		if(f.getPlayersInChat() == null) return;
		if(!(f.getPlayersInChat().contains(e.getPlayer()))) return;
		e.setCancelled(true);
		FactionPlayer own = f.getPlayer(e.getPlayer());
		for(FactionPlayer p : f.getPlayers()){
			Player pp  = Bukkit.getPlayer(p.getUuid());
			if(pp != null){
				if(own.getRole() == FactionRole.MEMBER){
					pp.sendMessage(Utils.c("&4" + e.getPlayer().getName() + " &6»" + e.getMessage()));
				}else if(own.getRole() == FactionRole.OFFICER){
					pp.sendMessage(Utils.c("&4" + e.getPlayer().getName() + " &4⭐» &6" + e.getMessage()));
				}else if(own.getRole() == FactionRole.CO_LEADER){
					pp.sendMessage(Utils.c("&4" + e.getPlayer().getName() + " &4⭐⭐» &6" + e.getMessage()));
				}else{
					pp.sendMessage(Utils.c("&4" + e.getPlayer().getName() + " &4⭐⭐⭐» &6" + e.getMessage()));
				}
			}
		}
	}
}
