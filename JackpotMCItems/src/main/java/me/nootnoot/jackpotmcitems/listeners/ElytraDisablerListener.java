package me.nootnoot.jackpotmcitems.listeners;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.entities.HitAmountObject;
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
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ElytraDisablerListener extends Cooldown implements Listener {

	private final Map<String, HitAmountObject> hitAmounts = new HashMap<>();

	private final ArrayList<String> playersOnElytraStop = new ArrayList<>();


	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		if(!(e.getEntity() instanceof Player target && e.getDamager() instanceof Player p)) return;

		ItemStack item = p.getInventory().getItemInMainHand();
		if (item.getType() == Material.AIR) return;
		if(!(item.hasItemMeta())) return;
		ItemMeta imeta = item.getItemMeta();
		if(!(imeta.getPersistentDataContainer().has(new NamespacedKey(JackpotMCItems.getInstance(), "customItem")))) return;
		if(!(imeta.getPersistentDataContainer().get(new NamespacedKey(JackpotMCItems.getInstance(), "name"), PersistentDataType.STRING)).equalsIgnoreCase("elytra_disabler")) return;


		CustomItem customItem = JackpotMCItems.getInstance().getItemManager().getItem("elytra_disabler");

		/*
		checking if player is on cooldown
		 */
		if (playersOnCooldown.containsKey(p.getName())) {
			p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ON_COOLDOWN")
							.replace("%time%", String.valueOf(playersOnCooldown.get(p.getName()))).replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))));
			return;
		}

		if(GlobalCooldown.getGlobalCooldown().containsKey(p.getName())){
			p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ON_GLOBAL_COOLDOWN").replace("%time%", String.valueOf(GlobalCooldown.getGlobalCooldown().get(p.getName())))));
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

			playersOnCooldown.put(p.getName(), (long)customItem.getCooldown());
			GlobalCooldown.getGlobalCooldown().put(p.getName(), JackpotMCItems.getInstance().getConfig().getLong("global-cooldown"));
			GlobalCooldown.runGlobalTask(p);

			hitAmounts.remove(p.getName());

			p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ACTIVATE_MESSAGE").replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))));
			target.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ELYTRA_DISABLE_ACTIVATED")));
			playersOnElytraStop.add(target.getName());


			new BukkitRunnable(){
				@Override
				public void run(){
					playersOnElytraStop.remove(target.getName());
					p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("EXPIRE_MESSAGE").replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))));
					target.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("EXPIRE_MESSAGE").replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))));
				}
			}.runTaskLater(JackpotMCItems.getInstance(), JackpotMCItems.getInstance().getConfig().getLong("items.elytra_disabler.duration") * 20L);

			customItem.remove(item, p);
		}

		/*
		Cooldown:
		*/
		runTask(p);


	}

	@EventHandler
	public void onElytra(EntityToggleGlideEvent e){
		if(!(e.getEntity() instanceof Player p)) return;
		if(playersOnElytraStop.contains(p.getName())){
			e.setCancelled(true);
			p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ELYTRA_DISABLED_MESSAGE")));
		}
	}
}
