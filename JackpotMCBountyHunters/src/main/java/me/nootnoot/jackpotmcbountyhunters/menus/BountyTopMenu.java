package me.nootnoot.jackpotmcbountyhunters.menus;

import me.nootnoot.framework.menusystem.MenuInterface;
import me.nootnoot.framework.menusystem.entities.Slot;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmcbountyhunters.JackpotMCBountyHunters;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BountyTopMenu extends MenuInterface {
	public BountyTopMenu() {
		super(Utils.c(JackpotMCBountyHunters.getInstance().getBountyTopMenuConfig().getConfig().getString("menu.name")), JackpotMCBountyHunters.getInstance().getBountyTopMenuConfig().getConfig().getInt("menu.size"));
	}

	private final FileConfiguration config = JackpotMCBountyHunters.getInstance().getBountyTopMenuConfig().getConfig();

	@Override
	public void define() {
		for(String path : config.getConfigurationSection("menu.items.").getKeys(false)){
			ItemStack item = new ItemStack(Material.matchMaterial(config.getString("menu.items." + path + ".material")));
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(Utils.c(config.getString("menu.items." + path + ".name")));
			meta.setLore(Utils.cL(config.getStringList("menu.items." + path + ".lore")));
			item.setItemMeta(meta);
			Slot slot = new Slot(config.getInt("menu.items." + path + ".slot"), item);
			slot.setAction(()->{
				if(e.getCurrentItem() == null) return;
				if(e.getCurrentItem().getType() == Material.BARRIER){
					JackpotMCBountyHunters.getInstance().getMenuManager().closeInterface(getOwner());
				}
			});
		}

		ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.c("&fEmpty Slot"));
		item.setItemMeta(meta);

		setSlot(new Slot(12, item));
		setSlot(new Slot(13, item));
		setSlot(new Slot(14, item));

		setSlot(new Slot(20, item));
		setSlot(new Slot(21, item));
		setSlot(new Slot(22, item));
		setSlot(new Slot(23, item));
		setSlot(new Slot(24, item));

		setSlot(new Slot(29, item));
		setSlot(new Slot(30, item));
		setSlot(new Slot(31, item));
		setSlot(new Slot(32, item));
		setSlot(new Slot(33, item));

		List<Integer> numbers = List.of(12, 13, 14, 20, 21, 22, 23, 24, 29, 30, 31, 32, 33);
		List<ItemStack> bounties = JackpotMCBountyHunters.getInstance().getTop10Bounties();
		int j = 0;
		for(int i : numbers){
			if(bounties.size() - 1 < j) break;
			setSlot(new Slot(i, bounties.get(j)));
			j++;
		}
	}
}
