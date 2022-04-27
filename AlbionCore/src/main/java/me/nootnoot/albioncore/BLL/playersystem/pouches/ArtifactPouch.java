package me.nootnoot.albioncore.BLL.playersystem.pouches;

import lombok.Getter;
import me.nootnoot.albioncore.BLL.artifactsystem.entities.Artifact;

import java.util.ArrayList;

public class ArtifactPouch {

	@Getter
	private final ArrayList<Artifact> claimedArtifacts;

	public ArtifactPouch(){
		claimedArtifacts = new ArrayList<>(); //later get from database
	}

}
