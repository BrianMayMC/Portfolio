package me.nootnoot.albioncore.BLL.artifactsystem.managers;

import lombok.Getter;
import me.nootnoot.albioncore.AlbionCore;
import me.nootnoot.albioncore.BLL.artifactsystem.entities.Artifact;
import me.nootnoot.albioncore.BLL.artifactsystem.entities.ArtifactObject;
import me.nootnoot.albioncore.BLL.artifactsystem.enums.ArtifactNames;
import me.nootnoot.albioncore.BLL.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class ArtifactManager {

	@Getter
	private final ArrayList<Artifact> artifacts;


	@Getter
	private final HashSet<Artifact> spawnedArtifacts;

	public ArtifactManager(){
		artifacts = new ArrayList<>();
		spawnedArtifacts = new HashSet<>();
		FillArtifacts();
	}

	public Artifact GetArtifact(String name){
		for(Artifact artifact : artifacts){
			if(artifact.getName().equalsIgnoreCase(name)){
				return artifact;
			}
		}
		return null;
	}

	public Artifact GetArtifactByType(ArtifactNames artifactName){
		for(Artifact artifact : artifacts){
			if(artifact.getArtifactName() == artifactName){
				return artifact;
			}
		}
		return null;
	}

	public void SpawnArtifact(Artifact artifact, Player p){
		Location loc = new Location(p.getWorld(), (int) p.getLocation().getX(), (int) p.getLocation().getY() + 10, (int) p.getLocation().getZ());

		Vector vector = new Vector();
		double random = ThreadLocalRandom.current().nextDouble(0, 1);
		vector.setX(random);
		vector.setZ(random);

		for(ArtifactObject object : artifact.CreateArtifactBlock(loc)){

			FallingBlock fallingBlock = Bukkit.getWorld(p.getWorld().getUID()).spawnFallingBlock(object.getLoc(), object.getMaterial().createBlockData());

			fallingBlock.setVelocity(vector);

			if(object.getMaterial() == Material.BEACON){
				fallingBlock.setMetadata("artifactType", new FixedMetadataValue(AlbionCore.getInstance(), artifact.getArtifactName()));
			}
			spawnedArtifacts.add(artifact);

			fallingBlock.setDropItem(false);
			fallingBlock.setHurtEntities(false);
		}
	}

	public void RemoveArtifact(Artifact artifact){
		for(ArtifactObject artifactObject : artifact.getBlocksPlaced()){
			artifactObject.getLoc().getBlock().setType(artifactObject.getPreviousMaterial());
			artifactObject.getLoc().getBlock().removeMetadata("artifactPlatform", AlbionCore.getInstance());
		}
		artifact.getCoreLocation().getBlock().setType(Material.AIR);
		artifact.getCoreLocation().getBlock().removeMetadata("artifactType", AlbionCore.getInstance());
		spawnedArtifacts.remove(artifact);
	}

	public void RemoveAllArtifacts(){
		for(Artifact artifact : getSpawnedArtifacts()){
			for(ArtifactObject artifactObject : artifact.getBlocksPlaced()){
				artifactObject.getLoc().getBlock().setType(artifactObject.getMaterial());
				artifactObject.getLoc().getBlock().removeMetadata("artifactPlatform", AlbionCore.getInstance());
			}
		}
		spawnedArtifacts.clear();
	}

	private void FillArtifacts(){
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.DARK_PURPLE + "Dummy lore");
		artifacts.add(new Artifact(
				Utils.c(AlbionCore.getInstance().getItemsFile().getConfig().getString("ARTIFACT_OF_THE_DROWNED.name")),
				lore,
				null,
				"SpellSyllable",
				ArtifactNames.ARTIFACT_OF_THE_DROWNED
		));
		artifacts.add(new Artifact(
				Utils.c(AlbionCore.getInstance().getItemsFile().getConfig().getString("ARTIFACT_OF_JUSTICE.name")),
				lore,
				null,
				"SpellSyllable2",
				ArtifactNames.ARTIFACT_OF_JUSTICE
		));
		artifacts.add(new Artifact(
				Utils.c(AlbionCore.getInstance().getItemsFile().getConfig().getString("ARTIFACT_OF_THE_DAMNED.name")),
				lore,
				null,
				"SpellSyllable3",
				ArtifactNames.ARTIFACT_OF_THE_DAMNED
		));
		artifacts.add(new Artifact(
				Utils.c(AlbionCore.getInstance().getItemsFile().getConfig().getString("ARTIFACT_OF_THE_LOST.name")),
				lore,
				null,
				"SpellSyllable4",
				ArtifactNames.ARTIFACT_OF_THE_LOST
		));
	}
}
