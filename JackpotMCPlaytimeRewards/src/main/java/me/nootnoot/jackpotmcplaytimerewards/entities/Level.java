package me.nootnoot.jackpotmcplaytimerewards.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor
@Getter
public class Level {
	private int level;
	private long requirementInHours;
	private int menuSlot;
	private List<String> rewards;
	private ItemStack claimed;
	private ItemStack locked;
}
