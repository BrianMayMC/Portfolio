package me.nootnoot.jackpotmcitems.menus;

import me.nootnoot.framework.menusystem.MenuInterface;
import me.nootnoot.framework.menusystem.entities.Slot;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.interfaces.CustomItem;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BlackMarketMenu extends MenuInterface {
	public BlackMarketMenu() {
		super(Utils.c(JackpotMCItems.getInstance().getBlackMarketMenuConfig().getConfig().getString("menu.name")), JackpotMCItems.getInstance().getBlackMarketMenuConfig().getConfig().getInt("menu.size"));
	}

	@Override
	public void define() {
		final FileConfiguration config = JackpotMCItems.getInstance().getBlackMarketMenuConfig().getConfig();
		for(String s : config.getConfigurationSection("menu.items.").getKeys(false)){
			ItemStack item = new ItemStack(Material.matchMaterial(config.getString("menu.items." + s + ".material")));
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(Utils.c(config.getString("menu.items." + s + ".name")));
			List<String> lore = new ArrayList<>();
			for(String l : config.getStringList("menu.items." + s + ".lore")){
				lore.add(Utils.c(l));
			}
			meta.setLore(lore);
			item.setItemMeta(meta);

			int price = config.getInt("menu.items." + s + ".price");
			String type = config.getString("menu.items." + s + ".type");
			Slot slot = new Slot(config.getInt("menu.items." + s + ".slot"), item);
			slot.setAction(()->{
				if(!(hasAmountOfStars(price))){
					getOwner().sendMessage(Utils.c("&c&l(!)&c You do not have enough nether stars for this."));
					return;
				}
				takeStarsFromInventory(price);
				CustomItem ci = JackpotMCItems.getInstance().getItemManager().getItem(type);
				getOwner().getInventory().addItem(ci.getItem());
				JackpotMCItems.getInstance().getMenuManager().closeInterface(getOwner());
			});
			setSlot(slot);
		}
	}

	public boolean hasAmountOfStars(int amount){
		int i = 0;
		for(ItemStack item : getOwner().getInventory().getContents()){
			if(item != null){
				if(item.getType() == Material.NETHER_STAR){
					i += item.getAmount();
				}
			}
		}
		return i >= amount;
	}

	public void takeStarsFromInventory(int amount){
		getOwner().getInventory().removeItem(new ItemStack(Material.NETHER_STAR, amount));
	}
}
