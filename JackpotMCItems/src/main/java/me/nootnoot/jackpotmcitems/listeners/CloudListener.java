package me.nootnoot.jackpotmcitems.listeners;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.interfaces.Cooldown;
import me.nootnoot.jackpotmcitems.interfaces.CustomItem;
import me.nootnoot.jackpotmcitems.interfaces.GlobalCooldown;
import me.nootnoot.jackpotmcitems.utils.Utils;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.apache.logging.log4j.core.config.plugins.PluginValue;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class CloudListener extends Cooldown implements Listener {


	private final List<Player> gliders = new ArrayList<>();

	@EventHandler
	public void interact(PlayerInteractEvent e){
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
			if (item.getType() == Material.AIR) return;
			if(!(item.hasItemMeta())) return;
			ItemMeta imeta = item.getItemMeta();
			if(!(imeta.getPersistentDataContainer().has(new NamespacedKey(JackpotMCItems.getInstance(), "customItem")))) return;
			if(!(imeta.getPersistentDataContainer().get(new NamespacedKey(JackpotMCItems.getInstance(), "name"), PersistentDataType.STRING)).equalsIgnoreCase("cloud")) return;

			Player p = e.getPlayer();

			CustomItem customItem = JackpotMCItems.getInstance().getItemManager().getItem("cloud");
			customItem.createItem();

			if (playersOnCooldown.containsKey(p.getName())) {
				p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ON_COOLDOWN")
						.replace("%time%", String.valueOf(playersOnCooldown.get(p.getName()))).replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))));
				return;
			}

			if(GlobalCooldown.getGlobalCooldown().containsKey(p.getName())){
				p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ON_GLOBAL_COOLDOWN").replace("%time%", String.valueOf(GlobalCooldown.getGlobalCooldown().get(p.getName())))));
				return;
			}


			p.setVelocity(new Vector(0, 10, 0));
			p.setGliding(true);

			customItem.giveEffectsAndSounds(customItem, p);

			p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ACTIVATE_MESSAGE").replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))));

			playersOnCooldown.put(p.getName(), (long) customItem.getCooldown());

			gliders.add(p);
			new BukkitRunnable(){
				@Override
				public void run(){
					gliders.remove(p);
				}
			}.runTaskLater(JackpotMCItems.getInstance(), 300L);
			customItem.remove(item, p);

			runTask(p);


			/*
			Global Cooldown:
			 */
			GlobalCooldown.getGlobalCooldown().put(p.getName(), JackpotMCItems.getInstance().getConfig().getLong("global-cooldown"));
			GlobalCooldown.runGlobalTask(p);
		}
	}


	@EventHandler
	public void glide(EntityToggleGlideEvent e){
		if(!(e.getEntity() instanceof Player p)) return;
		if(gliders.contains(p)){
			e.setCancelled(true);
		}
	}
}
