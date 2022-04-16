package me.nootnoot.ventedmasks.entities;

import lombok.Getter;
import me.nootnoot.ventedmasks.VentedMasks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class MaskManager {
	@Getter
	private final ArrayList<Mask> masks;

	public MaskManager(){
		masks = new ArrayList<>();
		fillMasks();
	}
	public void fillMasks(){
		for(String path : VentedMasks.getInstance().getConfig().getConfigurationSection("custommasks.").getKeys(false)){
			HashMap<Integer, MaskLevel> levels = new HashMap<>();
			path = "custommasks." + path;

			ArrayList<String> lore = new ArrayList<>();
			for(String s : VentedMasks.getInstance().getConfig().getStringList(path + ".lore")){
				lore.add(ChatColor.translateAlternateColorCodes('&', s));
			}

			for(String s : VentedMasks.getInstance().getConfig().getConfigurationSection(path + ".effects.levels").getKeys(false)){
				ArrayList<PotionEffect> effects = new ArrayList<>();

				for(String effect : VentedMasks.getInstance().getConfig().getStringList(path + ".effects.levels." + s + ".effects")){
					if(!path.equalsIgnoreCase("chicken") && !path.equalsIgnoreCase("endermite") &&
							!path.equalsIgnoreCase("silverfish") && !path.equalsIgnoreCase("sheep")) {
						String[] spit = effect.split(";");
						if (spit[0].equalsIgnoreCase("HEALTH_BOOST")) {
							effects.add(new PotionEffect(PotionEffectType.getByName(spit[0]), Integer.MAX_VALUE, Integer.parseInt(spit[1])));
						} else {
							effects.add(new PotionEffect(PotionEffectType.getByName(spit[0]), 15 * 20, Integer.parseInt(spit[1])));
						}
					}
					levels.put(Integer.parseInt(s),
							new MaskLevel(effects));
				}

			}
			masks.add(new Mask(
					VentedMasks.getInstance().getConfig().getString(path + ".name"),
					VentedMasks.getInstance().getConfig().getString(path + ".url"),
					lore,
					VentedMasks.getInstance().getConfig().getString(path + ".configname"),
					levels
			));
		}
	}

	public Mask byName(String name) {
		for(Mask mask : getMasks()){
			if(mask.getConfigname().equalsIgnoreCase(name)){
				return mask;
			}
		}
		return null;
	}

}
