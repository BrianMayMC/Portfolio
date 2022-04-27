package me.nootnoot.albioncore.BLL.playersystem.pouches;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.albioncore.AlbionCore;
import me.nootnoot.albioncore.BLL.artifactsystem.entities.Artifact;
import me.nootnoot.albioncore.BLL.artifactsystem.enums.ArtifactNames;
import me.nootnoot.albioncore.BLL.spellsystem.Spell;
import me.nootnoot.albioncore.BLL.spellsystem.SpellCategory;
import me.nootnoot.albioncore.BLL.spellsystem.SpellCreator;

import java.util.HashMap;

public class SpellPouch {

	@Getter@Setter
	private HashMap<Artifact, Spell> artifactSpell;
	@Getter@Setter
	private String cageSpell;

	public SpellPouch(){
		this.artifactSpell = new HashMap<>();
		CreateSpells();
	}

	public void CreateSpells(){
		Artifact artifact1 = AlbionCore.getInstance().getArtifactManager().GetArtifactByType(ArtifactNames.ARTIFACT_OF_THE_DAMNED);
		Artifact artifact2 = AlbionCore.getInstance().getArtifactManager().GetArtifactByType(ArtifactNames.ARTIFACT_OF_THE_DROWNED);
		Artifact artifact3 = AlbionCore.getInstance().getArtifactManager().GetArtifactByType(ArtifactNames.ARTIFACT_OF_JUSTICE);
		Artifact artifact4 = AlbionCore.getInstance().getArtifactManager().GetArtifactByType(ArtifactNames.ARTIFACT_OF_THE_LOST);
		for(int i = 0; i < 4; i ++){
			switch(i) {
				case 0:
					artifactSpell.put(artifact1, new Spell(SpellCreator.GetRandomSpell(), SpellCategory.DUNGEON));
				case 1:
					artifactSpell.put(artifact2, new Spell(SpellCreator.GetRandomSpell(), SpellCategory.FISHING));
				case 2:
					artifactSpell.put(artifact3, new Spell(SpellCreator.GetRandomSpell(), SpellCategory.FARMING));
				case 3:
					artifactSpell.put(artifact4, new Spell(SpellCreator.GetRandomSpell(), SpellCategory.CACTUS));
			}
		}
		cageSpell = SpellCreator.GetRandomSpell();
	}



}
