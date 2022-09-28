package me.nootnoot.jackpotmcitems.interfaces;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.entities.*;
import me.nootnoot.jackpotmcitems.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public abstract class CustomItem {

	@Getter@Setter
	private ItemEffects itemEffects;
	@Getter@Setter
	private int cooldown;
	@Getter@Setter
	private ShapedRecipe shapedRecipe;
	@Getter
	private final String name;
	@Getter
	private TextComponent itemName;
	@Getter@Setter
	private int hitAmounts = 0;
	@Getter@Setter
	private ItemStack item;
	

	private final FileConfiguration config = JackpotMCItems.getInstance().getConfig();

	public CustomItem(String name){
		this.name = name;
		createRest();
		createItem();
		createItemEffects();
		if(JackpotMCItems.getInstance().isUseRecipes()) {
			createRecipe();
		}
	}

	public void createItemEffects(){
		ArrayList<SourceSounds> sourceSounds = new ArrayList<>();
		ArrayList<TargetSounds> targetSounds = new ArrayList<>();
		ArrayList<SourceEffects> sourceEffects = new ArrayList<>();
		ArrayList<TargetEffects> targetEffects = new ArrayList<>();

		ArrayList<PotionEffect> potionEffects = new ArrayList<>();
		ArrayList<Particles> particles = new ArrayList<>();


		for(String path : config.getStringList("items." + name + ".particles")){
			String[] splittedString = path.split(";");
			particles.add(new Particles(Particle.valueOf(splittedString[0].toUpperCase()), Integer.parseInt(splittedString[1])));
		}

		for(String path : config.getStringList("items." + name + ".effects.target")){
			String[] splittedString = path.split(";");
			potionEffects.add(new PotionEffect(PotionEffectType.getByName(splittedString[0].toUpperCase()), (Integer.parseInt(splittedString[2]) * 20), Integer.parseInt(splittedString[1])));
		}
		targetEffects.add(new TargetEffects(potionEffects));

		ArrayList<PotionEffect> potionEffects2 = new ArrayList<>();

		for(String path : config.getStringList("items." + name + ".effects.source")){
			String[] splittedString = path.split(";");
			potionEffects2.add(new PotionEffect(PotionEffectType.getByName(splittedString[0].toUpperCase()), (Integer.parseInt(splittedString[2]) * 20), Integer.parseInt(splittedString[1])));
		}
		sourceEffects.add(new SourceEffects(potionEffects2));


		for(String path : config.getConfigurationSection("items." + name + ".sounds.source").getKeys(false)){
			sourceSounds.add(new SourceSounds(
					Sound.valueOf(config.getString("items." + name + ".sounds.source." + path + ".name")),
					(float) config.getDouble("items." + name + ".sounds.source." + path + ".volume"),
					(float) config.getDouble("items." + name + ".sounds.source." + path + ".pitch")
			));
		}

		for(String path : config.getConfigurationSection("items." + name + ".sounds.target").getKeys(false)){
			targetSounds.add(new TargetSounds(
					Sound.valueOf(config.getString("items." + name + ".sounds.target." + path + ".name")),
					(float) config.getDouble("items." + name + ".sounds.target." + path + ".volume"),
					(float) config.getDouble("items." + name + ".sounds.target." + path + ".pitch")
			));
		}
		this.setItemEffects(new ItemEffects(sourceEffects, targetEffects, sourceSounds, targetSounds, particles));
	}

	public void createRest(){
		setCooldown(config.getInt("items." + name + ".cooldown"));
		setHitAmounts(config.getInt("items." + name + ".hit_count"));
	}

	public void createItem(){
		ItemStack item = new ItemStack(Material.valueOf(config.getString("items." + name + ".item.material")));
		ItemMeta meta = item.getItemMeta();

		ArrayList<Component> lore = new ArrayList<>();
		for(String s : config.getStringList("items." + name + ".item.lore")){
			lore.add(Utils.c(s));
		}
		itemName = Utils.c(config.getString("items." + name + ".item.name"));

		meta.displayName(itemName);
		meta.lore(lore);

		meta.getPersistentDataContainer().set(new NamespacedKey(JackpotMCItems.getInstance(), "customItem"), PersistentDataType.STRING, "customItem");
		meta.getPersistentDataContainer().set(new NamespacedKey(JackpotMCItems.getInstance(), "name"), PersistentDataType.STRING, name);
		item.setItemMeta(meta);
		if(JackpotMCItems.getInstance().isGlow()){
			item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}

		this.setItem(item);
	}

	public abstract void createRecipe();

	public void giveEffectsAndSounds(CustomItem customItem, Player p, Player target){
		for (TargetEffects t : customItem.getItemEffects().targetEffects()) {
			for (PotionEffect potion : t.potionEffects()) {
				target.addPotionEffect(potion);
			}
		}
		for (SourceEffects s : customItem.getItemEffects().sourceEffects()) {
			for (PotionEffect potion : s.potionEffects()) {
				p.addPotionEffect(potion);
			}
		}
		for (TargetSounds ts : customItem.getItemEffects().targetSounds()) {
			target.playSound(target.getLocation(), ts.sound(), ts.volume(), ts.pitch());
		}
		for (SourceSounds ss : customItem.getItemEffects().sourceSounds()) {
			p.playSound(p.getLocation(), ss.sound(), ss.volume(), ss.pitch());
		}
		for(Particles particles : customItem.getItemEffects().particles()) {
			Bukkit.getWorld(p.getWorld().getUID()).spawnParticle(particles.particle(), p.getLocation(), particles.count());
		}
	}

	public void giveEffectsAndSounds(CustomItem customItem, Player p){
		for (SourceEffects s : customItem.getItemEffects().sourceEffects()) {
			for (PotionEffect potion : s.potionEffects()) {
				p.addPotionEffect(potion);
			}
		}
		for (SourceSounds ss : customItem.getItemEffects().sourceSounds()) {
			p.playSound(p.getLocation(), ss.sound(), ss.volume(), ss.pitch());
		}

		for(Particles particles : customItem.getItemEffects().particles()) {
			Bukkit.getWorld(p.getWorld().getUID()).spawnParticle(particles.particle(), p.getLocation(), particles.count());
		}
	}

	public void remove(ItemStack item, Player p){
		if(item.getAmount() > 1){
			item.setAmount(item.getAmount() - 1);
		}else{
			p.getInventory().remove(item);
		}
	}
}
