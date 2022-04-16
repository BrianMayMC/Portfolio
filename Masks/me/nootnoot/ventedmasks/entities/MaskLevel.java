package me.nootnoot.ventedmasks.entities;

import lombok.Getter;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class MaskLevel {

	@Getter
	private final List<PotionEffect> effects;

	public MaskLevel(List<PotionEffect> effects) {
		this.effects = effects;
	}



}
