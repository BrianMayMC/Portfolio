package me.nootnoot.albioncore.BLL.playersystem.entities;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.albioncore.BLL.playersystem.pouches.ArtifactPouch;
import me.nootnoot.albioncore.BLL.playersystem.pouches.SpellPouch;
import org.bukkit.entity.Player;

public class ABPlayer {

	@Getter@Setter
	private Player p;
	@Getter@Setter
	private SpellPouch spellPouch;
	@Getter@Setter
	private ArtifactPouch artifactPouch;

	public ABPlayer(Player p){
		this.spellPouch = new SpellPouch();
		this.artifactPouch = new ArtifactPouch();
		this.p = p;
	}
}
