package me.nootnoot.luckyblockbackpacks.listeners;

import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.entities.CombatPlayer;
import me.nootnoot.jackpotmccombat.managers.CombatPlayerManager;
import me.nootnoot.luckyblockbackpacks.LuckyblockBackpacks;
import me.nootnoot.luckyblockbackpacks.utils.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class BackpackClick implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent e){
		if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(e.getItem() == null) return;
		if(e.getItem().getType() == Material.AIR) return;
		if(!(e.getItem().hasItemMeta())) return;
		if(e.getItem().getItemMeta().getPersistentDataContainer().has(Utils.key, PersistentDataType.STRING)){
			e.setCancelled(true);
			if(LuckyblockBackpacks.getInstance().getBackpackManager().getPlayersInMenu().containsKey(e.getPlayer())) return;
			new BukkitRunnable(){
				@Override
				public void run(){
					e.getPlayer().closeInventory();
					if(LuckyblockBackpacks.getInstance().isCombatAThing()){
						if(JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(e.getPlayer()) != null){
							return;
						}
					}
					LuckyblockBackpacks.getInstance().getBackpackManager().openBackpack(e.getPlayer(), e.getPlayer());
				}
			}.runTaskLater(LuckyblockBackpacks.getInstance(), 2L);
		}
	}

	@EventHandler
	public void place(BlockPlaceEvent e){
		if(!e.getItemInHand().hasItemMeta()) return;
		ItemMeta meta = e.getItemInHand().getItemMeta();
		if(meta.getPersistentDataContainer().has(Utils.key, PersistentDataType.STRING)){
			e.setCancelled(true);
		}
	}
}
