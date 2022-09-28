package me.nootnoot.jackpotmccombat.listeners;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.entities.CombatPlayer;
import me.nootnoot.jackpotmccombat.utils.Utils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class PotionSplashListener implements Listener {

	@EventHandler
	public void onPotionSplash(PotionSplashEvent e){
		if(e.getAffectedEntities().isEmpty()) return;
		if(!(e.getEntityType().equals(EntityType.PLAYER))) return;
		if(!(Utils.isPvpEnabledAt(e.getPotion().getLocation()))) return;

		List<String> badPotionEffects = List.of("BLINDNESS", "POISON", "WEAKNESS", "HARM", "SLOW_FALLING");

		boolean contains = false;
		for(PotionEffect potionEffect : e.getPotion().getEffects()){
			if(badPotionEffects.contains(potionEffect.getType().getName())){
				contains = true;
			}
		}
		if(!contains){
			return;
		}
		for(LivingEntity livingEntity : e.getAffectedEntities()){
			if(livingEntity instanceof Player p) {
				if (Utils.isPvpEnabledAt(p.getLocation())) {
					CombatPlayer combatP = JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(p);
					if (combatP == null) {
						JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayers().add(new CombatPlayer(p, Utils.cooldown));
					} else {
						combatP.setCooldown(Utils.cooldown);
					}
				}
			}
		}
	}
}
