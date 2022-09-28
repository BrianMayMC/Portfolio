package me.nootnoot.jackpotmcfastcrystal;

import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class JackpotMCFastCrystal extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		// Plugin startup logic
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void event(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player player && event.getEntity() instanceof EnderCrystal) {
			((CraftPlayer) player).getHandle().b.a(new PacketPlayOutEntityDestroy(event.getEntity().getEntityId()));
		}
	}
}
