package me.nootnoot.jackpotmcitems.listeners;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.interfaces.Cooldown;
import me.nootnoot.jackpotmcitems.interfaces.CustomItem;
import me.nootnoot.jackpotmcitems.interfaces.GlobalCooldown;
import me.nootnoot.jackpotmcitems.utils.Utils;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class DeflectorListener extends Cooldown implements Listener {

	private final ArrayList<String> activatedPlayers = new ArrayList<>();
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			ItemStack item = e.getPlayer().getInventory().getItemInMainHand();

			if (item.getType() == Material.AIR) return;
			if(!(item.hasItemMeta())) return;
			ItemMeta imeta = item.getItemMeta();
			if(!(imeta.getPersistentDataContainer().has(new NamespacedKey(JackpotMCItems.getInstance(), "customItem")))) return;
			if(!(imeta.getPersistentDataContainer().get(new NamespacedKey(JackpotMCItems.getInstance(), "name"), PersistentDataType.STRING)).equalsIgnoreCase("deflector")) return;


			Player p = e.getPlayer();

			CustomItem customItem = JackpotMCItems.getInstance().getItemManager().getItem("deflector");

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



			customItem.giveEffectsAndSounds(customItem, p);

			p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("ACTIVATE_MESSAGE").replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))));


			activatedPlayers.add(p.getName());

			runTask(p);


			playersOnCooldown.put(p.getName(), (long) customItem.getCooldown());

			customItem.remove(item, p);


			runExpireTask(p, customItem);

			GlobalCooldown.getGlobalCooldown().put(p.getName(), JackpotMCItems.getInstance().getConfig().getLong("global-cooldown"));
			GlobalCooldown.runGlobalTask(p);
		}
	}

	public void runExpireTask(Player p, CustomItem customItem){
		new BukkitRunnable(){
			@Override
			public void run(){
				activatedPlayers.remove(p.getName());
				p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("EXPIRE_MESSAGE").replace("%item%", PlainTextComponentSerializer.plainText().serialize(customItem.getItemName()))));
			}
		}.runTaskLater(JackpotMCItems.getInstance(), JackpotMCItems.getInstance().getConfig().getLong("items.deflector.duration") * 20L);
	}

	@EventHandler
	public void OnArrow(ProjectileHitEvent e){
		if(e.getHitEntity() == null) return;
		if(!(e.getHitEntity() instanceof Player target)) return;

		if(!(e.getEntity() instanceof Arrow arrow)) return;

		if(!(activatedPlayers.contains(target.getName()))) return;


		List<String> badPotionEffects = List.of(
				"BLINDNESS",
				"HARM",
				"POISON",
				"SLOW",
				"SLOW_FALLING",
				"WEAKNESS",
				"WITHER"
		);


		if(badPotionEffects.contains(arrow.getBasePotionData().getType().getEffectType().getName())){
			PotionData potionData = new PotionData(PotionType.WATER);
			arrow.setBasePotionData(potionData);
		}
	}
}
