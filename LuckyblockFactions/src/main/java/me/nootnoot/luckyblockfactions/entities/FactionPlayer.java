package me.nootnoot.luckyblockfactions.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class FactionPlayer {
	private final UUID uuid;
	private FactionRole role;
	private String playerName;

	public FactionPlayer(UUID uuid, FactionRole role, String playerName) {
		this.uuid = uuid;
		this.role = role;
		this.playerName = playerName;
	}
}
