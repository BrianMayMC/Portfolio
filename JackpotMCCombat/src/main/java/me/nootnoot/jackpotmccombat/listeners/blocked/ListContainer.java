package me.nootnoot.jackpotmccombat.listeners.blocked;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListContainer {

	@Getter private final Map<Player, Long> arrowPlayers = new HashMap<>();
	@Getter private final Map<Player, Long> totemPlayers = new HashMap<>();


}
