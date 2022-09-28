package me.nootnoot.luckyblockoreregen.entities;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;

public class RegenBlock {

	@Getter private final String id;
	@Getter private final Material material;
	@Getter private final Location location;

	public RegenBlock(String id, Material material, Location location) {
		this.id = id;
		this.material = material;
		this.location = location;
	}

}
