package me.nootnoot.jackpotmcteams.entities;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeamPlayer {

	@Getter
	private final UUID playerUUID;
	private transient OfflinePlayer player;
	@Setter @Getter
	private String playerName;
	@Getter@Setter
	private TeamRoles role;

	public TeamPlayer(UUID playerUUID, TeamRoles role, String playerName) {
		this.playerUUID = playerUUID;
		this.player = Bukkit.getOfflinePlayer(playerUUID);
		this.playerName = playerName;
		this.role = role;
	}

	public Player getPlayer(){
		if(player == null){
			player = Bukkit.getOfflinePlayer(playerUUID);
		}
		if(player.isOnline()){
			return player.getPlayer();
		}
		return null;
	}

	public OfflinePlayer getOfflinePlayer(){
		if(player == null){
			return Bukkit.getOfflinePlayer(playerUUID);
		}
		return player;
	}

}
