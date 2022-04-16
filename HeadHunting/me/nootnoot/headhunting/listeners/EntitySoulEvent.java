package me.nootnoot.headhunting.listeners;

import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.entities.Booster;
import me.nootnoot.headhunting.entities.HHPlayer;
import me.nootnoot.headhunting.managers.BoosterManager;
import me.nootnoot.headhunting.managers.HHPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Locale;

public class EntitySoulEvent implements Listener {

	@EventHandler
	public void onMobKill(EntityDeathEvent e){
		if(e.getEntity().getKiller() == null) return;

		Player p = e.getEntity().getKiller();
		HHPlayer hhP = HHPlayerManager.getInstance().getHHPlayer(p);

		String entityName = e.getEntity().getName().toLowerCase();

		int amount = HeadHunting.getInstance().getConfig().getInt("souls." + entityName);

		if(BoosterManager.GetInstance().getActiveboosters().containsKey(p)){
			Booster booster = BoosterManager.GetInstance().getActiveboosters().get(p);
			int totalamount = (int) Math.round(amount * booster.getAmplifier());
			hhP.setSouls((hhP.getSouls() + totalamount));
		}else{
			hhP.setSouls(hhP.getSouls() + amount);
		}
	}
}
