package me.nootnoot.albioncore.BLL.artifactsystem.managers;

import me.nootnoot.albioncore.BLL.artifactsystem.entities.Booster;

import java.util.ArrayList;

public class BoosterManager {
	private final ArrayList<Booster> boosters;

	public BoosterManager(){
		boosters = new ArrayList<>();
	}

	public Booster GetBooster(String name){
		for(Booster booster : boosters){
			if(booster.getName().equalsIgnoreCase(name)){
				return booster;
			}
		}
		return null;
	}

	public void AddBooster(Booster booster){
		this.boosters.add(booster);
	}
	public void RemoveBooster(Booster booster){
		this.boosters.remove(booster);
	}
}
