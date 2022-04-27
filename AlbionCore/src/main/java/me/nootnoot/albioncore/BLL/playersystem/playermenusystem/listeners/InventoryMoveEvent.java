package me.nootnoot.albioncore.BLL.playersystem.playermenusystem.listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.albioncore.BLL.playersystem.playermenusystem.menus.MainMenu;
import me.nootnoot.albioncore.BLL.utils.ui.gui.GUIManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class InventoryMoveEvent implements Listener {

	@EventHandler
	public void onMenuMove(InventoryClickEvent e){
		if(e.getCurrentItem() == null)
			return;
		if(e.getCurrentItem().getType() == Material.AIR)
			return;

		NBTItem item = new NBTItem(e.getCurrentItem());
		if(item.hasKey("playerMenu")){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		if(e.getItemDrop().getItemStack().getType() != Material.AIR){
			NBTItem item = new NBTItem(e.getItemDrop().getItemStack());
			if(item.hasKey("playerMenu")){
				e.setCancelled(true);
				GUIManager.getInstance().openInterface(e.getPlayer(), new MainMenu());
			}
		}
	}
}
