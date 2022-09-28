package me.nootnoot.jackpotmcdailyrewards.menus;

import me.nootnoot.framework.menusystem.MenuInterface;
import me.nootnoot.framework.menusystem.entities.Slot;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmcdailyrewards.JackpotMCDailyRewards;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class SpinMenu extends MenuInterface {

	private final boolean bellB;
	private final Map<Integer, ItemStack> items;

	public SpinMenu(boolean bell, Map<Integer, ItemStack> items) {
		super(Utils.c("&7SPINNING!"), 54);
		this.bellB = bell;
		this.items = items;
	}

	@Override
	public void define() {
		ItemStack bell = new ItemStack(Material.BELL);
		ItemMeta meta = bell.getItemMeta();
		meta.setDisplayName(Utils.c("&6Click here to spin!"));
		bell.setItemMeta(meta);

		Slot slot = new Slot(4, bell);
		if(bellB){
			slot.setAction(()->{
				JackpotMCDailyRewards.getInstance().getRewardsManager().startSpin(getOwner());
			});
		}
		setSlot(slot);

		for(int i : items.keySet()){
			setSlot(new Slot(i, items.get(i)));
		}

		this.setFiller(false);

		ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta m = filler.getItemMeta();
		m.setDisplayName(Utils.c("&7"));
		filler.setItemMeta(m);

		setSlot(new Slot(0, filler));
		setSlot(new Slot(1, filler));
		setSlot(new Slot(2, filler));
		setSlot(new Slot(3, filler));
		setSlot(new Slot(5, filler));
		setSlot(new Slot(6, filler));
		setSlot(new Slot(7, filler));
		setSlot(new Slot(8, filler));
		setSlot(new Slot(9, filler));
		setSlot(new Slot(10, filler));
		setSlot(new Slot(16, filler));
		setSlot(new Slot(17, filler));
		setSlot(new Slot(18, filler));
		setSlot(new Slot(19, filler));
		setSlot(new Slot(21, filler));
		setSlot(new Slot(22, filler));
		setSlot(new Slot(23, filler));
		setSlot(new Slot(25, filler));
		setSlot(new Slot(26, filler));
		setSlot(new Slot(27, filler));
		setSlot(new Slot(28, filler));
		setSlot(new Slot(30, filler));
		setSlot(new Slot(31, filler));
		setSlot(new Slot(32, filler));
		setSlot(new Slot(34, filler));
		setSlot(new Slot(35, filler));
		setSlot(new Slot(36, filler));
		setSlot(new Slot(37, filler));
		setSlot(new Slot(43, filler));
		setSlot(new Slot(44, filler));
		setSlot(new Slot(45, filler));
		setSlot(new Slot(46, filler));
		setSlot(new Slot(47, filler));
		setSlot(new Slot(48, filler));
		setSlot(new Slot(49, filler));
		setSlot(new Slot(50, filler));
		setSlot(new Slot(51, filler));
		setSlot(new Slot(52, filler));
		setSlot(new Slot(53, filler));

	}
}
