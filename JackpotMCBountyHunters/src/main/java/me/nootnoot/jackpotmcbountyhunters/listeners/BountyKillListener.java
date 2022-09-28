package me.nootnoot.jackpotmcbountyhunters.listeners;

import me.nootnoot.framework.registrysystem.Registry;
import me.nootnoot.framework.registrysystem.RegistryType;
import me.nootnoot.framework.utils.CurrencyUtils;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmcbountyhunters.JackpotMCBountyHunters;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;

@Registry(type = RegistryType.LISTENER)
public class BountyKillListener implements Listener {

	private final FileConfiguration config = JackpotMCBountyHunters.getInstance().getConfig();

	@EventHandler
	public void onKill(PlayerDeathEvent e){
		Player p = e.getEntity();
		if(p.getKiller() == null) return;
		Player killer = p.getKiller();
		if(!(p.getPersistentDataContainer().has(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"), PersistentDataType.DOUBLE))) return;

		double amount = p.getPersistentDataContainer().get(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"), PersistentDataType.DOUBLE);
		p.getPersistentDataContainer().remove(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"));

		JackpotMCBountyHunters.getInstance().getEcon().depositPlayer(killer, amount);

		p.sendMessage(Utils.c(config.getString("bounty-lose").replace("%killer%", killer.getName()).replace("%prefix%", config.getString("prefix"))));
		killer.sendMessage(Utils.c(config.getString("bounty-claim").replace("%player%", p.getName()).replace("%amount%", CurrencyUtils.prettyMoney(amount, true, false))
				.replace("%prefix%", config.getString("prefix"))));
		Bukkit.broadcastMessage(Utils.c(config.getString("broadcast-bounty-claim").replace("%player%", p.getName())
				.replace("%killer%", killer.getName()).replace("%amount%", CurrencyUtils.prettyMoney(amount, true, false)).replace("%prefix%", config.getString("prefix"))));

		killer.playSound(killer.getLocation(), Sound.valueOf(config.getString("bounty-sound")), 1, 1);

	}
}
