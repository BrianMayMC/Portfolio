package me.nootnoot.albioncore.BLL.ecosystem.interfaces;

import lombok.Getter;
import lombok.Setter;

public abstract class Bank<T> {
	@Getter@Setter
	private int amount;
	@Getter@Setter
	private T currentLevel;
}
