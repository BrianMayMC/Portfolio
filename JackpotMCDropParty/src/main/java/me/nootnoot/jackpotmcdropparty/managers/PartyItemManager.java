package me.nootnoot.jackpotmcdropparty.managers;

import lombok.Getter;
import me.nootnoot.jackpotmcdropparty.JackpotMCDropParty;
import me.nootnoot.jackpotmcdropparty.entities.SimpleItem;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PartyItemManager {

	@Getter private final List<Location> placedDropParties;

	public PartyItemManager(){
		placedDropParties = new ArrayList<>();
	}


	public void AddItem(SimpleItem item){
		JackpotMCDropParty.getInstance().getMenuStorage().AddItem(item);
	}

	public void RemoveItem(SimpleItem item){
		JackpotMCDropParty.getInstance().getMenuStorage().RemoveItem(item);
	}


	public SimpleItem getRandomItem(){
		int number = ThreadLocalRandom.current().nextInt(JackpotMCDropParty.getInstance().getMenuStorage().getMenuItems().size());

		return JackpotMCDropParty.getInstance().getMenuStorage().getMenuItems().get(number);
	}
}
