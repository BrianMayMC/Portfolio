package me.nootnoot.jackpotmcitems.customitems;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.interfaces.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class Levitator extends CustomItem {
	public Levitator() {
		super("levitator");
	}

	@Override
	public void createRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(JackpotMCItems.getInstance(), "levitator"), getItem());
		recipe.shape(" F ", "FTF", " F ");
		recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
		recipe.setIngredient('F', Material.FEATHER);
		Bukkit.addRecipe(recipe);
		setShapedRecipe(recipe);
	}
}
