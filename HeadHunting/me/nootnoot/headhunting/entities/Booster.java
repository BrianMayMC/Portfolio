package me.nootnoot.headhunting.entities;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public class Booster {

	@Getter
	private final int duration;
	@Getter
	private final double amplifier;
	@Getter
	private final String configname;
	@Getter
	private final String name;
	@Getter
	private final ItemStack is;
	@Getter
	private final String type;


	public Booster(String configname, String name, int duration, double amplifier, ItemStack is, String type){
		this.name = name;
		this.configname = configname;
		this.duration = duration;
		this.amplifier = amplifier;
		this.is = is;
		this.type = type;
	}
}
