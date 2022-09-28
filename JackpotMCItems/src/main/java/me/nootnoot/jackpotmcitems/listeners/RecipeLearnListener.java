package me.nootnoot.jackpotmcitems.listeners;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.interfaces.CustomItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RecipeLearnListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		if(JackpotMCItems.getInstance().isUseRecipes()) {
			for (CustomItem item : JackpotMCItems.getInstance().getItemManager().getCustomItems()) {
				e.getPlayer().discoverRecipe(item.getShapedRecipe().getKey());
			}
		}
	}
}
