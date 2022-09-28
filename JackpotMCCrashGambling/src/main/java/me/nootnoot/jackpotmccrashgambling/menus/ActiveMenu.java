package me.nootnoot.jackpotmccrashgambling.menus;

import me.nootnoot.framework.menusystem.MenuInterface;
import me.nootnoot.framework.menusystem.entities.Slot;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmccrashgambling.entities.CrashGame;
import me.nootnoot.jackpotmccrashgambling.entities.Result;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class ActiveMenu extends MenuInterface {

	private final Map<Integer, ItemStack> items;
	private final ItemStack sign;
	private final ItemStack last;
	private final CrashGame game;
	private final boolean lastTick;
	private final boolean pulledOut;

	public ActiveMenu(Map<Integer, ItemStack> items, ItemStack sign, ItemStack last, CrashGame game, boolean lastTick, boolean pulledOut) {
		super(Utils.c("&7Active Bet"), 54);
		this.items = items;
		this.sign = sign;
		this.last = last;
		this.game = game;
		this.lastTick = lastTick;
		this.pulledOut = pulledOut;
	}

	@Override
	public void define() {
		Slot slot = new Slot(49, sign);
		slot.setAction(()->{
			if(pulledOut){
				getOwner().sendMessage(Utils.c("&c&l(!)&c You have already pulled out!"));
				return;
			}
			if(lastTick){
				getOwner().sendMessage(Utils.c("&c&l(!)&c The game is already over! You lost!"));
				return;
			}
			last.setType(Material.YELLOW_STAINED_GLASS_PANE);
			ItemMeta meta = last.getItemMeta();
			meta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			last.setItemMeta(meta);
			game.setChosenIncrease(game.getTempIncrease());
			game.setResult(Result.WINNER);
			game.setPulledOut(true);
		});
		setSlot(slot);
		for(Integer index : items.keySet()){
			if(index != 49) {
				setSlot(new Slot(index, items.get(index)));
			}
		}
	}

	public void refresh(Player p){
		slots.clear();
		for(Integer index : items.keySet()){
			setSlot(new Slot(index, items.get(index)));
		}
		Slot slot = new Slot(49, sign);
		setSlot(slot);
		slot.setAction(()->{
			if(last.getType() == Material.YELLOW_STAINED_GLASS_PANE){
				p.sendMessage(Utils.c("&c&l(!)&c You have already pulled out!"));
				return;
			}
			if(lastTick){
				p.sendMessage(Utils.c("&c&l(!)&c The game is already over! You lost!"));
				return;
			}
			last.setType(Material.YELLOW_STAINED_GLASS_PANE);
			ItemMeta meta = last.getItemMeta();
			meta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			last.setItemMeta(meta);
			game.setChosenIncrease(game.getTempIncrease());
			game.setResult(Result.WINNER);
		});
		inventory = loadInventory();
		p.updateInventory();
	}
}
