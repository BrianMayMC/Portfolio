package me.nootnoot.jackpotmcdailyrewards.menus;

import me.nootnoot.framework.menusystem.MenuInterface;
import me.nootnoot.framework.menusystem.entities.Slot;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmcdailyrewards.JackpotMCDailyRewards;
import me.nootnoot.jackpotmcdailyrewards.entities.SimpleItem;

public class ItemRemoveMenu extends MenuInterface {
	public ItemRemoveMenu() {
		super(Utils.c("&7Rewards Remove Menu"), 54);
	}

	@Override
	public void define() {
		int i = 0;
		for(SimpleItem item : JackpotMCDailyRewards.getInstance().getRewardsManager().getMItems()){
			if(i == 54) break;
			Slot slot = new Slot(i, item.getItem());
			slot.setAction(()->{
				JackpotMCDailyRewards.getInstance().getRewardsManager().removeItem(item);
				JackpotMCDailyRewards.getInstance().getMenuManager().closeInterface(getOwner());
				JackpotMCDailyRewards.getInstance().getMenuManager().openInterface(getOwner(), new ItemRemoveMenu());
			});
			setSlot(slot);
			i++;
		}
	}
}
