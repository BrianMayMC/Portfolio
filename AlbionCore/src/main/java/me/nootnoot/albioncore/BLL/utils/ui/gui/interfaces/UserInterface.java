package me.nootnoot.albioncore.BLL.utils.ui.gui.interfaces;

import me.nootnoot.albioncore.BLL.utils.ui.gui.GUIManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class UserInterface {
	private Inventory inventory;
	private Player player = null;
	private GUIManager guiManager;
	protected void setInventory(Inventory inventory){
		this.inventory = inventory;
	}
	public final void setPlayer(Player player){
		this.player = player;
	}
	public final boolean close(){
		if(player.getOpenInventory() != null) {
			player.closeInventory();
		}
		return true;
	}
	public final Player getPlayer(){
		return player;
	}
	public abstract Inventory loadInventory(Player citizen);
	public abstract void chooseIndex(int slot);
	public final void refresh(){
		guiManager.openInterface(player, this);
	}
	public final void setManager(GUIManager guiManager){
		this.guiManager = guiManager;
	}
	public void onClose() {}
}
