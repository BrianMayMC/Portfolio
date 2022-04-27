package me.nootnoot.albioncore.BLL.artifactsystem.entities;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;

public class Booster {
	//not sure about this yet
	@Getter@Setter
	private String name;

	public Booster(String name){
		this.name = name;
	}
}
