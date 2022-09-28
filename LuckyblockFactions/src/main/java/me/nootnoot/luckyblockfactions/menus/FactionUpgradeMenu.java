package me.nootnoot.luckyblockfactions.menus;

import me.nootnoot.framework.menusystem.MenuInterface;
import me.nootnoot.framework.menusystem.entities.Slot;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class FactionUpgradeMenu extends MenuInterface {
	public FactionUpgradeMenu() {
		super(Utils.c(LuckyblockFactions.getInstance().getUpgradeMenuConfig().getConfig().getString("menu.name")), LuckyblockFactions.getInstance().getUpgradeMenuConfig().getConfig().getInt("menu.size"));
	}

	@Override
	public void define() {
		final FileConfiguration config = LuckyblockFactions.getInstance().getUpgradeMenuConfig().getConfig();
		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(getOwner());
		for(String s : config.getConfigurationSection("menu.items.").getKeys(false)){
			ItemStack item = new ItemStack(Material.matchMaterial(config.getString("menu.items." + s + ".material")));
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(Utils.c(config.getString("menu.items." + s + ".name")));
			List<String> lore = new ArrayList<>();
			String upgrade = config.getString("menu.items." + s + ".upgrade");
			for(String l : config.getStringList("menu.items." + s + ".lore")){
				if(upgrade.equalsIgnoreCase("SIZE")) {
					String next = "N/A";
					if(getSizeForLevel(f.getSizeUpgrade() + 1) != 0){
						next = String.valueOf(getSizeForLevel(f.getSizeUpgrade() + 1));
					}
					lore.add(Utils.c(l.replace("%current%", String.valueOf(f.getSizeUpgrade()))
							.replace("%size%", String.valueOf(getSizeForLevel(f.getSizeUpgrade())))
							.replace("%next%", next)
							.replace("%cost%", Utils.formatCurrency(config.getDouble("upgrades.size." + f.getSizeUpgrade() + ".cost")))));
				}else{
					String next = "N/A";
					if(getPowerForLevel(f.getPowerUpgrade() + 1) != 0){
						next = String.valueOf(getPowerForLevel(f.getPowerUpgrade() + 1));
					}
					lore.add(Utils.c(l.replace("%current%", String.valueOf(f.getPowerUpgrade()))
							.replace("%power%", String.valueOf(getPowerForLevel(f.getPowerUpgrade())))
							.replace("%next%", next)
							.replace("%cost%", Utils.formatCurrency(config.getDouble("upgrades.power." + f.getPowerUpgrade() + ".cost")))));
				}
			}
			meta.setLore(lore);
			item.setItemMeta(meta);

			Slot slot = new Slot(config.getInt("menu.items." + s + ".slot"), item);
			setSlot(slot);


			slot.setAction(()->{
				if(upgrade.equalsIgnoreCase("SIZE")){
					if(LuckyblockFactions.getInstance().getEcon().getBalance(getOwner()) < config.getDouble("upgrades.size." + f.getSizeUpgrade() + ".cost")){
						getOwner().sendMessage(Utils.c("&C&l(!)&C You do not have enough money to purchase this."));
						return;
					}
					LuckyblockFactions.getInstance().getEcon().withdrawPlayer(getOwner(), config.getDouble("upgrades.size." + f.getSizeUpgrade() + ".cost"));
					if(getSizeForLevel(f.getSizeUpgrade() + 1) == 0){
						getOwner().sendMessage(Utils.c("&C&l(!)&c This upgrade is already max level!"));
						return;
					}
					f.setSizeUpgrade(f.getSizeUpgrade() + 1);
					LuckyblockFactions.getInstance().getMenuManager().closeInterface(getOwner());
				}else if(upgrade.equalsIgnoreCase("POWER")){
					if(LuckyblockFactions.getInstance().getEcon().getBalance(getOwner()) < config.getDouble("upgrades.power." + f.getPowerUpgrade() + ".cost")){
						getOwner().sendMessage(Utils.c("&C&l(!)&C You do not have enough money to purchase this."));
						return;
					}
					LuckyblockFactions.getInstance().getEcon().withdrawPlayer(getOwner(), config.getDouble("upgrades.power." + f.getPowerUpgrade() + ".cost"));
					if(getPowerForLevel(f.getPowerUpgrade() + 1) == 0){
						getOwner().sendMessage(Utils.c("&C&l(!)&c This upgrade is already max level!"));
						return;
					}
					f.setPowerUpgrade(f.getPowerUpgrade() + 1);
					LuckyblockFactions.getInstance().getMenuManager().closeInterface(getOwner());
				}
			});
		}
	}

	public double getSizeForLevel(int level){
		return LuckyblockFactions.getInstance().getUpgradeMenuConfig().getConfig().getDouble("upgrades.size." + level + ".value");
	}

	public double getPowerForLevel(int level){
		return LuckyblockFactions.getInstance().getUpgradeMenuConfig().getConfig().getDouble("upgrades.power." + level + ".value");
	}
}
