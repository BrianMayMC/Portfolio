package me.nootnoot.albioncore.BLL.artifactsystem.entities;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.albioncore.BLL.artifactsystem.enums.ArtifactNames;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Artifact {
	@Getter@Setter
	private String name;
	@Getter@Setter
	private ArrayList<Ability> abilities;
	@Getter@Setter
	private Location coreLocation;
	@Getter@Setter
	private HashSet<ArtifactObject> blocksPlaced;
	@Getter@Setter
	private String spellSyllable;
	@Getter@Setter
	private ArtifactNames artifactName;
	@Getter@Setter
	private ArrayList<String> lore;
	@Getter@Setter
	private Player p;
	@Getter@Setter
	private String status = "Locked";

	public Artifact(String name, ArrayList<String> lore, ArrayList<Ability> abilities, String spellSyllable, ArtifactNames artifactName){
		this.name = name;
		this.lore = lore;
		this.abilities = abilities;
		this.spellSyllable = spellSyllable;
		this.artifactName = artifactName;
	}

	public HashSet<ArtifactObject> CreateArtifactBlock(Location coreLocation){
		HashSet<ArtifactObject> blocks = new HashSet<>();
		blocks.add(new ArtifactObject(Material.BEACON, null, coreLocation));
		return blocks;
	}
}
