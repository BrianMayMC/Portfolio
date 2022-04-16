package me.nootnoot.ventedmasks.entities;

import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.ventedmasks.VentedMasks;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class UpgradeItem implements Listener {

	private static final ItemStack upgrade = new ItemStack(Material.NETHER_STAR);

	public static void giveItem(Player p, int amount){
		ItemMeta upgrade_meta = upgrade.getItemMeta();
		upgrade_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', VentedMasks.getInstance().getConfig().getString("upgrade-item.name")));
		ArrayList<String> lore = new ArrayList<>();
		for(String s : VentedMasks.getInstance().getConfig().getStringList("upgrade-item.lore")){
			lore.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		upgrade_meta.setLore(lore);
		upgrade_meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		upgrade_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		upgrade.setItemMeta(upgrade_meta);
		NBTItem nbtUpgrade = new NBTItem(upgrade);
		Random random = new Random();
		int randomnumber = random.nextInt(2124999999);
		nbtUpgrade.setInteger("upgrade", randomnumber);
		for(int i = 0; i < amount; i++){
			p.getInventory().addItem(nbtUpgrade.getItem());
		}
	}

	@EventHandler
	public void onDragNDrop(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getAction() != InventoryAction.SWAP_WITH_CURSOR) return;
		ItemStack cursorItem = e.getCursor();
		NBTItem nbtCursor = new NBTItem(cursorItem);
		ItemStack currentItem = e.getCurrentItem();
		NBTItem nbtCurrent = new NBTItem(currentItem);

		if (!nbtCurrent.hasNBTData()) return;
		if (!nbtCurrent.hasKey("mask")) return;
		if (!nbtCursor.hasNBTData()) return;
		if (!nbtCursor.hasKey("upgrade")) return;




		e.setCancelled(true);

		String id = nbtCurrent.getString("id");
		Mask mask = VentedMasks.getMaskManager().byName(id);
		int level = nbtCurrent.getInteger("level");

		if(level == 5){
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!)&c This mask is already level 5!"));
			return;
		}
		currentItem = mask.upgradeMask(currentItem);

		p.getInventory().remove(e.getCursor());
		p.setItemOnCursor(new ItemStack(Material.AIR));

		String name = currentItem.getItemMeta().getDisplayName();

		if(level == 1){
			name = name.replace("I", "II");
		}else if(level == 2){
			name = name.replace("II", "III");
		}else if(level == 3){
			name = name.replace("III", "IV");
		}else if(level == 4){
			name = name.replace("IV", "V");
		}


		ItemMeta currentItem_meta = currentItem.getItemMeta();
		currentItem_meta.setDisplayName(name);
		currentItem.setItemMeta(currentItem_meta);

		p.getInventory().remove(currentItem);
		p.getInventory().setItem(e.getSlot(), currentItem);




	}
}
