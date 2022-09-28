package me.nootnoot.jackpotmcplaytimerewards.menus;

import lombok.Setter;
import me.nootnoot.framework.menusystem.MenuInterface;
import me.nootnoot.framework.menusystem.entities.Slot;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmcplaytimerewards.JackpotMCPlaytimeRewards;
import me.nootnoot.jackpotmcplaytimerewards.entities.Level;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class LevelMenu extends MenuInterface {
	public LevelMenu() {
		super(Utils.c(Utils.getHex(JackpotMCPlaytimeRewards.getInstance().getLevelMenuConfig().getConfig().getString("menu.name"))), JackpotMCPlaytimeRewards.getInstance().getLevelMenuConfig().getConfig().getInt("menu.size"));
	}

	@Setter private static int page = 1;

	@Override
	public void define() {
		setFiller(false);
		int j = 0;
		int pLevel = getOwner().getPersistentDataContainer().getOrDefault(new NamespacedKey(JackpotMCPlaytimeRewards.getInstance(), "level"), PersistentDataType.INTEGER, 0);
		long playTime = getOwner().getPersistentDataContainer().getOrDefault(new NamespacedKey(JackpotMCPlaytimeRewards.getInstance(), "playtime"), PersistentDataType.LONG, 0L);
		for(int i = getSlotByPage(page - 1); i < getSlotByPage(page); i++){
			if(j == 10) break;
			if((i >= JackpotMCPlaytimeRewards.getInstance().getLevelManager().getLevels().size())) break;
			Level level = JackpotMCPlaytimeRewards.getInstance().getLevelManager().getLevels().get(i);
			if(level == null) break;
			if(pLevel >= level.getLevel()){
				Slot slot = new Slot(level.getMenuSlot(), level.getClaimed());
				setSlot(slot);
			}else if(pLevel + 1 == level.getLevel()){
				Slot slot;
				if(level.getRequirementInHours() * 60 * 60 <= playTime) {
					slot = new Slot(level.getMenuSlot(), level.getLocked());
					slot.setAction(() -> {
						JackpotMCPlaytimeRewards.getInstance().getLevelManager().claimLevel(level, getOwner());
						JackpotMCPlaytimeRewards.getInstance().getMenuManager().closeInterface(getOwner());
					});
				}else{
					slot = new Slot(level.getMenuSlot(), level.getLocked());
				}
				setSlot(slot);
			}else{
				Slot slot = new Slot(level.getMenuSlot(), level.getLocked());
				setSlot(slot);
			}
			j++;
		}

		final FileConfiguration config = JackpotMCPlaytimeRewards.getInstance().getLevelConfig().getConfig();

		Material cMaterial = Material.matchMaterial(config.getString("clock.material"));
		ItemStack clock;
		if(cMaterial == Material.PLAYER_HEAD){
			clock = Utils.getSkull(config.getString("clock.url"), 1, false);
		}else {
			clock = new ItemStack(cMaterial);
		}
		ItemMeta clockMeta = clock.getItemMeta();
		clockMeta.setDisplayName(Utils.c(config.getString("clock.name")));
		List<String> clockLore = new ArrayList<>();
		for(String s : config.getStringList("clock.lore")){
			clockLore.add(Utils.c(s.replace("%level%", String.valueOf(pLevel)).replace("%playtime%", Utils.formatPlaytime(playTime))));
		}
		clockMeta.setLore(clockLore);
		clock.setItemMeta(clockMeta);
		setSlot(new Slot(config.getInt("clock.slot"), clock));

		Material nMaterial = Material.matchMaterial(config.getString("next-page.material"));
		ItemStack next;
		if(cMaterial == Material.PLAYER_HEAD){
			next = Utils.getSkull(config.getString("next-page.url"), 1, false);
		}else {
			next = new ItemStack(nMaterial);
		}
		ItemMeta nextMeta = next.getItemMeta();
		nextMeta.setDisplayName(Utils.c(config.getString("next-page.name")));
		nextMeta.setLore(Utils.cL(config.getStringList("next-page.lore")));
		next.setItemMeta(nextMeta);
		Slot slot = new Slot(config.getInt("next-page.slot"), next);
		slot.setAction(()->{
			if(JackpotMCPlaytimeRewards.getInstance().getLevelManager().getLevels().size() - 1 > getSlotByPage(page - 1)) {
				page++;
				JackpotMCPlaytimeRewards.getInstance().getMenuManager().closeInterface(getOwner());
				JackpotMCPlaytimeRewards.getInstance().getMenuManager().openInterface(getOwner(), new LevelMenu());
			}
		});
		setSlot(slot);

		Material pMaterial = Material.matchMaterial(config.getString("previous-page.material"));
		ItemStack previous;
		if(pMaterial == Material.PLAYER_HEAD){
			previous = Utils.getSkull(config.getString("previous-page.url"), 1, false);
		}else {
			previous = new ItemStack(pMaterial);
		}
		ItemMeta previousMeta = previous.getItemMeta();
		previousMeta.setDisplayName(Utils.c(config.getString("previous-page.name")));
		previousMeta.setLore(Utils.cL(config.getStringList("previous-page.lore")));
		previous.setItemMeta(previousMeta);
		Slot slot2 = new Slot(config.getInt("previous-page.slot"), previous);
		slot2.setAction(()->{
			if(page > 1) {
				page--;
				JackpotMCPlaytimeRewards.getInstance().getMenuManager().closeInterface(getOwner());
				JackpotMCPlaytimeRewards.getInstance().getMenuManager().openInterface(getOwner(), new LevelMenu());
			}
		});
		setSlot(slot2);
	}

	public int getSlotByPage(int page){
		return page * 10;
	}
}
