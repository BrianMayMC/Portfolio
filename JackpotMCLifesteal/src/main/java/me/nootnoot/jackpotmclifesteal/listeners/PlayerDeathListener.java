package me.nootnoot.jackpotmclifesteal.listeners;

import me.nootnoot.framework.registrysystem.Registry;
import me.nootnoot.framework.registrysystem.RegistryType;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmclifesteal.JackpotMCLifesteal;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Registry(type = RegistryType.LISTENER)
public class PlayerDeathListener implements Listener {

	@EventHandler
	public void death(PlayerDeathEvent e){
		Player p = e.getEntity();
		if(p.getKiller() == null) return;
		Player killer = p.getKiller();
		AttributeInstance killerAttribute = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		AttributeInstance pAttribute = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);


		/*
		change killer values
		 */

		if(killer.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE) / 2 + 1 <= JackpotMCLifesteal.getInstance().getConfig().getDouble("max-hearts")){
			killer.getPersistentDataContainer().set(new NamespacedKey(JackpotMCLifesteal.getInstance(),"hearts"), PersistentDataType.DOUBLE, killer.getPersistentDataContainer().get(
					new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE) + 2);
		}
		else if(killer.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE) / 2 + 0.5 <= JackpotMCLifesteal.getInstance().getConfig().getDouble("max-hearts")){
			killer.getPersistentDataContainer().set(new NamespacedKey(JackpotMCLifesteal.getInstance(),"hearts"), PersistentDataType.DOUBLE, killer.getPersistentDataContainer().get(
					new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE) + 1);
		}

		killerAttribute.setBaseValue(killer.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE));
		killer.setHealthScale(killerAttribute.getBaseValue());
		killer.setHealth(killer.getMaxHealth());

		/*
		Ban the player if their hearts would be 0 after removing.
		 */

		if(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - 1 == 0 || p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - 2 == 0 && !p.hasPermission("jmc.lifesteal.donor")){
			if(!(p.hasPermission("jmc.lifesteal.bypass"))) {
				if (p.hasPermission("jmc.lifesteal.30m")) {
					p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
					p.setHealthScale(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
					p.getPersistentDataContainer().set(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE, 20d);
					JackpotMCLifesteal.getInstance().getPlayersBanning().add(p);
					Bukkit.getBanList(BanList.Type.NAME).addBan(p.getName(), Utils.c("&cYou ran out of hearts!"), Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)), null);
					p.getInventory().clear();
					p.kickPlayer(Utils.c("&cYou ran out of hearts!"));
				} else {
					p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
					p.setHealthScale(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
					p.getPersistentDataContainer().set(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE, 20d);
					JackpotMCLifesteal.getInstance().getPlayersBanning().add(p);
					Bukkit.getBanList(BanList.Type.NAME).addBan(p.getName(), Utils.c("&cYou ran out of hearts!"), Date.from(Instant.now().plus(24, ChronoUnit.HOURS)), null);
					p.getInventory().clear();
					p.kickPlayer(Utils.c("&cYou ran out of hearts!"));
				}
			}
		}else{
			/*
		Change heart values for player
		 */

			if(p.hasPermission("jmc.lifesteal.donor")) {
				p.getPersistentDataContainer().set(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE, p.getPersistentDataContainer().get(
						new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE) - 1);
			}else{
				p.getPersistentDataContainer().set(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE, p.getPersistentDataContainer().get(
						new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE) - 2);
			}

			pAttribute.setBaseValue(p.getPersistentDataContainer().get(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE));

			p.setHealthScale(pAttribute.getBaseValue());
		}
	}
}
