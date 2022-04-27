package me.nootnoot.albioncore.BLL.artifactsystem.entities;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;

public class ArtifactObject {
	@Getter
	private final Material material;
	@Getter
	private final Material previousMaterial;
	@Getter
	private final Location loc;

	public ArtifactObject(Material material, Material previousMaterial, Location loc){
		this.previousMaterial = previousMaterial;
		this.material = material;
		this.loc = loc;
	}
}
