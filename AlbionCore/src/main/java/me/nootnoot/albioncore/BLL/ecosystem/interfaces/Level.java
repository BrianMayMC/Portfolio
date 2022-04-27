package me.nootnoot.albioncore.BLL.ecosystem.interfaces;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;

public abstract class Level {

	@Getter@Setter
	private int level;
	@Getter@Setter
	private int requiredAmount;
	@Getter@Setter
	private String spellSyllable;
	@Getter@Setter
	private double coins;
	@Getter@Setter
	private ArrayList<String> loreMessages;
	@Getter@Setter
	private ArrayList<Material> resources;

}
