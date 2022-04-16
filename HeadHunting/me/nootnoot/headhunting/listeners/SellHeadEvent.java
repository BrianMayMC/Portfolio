package me.nootnoot.headhunting.listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.entities.Booster;
import me.nootnoot.headhunting.entities.HHPlayer;
import me.nootnoot.headhunting.entities.Head;
import me.nootnoot.headhunting.entities.Level;
import me.nootnoot.headhunting.managers.BoosterManager;
import me.nootnoot.headhunting.managers.HHPlayerManager;
import me.nootnoot.headhunting.managers.HeadManager;
import me.nootnoot.headhunting.managers.LevelManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SellHeadEvent implements Listener {

	@EventHandler
	public void onHeadInteract(PlayerInteractEvent e) {

		if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
		if (e.getItem() == null) return;
		if (e.getItem().getType() == Material.AIR) return;
		ItemStack s = e.getItem();
		NBTItem nbtS = new NBTItem(s);

		if (!nbtS.hasNBTData()) return;
		if (!nbtS.hasKey("head")) return;

		Player p = e.getPlayer();
		HHPlayer hhP = HHPlayerManager.getInstance().getHHPlayer(p);
		Level level = LevelManager.getInstance().byInt(hhP.getLevel());

		if (!s.getItemMeta().hasDisplayName()) return;

		String name = s.getItemMeta().getDisplayName().toLowerCase();
		Head head = null;
		for (Head headtofind : HeadManager.getInstance().getHeads()) {
			if (name.contains(headtofind.getConfigname())) {
				head = headtofind;
			}
		}
		if (head == null) {
			return;
		}

		if (hhP.getLevel() + 1 == 16) {
			p.sendMessage("config");
		}

		ArrayList<String> entities = level.getAcceptedEntities();

		boolean accepted = false;

		for (String string : entities) {
			if (name.contains(string)) {
				accepted = true;
				break;
			}
		}

		if (!accepted) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You are not allowed to sell this head!");
			return;
		}

		if (!p.isSneaking()) {
			int amount = p.getInventory().getItemInHand().getAmount();
			p.getInventory().setItem(p.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
			p.playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 1, 1);
			hhP.setSoldHeads(hhP.getSoldHeads() + (head.getSoulsAmount() * amount));

			float requiredHeads = LevelManager.getInstance().byInt(hhP.getLevel()).getRequiredHeads();
			float currentHeads = hhP.getSoldHeads();

			float expToSet = currentHeads / requiredHeads;

			if (BoosterManager.GetInstance().getActiveboosters().containsKey(p)) {
				Booster booster = BoosterManager.GetInstance().getActiveboosters().get(p);
				float totalexp = (float) (expToSet * booster.getAmplifier());
				if (!(p.getExp() >= 1)) {
					if (totalexp > 1) {
						p.setExp(1);
					} else {
						p.setExp(totalexp);
					}
				}
			} else {
				if (!(p.getExp() >= 1)) {
					if (expToSet > 1) {
						p.setExp(1);
					} else {
						p.setExp(expToSet);
					}
				}
			}
		} else {
			int amount = 0;
			for (ItemStack is : p.getInventory().getContents()) {
				if (is != null && is.getType() != Material.AIR) {
					NBTItem nbtIs = new NBTItem(is);
					if (nbtIs.hasNBTData()) {
						if (nbtIs.hasKey("head")) {
							if (is.getItemMeta().getDisplayName().equalsIgnoreCase(p.getItemInHand().getItemMeta().getDisplayName()))
								amount = amount + is.getAmount();
						}
					}
				}
			}
			p.getInventory().remove(p.getItemInHand());
			p.playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 1, 1);
			hhP.setSoldHeads(hhP.getSoldHeads() + (head.getSoulsAmount() * amount));

			float requiredHeads = LevelManager.getInstance().byInt(hhP.getLevel()).getRequiredHeads();
			float currentHeads = hhP.getSoldHeads();

			float expToSet = currentHeads / requiredHeads;
			if (BoosterManager.GetInstance().getActiveboosters().containsKey(p)) {
				Booster booster = BoosterManager.GetInstance().getActiveboosters().get(p);
				float totalexp = (float) (expToSet * booster.getAmplifier());
				if (!(p.getExp() >= 1)) {
					if (totalexp > 1) {
						p.setExp(1);
					} else {
						p.setExp(totalexp);
					}
				}
			}else {
				if (!(p.getExp() >= 1)) {
					if (expToSet > 1) {
						p.setExp(1);
					} else {
						p.setExp(expToSet);
					}
				}
			}
		}
	}
}