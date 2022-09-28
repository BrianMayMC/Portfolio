package me.nootnoot.jackpotmcitems.listeners;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class CritsMultiplierListener extends Cooldown implements Listener {

	private final ArrayList<String> activePlayers = new ArrayList<>();
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			ItemStack item = e.getPlayer().getInventory().getItemInMainHand();

			if (item.getType() == Material.AIR) return;
			if(!(item.hasItemMeta())) return;
			ItemMeta imeta = item.getItemMeta();
			if(!(imeta.getPersistentDataContainer().has(new NamespacedKey(JackpotMCItems.getInstance(), "customItem")))) return;
			if(!(imeta.getPersistentDataContainer().get(new NamespacedKey(JackpotMCItems.getInstance(), "name"), PersistentDataType.STRING)).equalsIgnoreCase("crits_multiplier")) return;


			Player p = e.getPlayer();
			CustomItem customItem = JackpotMCItems.getInstance().getItemManager().getItem("crits_multiplier");

			if (playersOnCooldown.containsKey(p.getName())) {
				p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ON_COOLDOWN")
								.replace("%time%", String.valueOf(playersOnCooldown.get(p.getName()))).replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))));
				return;
			}

			if(GlobalCooldown.getGlobalCooldown().containsKey(p.getName())){
				p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ON_GLOBAL_COOLDOWN").
								replace("%time%", String.valueOf(GlobalCooldown.getGlobalCooldown().get(p.getName())))));
				return;
			}

			p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ACTIVATE_MESSAGE").replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))));
			activePlayers.add(p.getName());

			new BukkitRunnable(){
				@Override
				public void run(){
					activePlayers.remove(p.getName());
					p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("EXPIRE_MESSAGE").replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))));
				}
			}.runTaskLaterAsynchronously(JackpotMCItems.getInstance(), (JackpotMCItems.getInstance().getConfig().getInt("items.crits_multiplier.duration") * 20L));

			customItem.giveEffectsAndSounds(customItem, p);

			playersOnCooldown.put(p.getName(), (long) customItem.getCooldown());

			customItem.remove(item, p);

			runTask(p);

			GlobalCooldown.getGlobalCooldown().put(p.getName(), JackpotMCItems.getInstance().getConfig().getLong("global-cooldown"));
			GlobalCooldown.runGlobalTask(p);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player p){
			if(!activePlayers.contains(p.getName())) return;

			if(!e.isCritical()) return;

			e.setDamage(e.getDamage() * JackpotMCItems.getInstance().getConfig().getDouble("items.crits_multiplier.multiplier"));
		}
	}


}
