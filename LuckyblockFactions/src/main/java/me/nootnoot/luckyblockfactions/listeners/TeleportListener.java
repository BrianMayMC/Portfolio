package me.nootnoot.luckyblockfactions.listeners;

import me.nootnoot.framework.registrysystem.Registry;
import me.nootnoot.framework.registrysystem.RegistryType;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

@Registry(type = RegistryType.LISTENER)
public class TeleportListener implements Listener {

	@EventHandler
	public void move(PlayerMoveEvent e){
		if(LuckyblockFactions.getInstance().getFactionManager().getPlayerTeleporting().contains(e.getPlayer())){
			LuckyblockFactions.getInstance().getFactionManager().getPlayerTeleporting().remove(e.getPlayer());
			e.getPlayer().sendMessage(Utils.c("&C&l(!)&c Teleport cancelled."));
		}
	}

	@EventHandler
	public void damage(EntityDamageByEntityEvent e){
		if(!(e.getEntity() instanceof Player p)) return;
		if(!(e.getDamager() instanceof Player target)) return;

		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(p);
		Faction t = LuckyblockFactions.getInstance().getFactionManager().getFaction(target);

		if(f == null) return;
		if(t != null) {
			if (f.getName().equalsIgnoreCase(t.getName())) return;
		}

		if(LuckyblockFactions.getInstance().getFactionManager().getPlayerTeleporting().contains(p)){
			LuckyblockFactions.getInstance().getFactionManager().getPlayerTeleporting().remove(p);
			p.sendMessage(Utils.c("&C&l(!)&c Teleport cancelled."));
		}
		if(LuckyblockFactions.getInstance().getFactionManager().getPlayerTeleporting().contains(target)){
			LuckyblockFactions.getInstance().getFactionManager().getPlayerTeleporting().remove(target);
			target.sendMessage(Utils.c("&C&l(!)&c Teleport cancelled."));
		}
	}
}
