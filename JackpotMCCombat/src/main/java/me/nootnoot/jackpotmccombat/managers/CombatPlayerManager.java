package me.nootnoot.jackpotmccombat.managers;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.jackpotmccombat.entities.CombatPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CombatPlayerManager {

	@Getter
	private final List<CombatPlayer> combatPlayers;

	@Getter
	private final List<Player> playersOnGrace;

	@Getter
	private final HashMap<Player, ArrayList<Location>> storedBlockChanges;

	public CombatPlayerManager(){
		combatPlayers = new ArrayList<>();
		playersOnGrace = new ArrayList<>();
		storedBlockChanges = new HashMap<>();
	}

	public CombatPlayer getCombatPlayer(Player p){
		for(CombatPlayer player : combatPlayers){
			if(player.getP().equals(p)){
				return player;
			}
		}
		return null;
	}

}
