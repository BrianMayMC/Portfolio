package me.nootnoot.ventedmasks.listeners;

import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import me.nootnoot.ventedmasks.VentedMasks;
import me.nootnoot.ventedmasks.entities.Mask;
import me.nootnoot.ventedmasks.listeners.tasks.NPCTask;
import me.nootnoot.ventedmasks.listeners.tasks.PotionExpireTaskHand;
import me.nootnoot.ventedmasks.listeners.tasks.PotionExpireTaskHelmet;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class EquipListener implements Listener {

	private BukkitTask potionExpireTaskHelmetDrag = null;
	private BukkitTask potionExpireTaskHelmetShiftClick = null;
	private BukkitTask potionExpireTaskHandMovedToSlot = null;
	private BukkitTask potionExpireTaskEquip = null;
	private BukkitTask potionExpireTaskHold = null;

	public static ItemStack returnPickaxe(){
		ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);

		ItemMeta pickaxe_meta = pickaxe.getItemMeta();
		pickaxe_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9&l*&f&l*&9&l* &b&lRowan Staff &7(Trench 1x1x1) &9&l*&f&l*&9&l*"));
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7It's carved from the Rowan Tree that grows"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7at the very heart of the &nIsle of the Blessed&7."));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Only the &nHigh Priestesses&7 and their &nBlood Guard&7"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7have ever set eyes on it."));
		pickaxe_meta.setLore(lore);
		pickaxe.setItemMeta(pickaxe_meta);

		NBTItem nbtPickaxe = new NBTItem(pickaxe);

		nbtPickaxe.setBoolean("maskspickaxe", true);

		return nbtPickaxe.getItem();
	}

	@EventHandler
	public void onNPC(NPCRightClickEvent e){
		if(e.getClicker().getItemInHand() != null && e.getClicker().getItemInHand().getType() != Material.AIR) {
			ItemStack item = e.getClicker().getItemInHand();
			int slot = e.getClicker().getInventory().getHeldItemSlot();
			NBTItem nbtItem = new NBTItem(item);
			if (nbtItem.hasKey("mask")) {
				e.setCancelled(true);
				new NPCTask(e.getClicker(), slot, item).runTaskLater(VentedMasks.getInstance(), 1L);
			}
		}
	}

	@EventHandler
	public void onInteractEquip(PlayerInteractEvent e) {

		if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
		if (e.getItem() == null) return;

		Player p = e.getPlayer();

		p.closeInventory();

		ItemStack item = e.getItem();
		NBTItem nbtItem = new NBTItem(item);

		if (nbtItem.hasKey("mask")) {
			e.setCancelled(true);
			String id = nbtItem.getString("id");
			Mask mask = VentedMasks.getMaskManager().byName(id);

			if(p.isSneaking() && nbtItem.getItem().getItemMeta().getDisplayName().contains("Silverfish")) return;

			p.setItemInHand(p.getInventory().getHelmet());
			p.getInventory().setHelmet(item);

			int level = nbtItem.getInteger("level");

			if (p.getInventory().contains(returnPickaxe())) {
				p.getInventory().remove(returnPickaxe());
			}
			if (nbtItem.getItem().getItemMeta().getDisplayName().contains("Silverfish")) {

				p.getInventory().remove(returnPickaxe());
				p.getInventory().addItem(returnPickaxe());
			}

			if (!nbtItem.getItem().getItemMeta().getDisplayName().contains("Chicken") && !nbtItem.getItem().getItemMeta().getDisplayName().contains("Endermite") &&
					!nbtItem.getItem().getItemMeta().getDisplayName().contains("Silverfish") && !nbtItem.getItem().getItemMeta().getDisplayName().contains("Sheep")) {
				for(PotionEffect potionEffect : mask.getBoosts().get(level).getEffects()){
					p.addPotionEffect(potionEffect, true);
				}
				if(potionExpireTaskHandMovedToSlot != null){
					potionExpireTaskHandMovedToSlot.cancel();
				}
				if(potionExpireTaskHelmetDrag != null){
					potionExpireTaskHelmetDrag.cancel();
				}
				if(potionExpireTaskHelmetShiftClick != null){
					potionExpireTaskHelmetShiftClick.cancel();
				}
				if(potionExpireTaskEquip != null){
					potionExpireTaskEquip.cancel();
				}
				if(potionExpireTaskHold != null){
					potionExpireTaskHold.cancel();
				}
				potionExpireTaskEquip = new PotionExpireTaskHelmet(p).runTaskTimer(VentedMasks.getInstance(), 10 * 20L, 10 * 20L);

			}
		}
	}

	@EventHandler
	public void OnInvClick(InventoryClickEvent e) {


		Player player = (Player) e.getWhoClicked();

			PlayerInventory playerInventory = player.getInventory();
			int heldItemSlot = playerInventory.getHeldItemSlot();

			if (e.getSlot() == 39) {
				ItemStack holdingitem = e.getCursor();
				ItemStack currentHelmet = e.getCurrentItem();

				if (currentHelmet != null) {
					if (currentHelmet.getType() != Material.AIR) {
						NBTItem nbtCurrentItem = new NBTItem(currentHelmet);
						if (nbtCurrentItem.hasNBTData()) {
							if (nbtCurrentItem.hasKey("mask")) {
								if (nbtCurrentItem.getItem().getItemMeta().getDisplayName().contains("Silverfish")) {
									if (player.getInventory().contains(returnPickaxe())) {
										player.getInventory().remove(returnPickaxe());
									}
								}
							}
						}
					}
				}
				if (holdingitem == null || holdingitem.getType() == Material.AIR) return;

				NBTItem nbtHoldingItem = new NBTItem(holdingitem);

					if (!nbtHoldingItem.hasNBTData()) return;

					if (!nbtHoldingItem.hasKey("mask")) return;

					String id = nbtHoldingItem.getString("id");
					int level = nbtHoldingItem.getInteger("level");
					Mask mask = VentedMasks.getMaskManager().byName(id);

					player.getInventory().setHelmet(e.getCurrentItem());

				if (nbtHoldingItem.getItem().getItemMeta().getDisplayName().contains("Silverfish")) {
					player.getInventory().remove(returnPickaxe());
					player.getInventory().addItem(returnPickaxe());
				}

				if (!nbtHoldingItem.getItem().getItemMeta().getDisplayName().contains("Chicken") && !nbtHoldingItem.getItem().getItemMeta().getDisplayName().contains("Endermite") &&
						!nbtHoldingItem.getItem().getItemMeta().getDisplayName().contains("Silverfish") && !nbtHoldingItem.getItem().getItemMeta().getDisplayName().contains("Sheep")) {
					for(PotionEffect potionEffect : mask.getBoosts().get(level).getEffects()){
						player.addPotionEffect(potionEffect, true);
					}

					if(potionExpireTaskHandMovedToSlot != null){
						potionExpireTaskHandMovedToSlot.cancel();
					}
					if(potionExpireTaskHelmetDrag != null){
						potionExpireTaskHelmetDrag.cancel();
					}
					if(potionExpireTaskHelmetShiftClick != null){
						potionExpireTaskHelmetShiftClick.cancel();
					}
					if(potionExpireTaskEquip != null){
						potionExpireTaskEquip.cancel();
					}
					if(potionExpireTaskHold != null){
						potionExpireTaskHold.cancel();
					}
					potionExpireTaskHelmetDrag = new PotionExpireTaskHelmet(player).runTaskTimer(VentedMasks.getInstance(), 10 * 20L, 10 * 20L);

				}
			}

			if (e.getClick().isShiftClick()) {
				if (e.getView().getTopInventory().getType() != InventoryType.CRAFTING) {
					return;
				}

				ItemStack currenthelmet2 = player.getInventory().getHelmet();
				if (currenthelmet2 != null) {
					if (currenthelmet2.getType() != Material.AIR) {
						NBTItem nbtCurrentHelmet = new NBTItem(currenthelmet2);
						if (nbtCurrentHelmet.hasNBTData()) {
							if (nbtCurrentHelmet.hasKey("mask")) {
								if (nbtCurrentHelmet.getItem().getItemMeta().getDisplayName().contains("Silverfish")) {
									if (player.getInventory().contains(returnPickaxe())) {
										player.getInventory().remove(returnPickaxe());
									}
								}
							}
						}
					}
				}
				ItemStack clickeditem = e.getCurrentItem();

				if (clickeditem == null || clickeditem.getType() == Material.AIR) return;

				NBTItem nbtClickedItem = new NBTItem(clickeditem);

				if (!nbtClickedItem.hasNBTData()) return;

				if (!nbtClickedItem.hasKey("mask")) return;

				String id = nbtClickedItem.getString("id");
				int level = nbtClickedItem.getInteger("level");
				Mask mask = VentedMasks.getMaskManager().byName(id);


				ItemStack currenthelmet = player.getInventory().getHelmet();


				if (currenthelmet != null && currenthelmet.getType() != Material.AIR) {
					player.getInventory().addItem(currenthelmet);
				}

				player.getInventory().setHelmet(e.getCurrentItem());
				player.getInventory().removeItem(e.getCurrentItem());

				if (nbtClickedItem.getItem().getItemMeta().getDisplayName().contains("Silverfish")) {
					player.getInventory().remove(returnPickaxe());
					player.getInventory().addItem(returnPickaxe());
				}

				if (!nbtClickedItem.getItem().getItemMeta().getDisplayName().contains("Chicken") && !nbtClickedItem.getItem().getItemMeta().getDisplayName().contains("Endermite") &&
						!nbtClickedItem.getItem().getItemMeta().getDisplayName().contains("Silverfish") && !nbtClickedItem.getItem().getItemMeta().getDisplayName().contains("Sheep")) {
					for(PotionEffect potionEffect : mask.getBoosts().get(level).getEffects()){
						player.addPotionEffect(potionEffect, true);
					}
					if(potionExpireTaskHandMovedToSlot != null){
						potionExpireTaskHandMovedToSlot.cancel();
					}
					if(potionExpireTaskHelmetDrag != null){
						potionExpireTaskHelmetDrag.cancel();
					}
					if(potionExpireTaskHelmetShiftClick != null){
						potionExpireTaskHelmetShiftClick.cancel();
					}
					if(potionExpireTaskEquip != null){
						potionExpireTaskEquip.cancel();
					}
					if(potionExpireTaskHold != null){
						potionExpireTaskHold.cancel();
					}
					potionExpireTaskHelmetShiftClick = new PotionExpireTaskHelmet(player).runTaskTimer(VentedMasks.getInstance(), 10 * 20L, 10 * 20L);

				}
			}

			if (e.getSlot() == heldItemSlot) {

				ItemStack holdingitem = e.getCursor();

				if (holdingitem == null || holdingitem.getType() == Material.AIR) return;

				NBTItem nbtHoldingItem = new NBTItem(holdingitem);

				if (!nbtHoldingItem.hasNBTData()) return;

				if (!nbtHoldingItem.hasKey("mask")) return;

				String id = nbtHoldingItem.getString("id");
				int level = nbtHoldingItem.getInteger("level");
				Mask mask = VentedMasks.getMaskManager().byName(id);

				if (!nbtHoldingItem.getItem().getItemMeta().getDisplayName().contains("Chicken") && !nbtHoldingItem.getItem().getItemMeta().getDisplayName().contains("Endermite") &&
						!nbtHoldingItem.getItem().getItemMeta().getDisplayName().contains("Silverfish") && !nbtHoldingItem.getItem().getItemMeta().getDisplayName().contains("Sheep")) {
						for(PotionEffect potionEffect : mask.getBoosts().get(level).getEffects()){
							player.addPotionEffect(potionEffect, true);
						}
						if(potionExpireTaskHandMovedToSlot != null){
							potionExpireTaskHandMovedToSlot.cancel();
						}
						if(potionExpireTaskHelmetDrag != null){
							potionExpireTaskHelmetDrag.cancel();
						}
						if(potionExpireTaskHelmetShiftClick != null){
							potionExpireTaskHelmetShiftClick.cancel();
						}
						if(potionExpireTaskEquip != null){
							potionExpireTaskEquip.cancel();
						}
						if(potionExpireTaskHold != null){
							potionExpireTaskHold.cancel();
						}
						potionExpireTaskHandMovedToSlot = new PotionExpireTaskHand(player).runTaskTimer(VentedMasks.getInstance(), 10 * 20L, 10 * 20L);

					}
				}
			}

	@EventHandler
	public void onEquip(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();

		int index = e.getNewSlot();
		ItemStack item = p.getInventory().getItem(index);

		if (item == null || item.getType() == Material.AIR) return;

		NBTItem nbtItem = new NBTItem(item);

		if (nbtItem.hasKey("mask")) {
			String id = nbtItem.getString("id");
			Mask mask = VentedMasks.getMaskManager().byName(id);

			int level = nbtItem.getInteger("level");
			if (!nbtItem.getItem().getItemMeta().getDisplayName().contains("Chicken") && !nbtItem.getItem().getItemMeta().getDisplayName().contains("Endermite") &&
					!nbtItem.getItem().getItemMeta().getDisplayName().contains("Silverfish") && !nbtItem.getItem().getItemMeta().getDisplayName().contains("Sheep")) {
				for(PotionEffect potionEffect : mask.getBoosts().get(level).getEffects()){
					p.addPotionEffect(potionEffect, true);
				}
				if(potionExpireTaskHandMovedToSlot != null){
					potionExpireTaskHandMovedToSlot.cancel();
				}
				if(potionExpireTaskHelmetDrag != null){
					potionExpireTaskHelmetDrag.cancel();
				}
				if(potionExpireTaskHelmetShiftClick != null){
					potionExpireTaskHelmetShiftClick.cancel();
				}
				if(potionExpireTaskEquip != null){
					potionExpireTaskEquip.cancel();
				}
				if(potionExpireTaskHold != null){
					potionExpireTaskHold.cancel();
				}

				potionExpireTaskHold = new PotionExpireTaskHand(p).runTaskTimer(VentedMasks.getInstance(), 10 * 20L, 10 * 20L);

			}
		}
	}
}

