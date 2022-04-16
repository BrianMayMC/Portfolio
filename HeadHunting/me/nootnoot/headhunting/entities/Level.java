package me.nootnoot.headhunting.entities;

import lombok.Getter;

import java.util.ArrayList;

public class Level {

	@Getter
	private final int requiredHeads;

	@Getter
	private final ArrayList<String> acceptedEntities;

	@Getter
	private final ArrayList<String> rewards;

	@Getter
	private final int upgradeCost;

	@Getter
	private final int level;


	public Level(int requiredHeads, ArrayList<String> acceptedEntities, ArrayList<String> rewards, int upgradeCost, int level){
		this.requiredHeads = requiredHeads;
		this.acceptedEntities = acceptedEntities;
		this.rewards = rewards;
		this.upgradeCost = upgradeCost;
		this.level = level;
	}
}
