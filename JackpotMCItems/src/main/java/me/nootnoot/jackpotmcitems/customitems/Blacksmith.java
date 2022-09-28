package me.nootnoot.jackpotmcitems.customitems;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.interfaces.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class Blacksmith extends CustomItem {

	public Blacksmith(){
		super("blacksmith");
	}

	@Override
	public void createRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(JackpotMCItems.getInstance(), "blacksmith"), getItem());
		recipe.shape(" F ", "FNF", " F ");
		recipe.setIngredient('N', Material.NETHERITE_SCRAP);
		recipe.setIngredient('F', Material.FIREWORK_STAR);
		Bukkit.addRecipe(recipe);
		setShapedRecipe(recipe);
	}
}
