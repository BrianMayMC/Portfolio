package me.nootnoot.luckyblockchestrefill.entities;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.Chest;

public class RefillChest {

	@Getter
	private final SimpleLocation location;
	@Getter
	private final String type;
	@Getter @Setter
	private transient Chest chest;

	public RefillChest(SimpleLocation location, String type) {
		this.location = location;
		this.type = type;
	}
}
