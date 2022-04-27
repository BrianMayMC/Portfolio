package me.nootnoot.albioncore.BLL.playersystem.managers;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.albioncore.BLL.playersystem.entities.ABPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ABPlayerManager {
	@Getter@Setter
	private ArrayList<ABPlayer> players;

	public ABPlayerManager(){
		players = new ArrayList<>();
	}

	public void RegisterPlayer(Player p){
		players.add(new ABPlayer(p));
	}

	public ABPlayer GetPlayer(Player p){
		for(ABPlayer player : players){
			if(player.getP() == p){
				return player;
			}
		}
		return null;
	}
}
