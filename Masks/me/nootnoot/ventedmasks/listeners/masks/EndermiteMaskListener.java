package me.nootnoot.ventedmasks.listeners.masks;

import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.ventedmasks.VentedMasks;
import me.nootnoot.ventedmasks.entities.Mask;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

public class EndermiteMaskListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		Player p = e.getPlayer();

		NBTItem nbtHelmet = null;
		NBTItem nbtHand = null;
		Mask helmetMask = null;
		Mask handMask = null;

		if(p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() != Material.AIR){
			nbtHelmet = new NBTItem(p.getInventory().getHelmet());
			String id = nbtHelmet.getString("id");
			helmetMask = VentedMasks.getMaskManager().byName(id);
		}
		if (p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType() != Material.AIR) {
			nbtHand = new NBTItem(p.getInventory().getItemInHand());
			String id = nbtHand.getString("id");
			handMask = VentedMasks.getMaskManager().byName(id);
		}

		if(nbtHelmet != null){
			if(nbtHelmet.hasKey("mask")) {
				if (nbtHelmet.getItem().getItemMeta().getDisplayName().contains("Endermite")) {
					e.setCancelled(true);
					p.getInventory().addItem(e.getBlock().getDrops().toArray(new ItemStack[0]));
					e.getBlock().setType(Material.AIR);
				}
			}
		}
		if(nbtHand != null){
			if(nbtHand.hasKey("mask")) {
				if (nbtHand.getItem().getItemMeta().getDisplayName().contains("Endermite")) {
					e.setCancelled(true);
					p.getInventory().addItem(e.getBlock().getDrops().toArray(new ItemStack[0]));
					e.getBlock().setType(Material.AIR);
				}
			}
		}
	}
	@EventHandler
	public void onObbyBreak(BlockDamageEvent e){
		Player p = e.getPlayer();

		NBTItem nbtHelmet = null;
		NBTItem nbtHand = null;
		Mask helmetMask = null;
		Mask handMask = null;

		if(p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() != Material.AIR){
			nbtHelmet = new NBTItem(p.getInventory().getHelmet());
			String id = nbtHelmet.getString("id");
			helmetMask = VentedMasks.getMaskManager().byName(id);
		}
		if (p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType() != Material.AIR) {
			nbtHand = new NBTItem(p.getInventory().getItemInHand());
			String id = nbtHand.getString("id");
			handMask = VentedMasks.getMaskManager().byName(id);
		}

		if(nbtHelmet != null){
			if(nbtHelmet.hasKey("mask")) {
				if (nbtHelmet.getItem().getItemMeta().getDisplayName().contains("Endermite")) {
					int level = nbtHelmet.getInteger("level");
					if(level > 2) {
						if (e.getBlock().getType() == Material.OBSIDIAN) {
							e.getBlock().setType(Material.AIR);
							p.getInventory().addItem(new ItemStack(Material.OBSIDIAN));
						}
					}
				}
			}
		}
		if(nbtHand != null){
			if(nbtHand.hasKey("mask")){
				if (nbtHand.getItem().getItemMeta().getDisplayName().contains("Endermite")) {
					if (e.getBlock().getType() == Material.OBSIDIAN) {
						e.getBlock().setType(Material.AIR);
						p.getInventory().addItem(new ItemStack(Material.OBSIDIAN));
					}
				}
			}
		}
	}
}
