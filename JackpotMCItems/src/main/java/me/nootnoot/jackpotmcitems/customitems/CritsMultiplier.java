package me.nootnoot.jackpotmcitems.customitems;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.interfaces.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class CritsMultiplier extends CustomItem {
	public CritsMultiplier() {
		super("crits_multiplier");
	}

	@Override
	public void createRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(JackpotMCItems.getInstance(), "crits_multiplier"), getItem());
		recipe.shape("DDD", "DBD", "DDD");
		recipe.setIngredient('D', Material.DIAMOND);
		recipe.setIngredient('B', Material.BLAZE_POWDER);
		Bukkit.addRecipe(recipe);
		setShapedRecipe(recipe);
	}
}
