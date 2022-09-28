package me.nootnoot.framework.menusystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MenuListener implements Listener {

	private final MenuManager menuManager;

	public MenuListener(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

	@EventHandler
	public void DragEvent(InventoryDragEvent e){
		if(menuManager.isInInterface((Player)e.getWhoClicked())){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void select(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player p)) return;
		if(!(menuManager.isInInterface(p))) return;
		menuManager.selectSlot(p, e.getRawSlot(), e);
		if (!(menuManager.isInInterface((Player) e.getWhoClicked()))) return;
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void exit(InventoryCloseEvent e){
		if(menuManager.isInInterface((Player)e.getPlayer())){
			MenuInterface menu = menuManager.getCurrentMenu((Player)e.getPlayer());
			if(menu.getFallBackMenu() != null){
				menuManager.openInterface((Player)e.getPlayer(), menu.getFallBackMenu());
			}else {
				menuManager.RemoveFromMap((Player) e.getPlayer());
			}
		}
	}

	@EventHandler
	public void OnQuit(PlayerQuitEvent e){
		if(menuManager.isInInterface(e.getPlayer())){
			menuManager.RemoveFromMap(e.getPlayer());
		}
	}
}
