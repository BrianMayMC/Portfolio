package me.nootnoot.albioncore.BLL.artifactsystem.entities;

import lombok.Getter;
import lombok.Setter;

public class Ability {
	//not sure about this yet
	@Getter@Setter
	private String name;
	@Getter@Setter
	private Booster booster;

	public Ability(String name, Booster booster){
		this.name = name;
		this.booster = booster;
	}
}
