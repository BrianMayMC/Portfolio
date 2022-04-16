package me.nootnoot.headhunting.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class HHPlayer {
	@Getter
	private final UUID uuid;
	@Getter
	private final String name;
	@Getter @Setter
	private int level;
	@Getter @Setter
	private int soldHeads;
	@Getter @Setter
	private int souls;

	public HHPlayer(UUID uuid, String name, int level, int soldHeads, int souls){
		this.uuid = uuid;
		this.name = name;
		this.level = level;
		this.soldHeads = soldHeads;
		this.souls = souls;
	}
}
