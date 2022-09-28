package me.nootnoot.jackpotmcitems.customitems;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.interfaces.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class Deflector extends CustomItem {

	public Deflector() {
		super("deflector");
	}

	@Override
	public void createRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(JackpotMCItems.getInstance(), "deflector"), getItem());
		recipe.shape("DDD", "DAD", "DDD");
		recipe.setIngredient('A', Material.SPECTRAL_ARROW);
		recipe.setIngredient('D', Material.DIAMOND);
		Bukkit.addRecipe(recipe);
		setShapedRecipe(recipe);
	}
}
