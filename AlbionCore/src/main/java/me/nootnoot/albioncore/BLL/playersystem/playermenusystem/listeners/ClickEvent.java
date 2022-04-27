package me.nootnoot.albioncore.BLL.playersystem.playermenusystem.listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.albioncore.BLL.playersystem.playermenusystem.menus.MainMenu;
import me.nootnoot.albioncore.BLL.utils.ui.gui.GUIManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickEvent implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent e){
		if(e.getItem() != null && e.getItem().getType() != Material.AIR){
			NBTItem item = new NBTItem(e.getItem());
			if(item.hasKey("playerMenu")){
				GUIManager.getInstance().openInterface(e.getPlayer(), new MainMenu());
			}
		}
	}
}
