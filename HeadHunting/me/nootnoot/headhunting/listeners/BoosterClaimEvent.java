package me.nootnoot.headhunting.listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.entities.Booster;
import me.nootnoot.headhunting.managers.BoosterManager;
import me.nootnoot.headhunting.tasks.BoosterTask;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BoosterClaimEvent implements Listener {
	@EventHandler
	public void onBoosterClaim(PlayerInteractEvent e){
		if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
		if (e.getItem() == null) return;
		if (e.getItem().getType() == Material.AIR) return;
		Player p = e.getPlayer();
		ItemStack is = e.getItem();
		NBTItem nbtIs = new NBTItem(is);
		if (!nbtIs.hasNBTData()) return;
		if (!nbtIs.hasKey("booster")) return;

		if(BoosterManager.GetInstance().getActiveboosters().containsKey(p)){
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					HeadHunting.getInstance().getConfig().getString("messages.boosters.activebooster")));
			return;
		}
		Booster booster = BoosterManager.GetInstance().ByName(is.getItemMeta().getDisplayName());

		if(p.getInventory().getItemInHand().getAmount() == 1)
			p.getInventory().setItemInHand(new ItemStack(Material.AIR));
		else
			p.getInventory().getItemInHand().setAmount(p.getInventory().getItemInHand().getAmount() - 1);

		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				HeadHunting.getInstance().getConfig().getString("messages.boosters.activatemessage")));
		BoosterManager.GetInstance().getActiveboosters().put(p, BoosterManager.GetInstance().ByName(is.getItemMeta().getDisplayName()));
		new BoosterTask(p).runTaskLater(HeadHunting.getInstance(), (long) booster.getDuration() * 20 * 60);
	}
}
