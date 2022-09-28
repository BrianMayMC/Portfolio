package me.nootnoot.jackpotmcitems.customitems;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.interfaces.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class Cloud extends CustomItem {
	public Cloud(){
		super("cloud");
	}

	@Override
	public void createRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(JackpotMCItems.getInstance(), "cloud"), getItem());
		recipe.shape(" D ", "DFD", " D ");
		recipe.setIngredient('D', Material.DIAMOND);
		recipe.setIngredient('F', Material.FEATHER);
		Bukkit.addRecipe(recipe);
		setShapedRecipe(recipe);
	}
}
