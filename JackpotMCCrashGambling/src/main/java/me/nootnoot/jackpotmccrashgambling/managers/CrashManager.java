package me.nootnoot.jackpotmccrashgambling.managers;

import com.google.common.collect.Sets;
import lombok.Getter;
import me.nootnoot.framework.menusystem.entities.Slot;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmccrashgambling.JackpotMCCrashGambling;
import me.nootnoot.jackpotmccrashgambling.entities.CrashGame;
import me.nootnoot.jackpotmccrashgambling.entities.Result;
import me.nootnoot.jackpotmccrashgambling.menus.ActiveMenu;
import me.nootnoot.jackpotmccrashgambling.storage.CrashStorage;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CrashManager {

	@Getter
	private final List<CrashGame> crashGames;

	private final CrashStorage db;

	public CrashManager(CrashStorage crashStorage){
		crashGames = crashStorage.getGames();
		this.db = crashStorage;
	}

	public void registerGame(CrashGame game){
		crashGames.add(game);
		db.addGame(game);
	}

	public List<CrashGame> getGames(UUID p){
		List<CrashGame> games = new ArrayList<>();
		for(CrashGame g : crashGames){
			if(g.getPlayer().equals(p)){
				games.add(g);
			}
		}
		return games;
	}

	public void startGame(CrashGame crashGame, Player p){
		int amountToTick = ThreadLocalRandom.current().nextInt(JackpotMCCrashGambling.getInstance().getConfig().getInt("amount"));
		Map<Integer, ItemStack> items = getItems(crashGame);
		final int[] indexOfLast = {38};
		final boolean[] first = {true};
		NumberFormat format = new DecimalFormat("#0.00");

		final int[] done = {0};
		new BukkitRunnable(){
			@Override
			public void run(){
				double increase = ThreadLocalRandom.current().nextDouble(JackpotMCCrashGambling.getInstance().getConfig().getDouble("min-increase"), JackpotMCCrashGambling.getInstance().getConfig().getDouble("max-increase"));
				ItemStack item = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
				if(done[0] == amountToTick){
					item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
					item.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
					item.getItemMeta().addEnchant(Enchantment.ARROW_FIRE, 1, true);
				}
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(Utils.c("&ex" + (format.format(crashGame.getTempIncrease() + increase))));
				item.setItemMeta(meta);
				int index = indexOfNextItem(crashGame.getTempIncrease(), increase, indexOfLast[0], first[0]);
				if(checkForClear(indexOfLast[0])){
					items.clear();
				}
				ItemStack sign = new ItemStack(Material.FIREWORK_ROCKET);
				ItemMeta signMeta = sign.getItemMeta();
				if(crashGame.getChosenIncrease() == 0) {
					signMeta.setDisplayName(Utils.c("&6&lStatus:"));
					signMeta.setLore(Utils.cL(List.of("&eCurrent increase: &ax" + format.format(crashGame.getTempIncrease()), "", "&eYour bet: &a$" + Utils.formatCurrency(crashGame.getBet()), "", "&eTotal: &a$" +
							Utils.formatCurrency(crashGame.getBet() * crashGame.getTempIncrease()), "", "&c&l(Left-Click) me to pull out!")));
					sign.setItemMeta(signMeta);
				}else{
					signMeta.setDisplayName(Utils.c("&6&lStatus:"));
					signMeta.setLore(Utils.cL(List.of("&eChosen increase: &ax" + format.format(crashGame.getChosenIncrease()), "", "&eYour bet: &a$" + Utils.formatCurrency(crashGame.getBet()), "", "&eTotal: &a$" +
							Utils.formatCurrency(crashGame.getBet() * crashGame.getChosenIncrease()), "", "&c&lYou've pulled out!")));
					sign.setItemMeta(signMeta);
				}
				if(done[0] == amountToTick){
					signMeta.setDisplayName(Utils.c("&6&lStatus:"));
					if(crashGame.getResult() == Result.WINNER) {
						signMeta.setLore(Utils.cL(List.of("&eChosen increase: &ax" + format.format(crashGame.getChosenIncrease()), "", "&eYour bet: &a$" + Utils.formatCurrency(crashGame.getBet()), "", "&eTotal: &a$" +
								Utils.formatCurrency(crashGame.getBet() * crashGame.getChosenIncrease()), "", "&c&lYou won &a$" + Utils.formatCurrency(crashGame.getBet() * crashGame.getChosenIncrease()) + "&c&l!!!")));
					}else{
						signMeta.setLore(Utils.cL(List.of("&eChosen increase: &ax" + format.format(crashGame.getChosenIncrease()), "", "&eYour bet: &a$" + Utils.formatCurrency(crashGame.getBet()), "", "&eTotal: &a$" +
								Utils.formatCurrency(crashGame.getBet() * crashGame.getChosenIncrease()), "", "&c&lYou Lost!!!")));
					}
					sign.setItemMeta(signMeta);
				}

				items.put(index, item);
				first[0] = false;
				indexOfLast[0] = index;
				crashGame.setTempIncrease(crashGame.getTempIncrease() + increase);
				if(done[0] == amountToTick){
					JackpotMCCrashGambling.getInstance().getMenuManager().openInterface(p, new ActiveMenu(items, sign, item, crashGame, true, crashGame.isPulledOut()));
				}else{
					ActiveMenu menu = new ActiveMenu(items, sign, item, crashGame, false, crashGame.isPulledOut());
					JackpotMCCrashGambling.getInstance().getMenuManager().openInterface(p, menu);

				}

				p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
				if(done[0] == amountToTick){
					if(crashGame.getResult() == Result.WINNER) {
						JackpotMCCrashGambling.getInstance().getEcon().depositPlayer(p, crashGame.getChosenIncrease() * crashGame.getBet());
					}else{
						crashGame.setResult(Result.LOSER);
					}
					cancel();
				}
				done[0]++;
			}
		}.runTaskTimer(JackpotMCCrashGambling.getInstance(), 1L, JackpotMCCrashGambling.getInstance().getConfig().getLong("increase-delay"));
	}

	public boolean checkForClear(int i) {
		return i == 11 || i == 12 || i == 13 || i == 14 || i == 15 || i == 24 || i == 33 || i == 42;
	}

	public int indexOfNextItem(double current, double increase, int i, boolean first){
		if(first){
			return 38;
		}
		if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15 || i == 24 || i == 33 || i == 42){
			return 38;
		}
		if(increase < JackpotMCCrashGambling.getInstance().getConfig().getDouble("increment-panel")){
			return i + 1;
		}else{
			return i - 8;
		}
	}

	public Map<Integer, ItemStack> getItems(CrashGame game){
		Map<Integer, ItemStack> items = new HashMap<>();
		ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.c("&7"));
		item.setItemMeta(meta);
		ItemStack sign = new ItemStack(Material.OAK_SIGN);
		ItemMeta signMeta = sign.getItemMeta();
		signMeta.setDisplayName(Utils.c("&6&lStatus:"));
		signMeta.setLore(Utils.cL(List.of("&eCurrent increase: &ax" + game.getTempIncrease(), "", "&eYour bet: &a$" + Utils.formatCurrency(game.getBet()), "", "&eTotal: &a$" +
				Utils.formatCurrency(game.getBet() * game.getTempIncrease()), "", "&c&l(Left-Click) me to pull out!")));
		sign.setItemMeta(signMeta);

		for(int i = 0; i < 9; i++) {
			items.put(i, item);
		}
		items.put(9, item);
		items.put(17, item);
		items.put(18, item);
		items.put(26, item);
		items.put(27, item);
		items.put(35, item);
		items.put(36, item);
		items.put(44, item);
		for(int i = 45; i < 54; i++) {
			items.put(i, item);
		}
		return items;
	}
}
