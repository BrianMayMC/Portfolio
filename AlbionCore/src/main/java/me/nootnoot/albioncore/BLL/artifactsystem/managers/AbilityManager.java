package me.nootnoot.albioncore.BLL.artifactsystem.managers;

import me.nootnoot.albioncore.BLL.artifactsystem.entities.Ability;

import java.util.ArrayList;

public class AbilityManager {
	private final ArrayList<Ability> abilities;

	public AbilityManager(){
		this.abilities = new ArrayList<>();
	}

	public Ability GetAbility(String name){
		for(Ability ability : abilities){
			if(ability.getName().equalsIgnoreCase(name)){
				return ability;
			}
		}
		return null;
	}

	public void AddAbility(Ability ability){
		this.abilities.add(ability);
	}
	public void RemoveAbility(Ability ability){
		this.abilities.remove(ability);
	}
}
