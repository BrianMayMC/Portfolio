package me.nootnoot.jackpotmcitems.customitems;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.interfaces.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class StrengthRod extends CustomItem {
	public StrengthRod() {
		super("strength_rod");
	}

	@Override
	public void createRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(JackpotMCItems.getInstance(), "strength_rod"), getItem());
		recipe.shape("DDD", "DBD", "DDD");
		recipe.setIngredient('D', Material.DIAMOND);
		recipe.setIngredient('B', Material.BLAZE_ROD);
		Bukkit.addRecipe(recipe);
		setShapedRecipe(recipe);
	}
}
