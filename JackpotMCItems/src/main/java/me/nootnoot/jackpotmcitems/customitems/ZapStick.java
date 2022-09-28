package me.nootnoot.jackpotmcitems.customitems;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.interfaces.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;


public class ZapStick extends CustomItem {

	public ZapStick() {
		super("zap_stick");
	}

	@Override
	public void createRecipe(){
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(JackpotMCItems.getInstance(), "zap_stick"), getItem());
		ItemStack potion = new ItemStack(Material.POTION);
		PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
		potionMeta.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER, false, false));
		potion.setItemMeta(potionMeta);

		recipe.shape("GGG", "GPG", "GGG");
		recipe.setIngredient('G', Material.GOLD_INGOT);
		recipe.setIngredient('P', potion);
		Bukkit.addRecipe(recipe);
		setShapedRecipe(recipe);
	}
}
