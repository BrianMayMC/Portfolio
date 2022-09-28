package me.nootnoot.jackpotmccoinflip.menus;

import it.unimi.dsi.fastutil.longs.LongReferencePair;
import me.nootnoot.framework.menusystem.MenuInterface;
import me.nootnoot.framework.menusystem.entities.Slot;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmccoinflip.JackpotMCCoinflip;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ActiveMenu extends MenuInterface {

	private final ItemStack head;
	private final ItemStack filler;
	public ActiveMenu(ItemStack head, ItemStack filler) {
		super(Utils.c("&8SPINNING!"), 45);
		this.head = head;
		this.filler = filler;
	}

	@Override
	public void define() {
		id = 20;
		ItemStack leave = new ItemStack(Material.BARRIER);
		ItemMeta meta = leave.getItemMeta();
		meta.setDisplayName(Utils.c("&c&l(!)&c Leave Coinflip"));
		meta.setLore(Utils.cL(List.of("&7Click here to leave the coinflip.", "&7You will still get the money if you win.")));
		leave.setItemMeta(meta);
		Slot s = new Slot(40, leave);
		s.setAction(()->{
			e.getWhoClicked().getPersistentDataContainer().set(new NamespacedKey(JackpotMCCoinflip.getInstance(), "leave"), PersistentDataType.STRING, "yes");
			JackpotMCCoinflip.getInstance().getMenuManager().closeInterface((Player)e.getWhoClicked());
		});
		setSlot(s);
		setSlot(new Slot(22, head));
		setFillerItem(filler);

	}
}
