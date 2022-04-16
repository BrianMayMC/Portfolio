package me.nootnoot.headhunting.managers;

import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.data.PlayerSQLLiteHandler;
import me.nootnoot.headhunting.entities.HHPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HHPlayerManager implements Listener {

	private static HHPlayerManager instance;

	private final Map<UUID, HHPlayer> playerRegistery;

	public HHPlayerManager(){
		this.playerRegistery = new HashMap<>();
		init();
		startTask();
	}

	public static HHPlayerManager getInstance(){
		if(instance != null)
			return instance;
		return instance = new HHPlayerManager();
	}


	public void init(){
		for(Player p : Bukkit.getOnlinePlayers()){
			this.loadOrRegister(p.getUniqueId());
		}
	}

	public void startTask(){
		//autosave
		Bukkit.getScheduler().scheduleSyncRepeatingTask(HeadHunting.getInstance(),
				() -> HHPlayerManager.getInstance().saveAll(), 60*20L, 60*20L);
	}

	public void loadOrRegister(UUID uuid){
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
		if(PlayerSQLLiteHandler.getInstance().isRegistered(uuid)){
			String registeredName = PlayerSQLLiteHandler.getInstance().getName(uuid);
			if(!registeredName.equals(offlinePlayer.getName()))
				PlayerSQLLiteHandler.getInstance().setName(uuid, offlinePlayer.getName());
		}else {
			PlayerSQLLiteHandler.getInstance().registerPlayer(uuid, offlinePlayer.getName());
		}
		this.playerRegistery.put(uuid, PlayerSQLLiteHandler.getInstance().loadPlayer(uuid));
	}

	private HHPlayer loadOrGet(UUID uuid){
		if(this.playerRegistery.containsKey(uuid)){
			return this.playerRegistery.get(uuid);
		}

		HHPlayer p = PlayerSQLLiteHandler.getInstance().loadPlayer(uuid);
		this.playerRegistery.put(uuid, p);
		return p;
	}

	public HHPlayer getHHPlayer(Player p){
		return this.playerRegistery.get(p.getUniqueId());
	}

	public HHPlayer getHHPlayer(String name){
		for(HHPlayer p : this.playerRegistery.values()){
			if(p.getName().equals(name)){
				return p;
			}
		}
		return null;
	}

	public HHPlayer getHHPlayer(UUID uuid){
		this.loadOrGet(uuid);
		return this.playerRegistery.get(uuid);
	}

	public Collection<HHPlayer> getPlayers(){
		return this.playerRegistery.values();
	}

	public void saveAll(){
		for(HHPlayer p : this.playerRegistery.values()){
			PlayerSQLLiteHandler.getInstance().savePlayer(p);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent e) {
		this.loadOrRegister(e.getPlayer().getUniqueId());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e) {
		HHPlayer p = this.getHHPlayer(e.getPlayer());
		PlayerSQLLiteHandler.getInstance().savePlayer(p);
		this.playerRegistery.remove(e.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onDisable(PluginDisableEvent e){
		this.saveAll();
		this.playerRegistery.clear();
	}
}
