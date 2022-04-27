package me.nootnoot.albioncore.BLL.spellsystem;

import lombok.Getter;
import lombok.Setter;

public class Spell {
	@Getter@Setter
	private String spell;
	@Getter@Setter
	private SpellCategory spellCategory;

	public Spell(String spell, SpellCategory spellCategory){
		this.spell = spell;
		this.spellCategory = spellCategory;
	}
}
