package me.nootnoot.jackpotmcitems.listeners;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.entities.*;
import me.nootnoot.jackpotmcitems.interfaces.Cooldown;
import me.nootnoot.jackpotmcitems.interfaces.CustomItem;
import me.nootnoot.jackpotmcitems.interfaces.GlobalCooldown;
import me.nootnoot.jackpotmcitems.utils.Utils;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class ZapStickListener extends Cooldown implements Listener {

	private final Map<String, HitAmountObject> hitAmounts = new HashMap<>();

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		if(!(e.getEntity() instanceof Player target && e.getDamager() instanceof Player p)) return;

		ItemStack item = p.getInventory().getItemInMainHand();
		if (item.getType() == Material.AIR) return;
		if(!(item.hasItemMeta())) return;
		ItemMeta imeta = item.getItemMeta();
		if(!(imeta.getPersistentDataContainer().has(new NamespacedKey(JackpotMCItems.getInstance(), "customItem")))) return;
		if(!(imeta.getPersistentDataContainer().get(new NamespacedKey(JackpotMCItems.getInstance(), "name"), PersistentDataType.STRING)).equalsIgnoreCase("zap_stick")) return;

		CustomItem customItem = JackpotMCItems.getInstance().getItemManager().getItem("zap_stick");

		/*
		checking if player is on cooldown
		 */
		if (playersOnCooldown.containsKey(p.getName())) {
			p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ON_COOLDOWN")
							.replace("%time%", String.valueOf(playersOnCooldown.get(p.getName()))).replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))));
			return;
		}

		if(GlobalCooldown.getGlobalCooldown().containsKey(p.getName())){
			p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ON_GLOBAL_COOLDOWN")
					.replace("%time%", String.valueOf(GlobalCooldown.getGlobalCooldown().get(p.getName())))));
			return;
		}


		/*
		checking if same target is hit 3 times
		 */
		HitAmountObject hitAmountObject;
		if(hitAmounts.containsKey(p.getName())){
			if(hitAmounts.get(p.getName()).getTarget() == target){
				hitAmountObject = hitAmounts.get(p.getName());
			}
			else{
				hitAmountObject = new HitAmountObject(target);
				hitAmounts.put(p.getName(), hitAmountObject);
			}
		}else{
			hitAmountObject = new HitAmountObject(target);
			hitAmounts.put(p.getName(), hitAmountObject);
		}
		hitAmountObject.setHits(hitAmountObject.getHits() + 1);
		if(hitAmountObject.getHits() == customItem.getHitAmounts()) {

			customItem.giveEffectsAndSounds(customItem, p, target);

			p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ACTIVATE_MESSAGE")
					.replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))));

			playersOnCooldown.put(p.getName(), (long)customItem.getCooldown());
			GlobalCooldown.getGlobalCooldown().put(p.getName(), JackpotMCItems.getInstance().getConfig().getLong("global-cooldown"));
			GlobalCooldown.runGlobalTask(p);

			hitAmounts.remove(p.getName());

			customItem.remove(item, p);
		}

		/*
		Cooldown:
		*/
		runTask(p);
	}
}
