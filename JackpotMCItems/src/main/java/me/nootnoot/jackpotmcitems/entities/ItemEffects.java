package me.nootnoot.jackpotmcitems.entities;

import java.util.ArrayList;

public record ItemEffects(ArrayList<SourceEffects> sourceEffects,
						  ArrayList<TargetEffects> targetEffects,
						  ArrayList<SourceSounds> sourceSounds,
						  ArrayList<TargetSounds> targetSounds,
						  ArrayList<Particles> particles) {

}
