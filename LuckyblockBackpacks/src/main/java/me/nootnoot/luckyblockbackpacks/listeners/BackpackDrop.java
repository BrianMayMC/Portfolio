package me.nootnoot.luckyblockbackpacks.listeners;

import me.nootnoot.luckyblockbackpacks.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.persistence.PersistentDataType;

public class BackpackDrop implements Listener {

	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		if(!(e.getItemDrop().getItemStack().hasItemMeta())) return;
		if(e.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().has(Utils.key, PersistentDataType.STRING)){
			e.getItemDrop().remove();
			e.getPlayer().sendMessage(Utils.c("&a&l(!)&a Successfully removed your backpack from your inventory. Do &l/backpack give&a to get it back!"));
		}
	}
}
