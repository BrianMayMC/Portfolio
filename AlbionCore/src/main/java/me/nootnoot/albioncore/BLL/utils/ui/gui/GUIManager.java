package me.nootnoot.albioncore.BLL.utils.ui.gui;

import me.nootnoot.albioncore.AlbionCore;
import me.nootnoot.albioncore.BLL.utils.ui.gui.interfaces.NewUserInterface;
import me.nootnoot.albioncore.BLL.utils.ui.gui.interfaces.UserInterface;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GUIManager {
	private HashMap<Player, UserInterface> playerUserInterfaceHashMap;
	private static GUIManager instance;
	private GUIManager(){
		playerUserInterfaceHashMap = new HashMap<>();
		Bukkit.getServer().getPluginManager().registerEvents(new GUIHandler(this), AlbionCore.getInstance());
	}
	public  boolean openInterface(Player player, UserInterface userInterface){
		if(inInterface(player)){
			removeFromMap(player);
		}
		userInterface.setPlayer(player);
		userInterface.setManager(this);
		if(userInterface instanceof NewUserInterface){
			((NewUserInterface) userInterface).define();
		}
		player.openInventory(userInterface.loadInventory(player));
		playerUserInterfaceHashMap.put(player, userInterface);
		return false;
	}
	public  boolean closeInterface(Player player){
		if(inInterface(player)){
			playerUserInterfaceHashMap.get(player).onClose();
			playerUserInterfaceHashMap.get(player).close();
			playerUserInterfaceHashMap.remove(player);
			player.closeInventory();
		}
		return false;
	}
	public  boolean removeFromMap(Player player){
		if(playerUserInterfaceHashMap.containsKey(player)) {
			playerUserInterfaceHashMap.get(player).onClose();
			playerUserInterfaceHashMap.remove(player);
			return true;
		}
		return false;
	}
	public  boolean inInterface(Player player){
		return playerUserInterfaceHashMap.containsKey(player);
	}
	public  void selectSlot(Player player, int slot){
		playerUserInterfaceHashMap.get(player).chooseIndex(slot);
	}
	public  void closeInterfaces(){
		for(Player player : playerUserInterfaceHashMap.keySet()){
			playerUserInterfaceHashMap.get(player).close();
		}
	}
	public static GUIManager getInstance(){
		if(instance == null) {
			instance = new GUIManager();
		}
		return instance;
	}
}