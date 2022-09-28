package me.nootnoot.jackpotmccoinflip.managers;

import lombok.Getter;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmccoinflip.JackpotMCCoinflip;
import me.nootnoot.jackpotmccoinflip.entities.Coinflip;
import me.nootnoot.jackpotmccoinflip.menus.ActiveMenu;
import me.nootnoot.jackpotmccoinflip.storage.CoinflipStorage;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.w3c.dom.NamedNodeMap;

import java.util.List;
import java.util.NavigableMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CoinflipManager {

	@Getter private final List<Coinflip> coinflips;

	private final CoinflipStorage db;

	public CoinflipManager(CoinflipStorage db){
		this.db = db;
		this.coinflips = db.get();
	}

	public void createCoinflip(Coinflip coinflip){
		coinflips.add(coinflip);
		db.addCoinflip(coinflip);
	}

	public void removeCoinflip(Coinflip coinflip){
		coinflips.remove(coinflip);
		db.removeCoinflip(coinflip);
	}

	public boolean hasCoinflip(UUID uuid){
		for(Coinflip c : coinflips){
			if(c.getOwner().equals(uuid)){
				return true;
			}
		}
		return false;
	}

	public void startCoinflip(Coinflip coinflip, UUID player){
		final long[] speed = {JackpotMCCoinflip.getInstance().getConfig().getLong("tick-speed")};
		coinflip.setPlayer(player);
		String amountString = JackpotMCCoinflip.getInstance().getConfig().getString("tick-amount");
		String[] split = amountString.split("-");
		int amount = ThreadLocalRandom.current().nextInt(Integer.parseInt(split[0]), Integer.parseInt(split[1]) + 1);
		int[] done = {0};
		Player p = Bukkit.getPlayer(player);
		OfflinePlayer owner = Bukkit.getOfflinePlayer(coinflip.getOwner());
		new BukkitRunnable() {
			@Override
			public void run() {
				ItemStack head = new ItemStack(Material.PLAYER_HEAD);
				SkullMeta meta = (SkullMeta) head.getItemMeta();
				OfflinePlayer next = Bukkit.getOfflinePlayer(getNextHeadOwner(coinflip, done[0]));
				meta.setOwningPlayer(next);
				meta.setDisplayName(Utils.c("&e" + next.getName()));
				head.setItemMeta(meta);

				if(!(p.getPersistentDataContainer().has(new NamespacedKey(JackpotMCCoinflip.getInstance(), "leave"), PersistentDataType.STRING))) {
					JackpotMCCoinflip.getInstance().getMenuManager().openInterface(p, new ActiveMenu(head, getNextFiller(coinflip, getNextHeadOwner(coinflip, done[0]))));
					p.playSound(p.getLocation(), Sound.valueOf(JackpotMCCoinflip.getInstance().getConfig().getString("sound")), 1f, 1f);
				}
				if (owner.isOnline()) {
					if(!(owner.getPlayer().getPersistentDataContainer().has(new NamespacedKey(JackpotMCCoinflip.getInstance(), "leave"), PersistentDataType.STRING))) {
						JackpotMCCoinflip.getInstance().getMenuManager().openInterface(owner.getPlayer(), new ActiveMenu(head, getNextFiller(coinflip, getNextHeadOwner(coinflip, done[0]))));
						owner.getPlayer().playSound(owner.getPlayer().getLocation(), Sound.valueOf(JackpotMCCoinflip.getInstance().getConfig().getString("sound")), 1f, 1f);
					}
				}
				if (done[0] == amount) {
					UUID winner;
					if (amount % 2 == 0) {
						winner = coinflip.getOwner();
					} else {
						winner = coinflip.getPlayer();
					}
					JackpotMCCoinflip.getInstance().getEcon().depositPlayer(Bukkit.getOfflinePlayer(winner), coinflip.getBet() * 2);

					ItemStack filler = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
					ItemMeta fMeta = filler.getItemMeta();
					fMeta.setDisplayName(Utils.c("&7"));
					filler.setItemMeta(fMeta);

					JackpotMCCoinflip.getInstance().getMenuManager().openInterface(p, new ActiveMenu(head, filler));
					p.playSound(p.getLocation(), Sound.valueOf(JackpotMCCoinflip.getInstance().getConfig().getString("sound")), 1f, 1f);
					if (owner.isOnline()) {
						JackpotMCCoinflip.getInstance().getMenuManager().openInterface(owner.getPlayer(), new ActiveMenu(head, filler));
						owner.getPlayer().playSound(owner.getPlayer().getLocation(), Sound.valueOf(JackpotMCCoinflip.getInstance().getConfig().getString("sound")), 1f, 1f);
					}

					if (winner.equals(coinflip.getOwner())) {
						if (Bukkit.getOfflinePlayer(coinflip.getPlayer()).isOnline()) {
							Bukkit.getOfflinePlayer(coinflip.getPlayer()).getPlayer().sendMessage(Utils.c("&c&l(!)&c You lost $" + Utils.prettyMoney(coinflip.getBet(), true, false) + "!"));
						}
						if (Bukkit.getOfflinePlayer(coinflip.getOwner()).isOnline()) {
							Bukkit.getOfflinePlayer(coinflip.getOwner()).getPlayer().sendMessage(Utils.c("&a&l(!)&a You won $" +  Utils.prettyMoney(coinflip.getBet(), true, false) + "!"));
						}
					} else {
						if (Bukkit.getOfflinePlayer(coinflip.getPlayer()).isOnline()) {
							Bukkit.getOfflinePlayer(coinflip.getPlayer()).getPlayer().sendMessage(Utils.c("&a&l(!)&a You won $" +  Utils.prettyMoney(coinflip.getBet(), true, false) + "!"));
						}
						if (Bukkit.getOfflinePlayer(coinflip.getOwner()).isOnline()) {
							Bukkit.getOfflinePlayer(coinflip.getOwner()).getPlayer().sendMessage(Utils.c("&c&l(!)&c You lost $" + Utils.prettyMoney(coinflip.getBet(), true, false) + "!"));
						}
					}
					cancel();
					new BukkitRunnable() {
						@Override
						public void run() {
							p.getPersistentDataContainer().remove(new NamespacedKey(JackpotMCCoinflip.getInstance(), "leave"));
							JackpotMCCoinflip.getInstance().getMenuManager().closeInterface(p);
							if (owner.isOnline()) {
								owner.getPlayer().getPersistentDataContainer().remove(new NamespacedKey(JackpotMCCoinflip.getInstance(), "leave"));
								JackpotMCCoinflip.getInstance().getMenuManager().closeInterface(owner.getPlayer());
							}
						}
					}.runTaskLater(JackpotMCCoinflip.getInstance(), 60L);
				}
				done[0]++;
				speed[0] = decreaseSpeed(done[0], speed[0]);
			}
		}.runTaskTimer(JackpotMCCoinflip.getInstance(), speed[0], speed[0]);
	}

	public UUID getNextHeadOwner(Coinflip coinflip, int tickAmount){
		if(tickAmount % 2 == 0){
			return coinflip.getOwner();
		}
		return coinflip.getPlayer();
	}

	public long decreaseSpeed(int amountOfTimesCompleted, long speed){
		if(amountOfTimesCompleted % 5 == 0){
			return speed + 20;
		}
		return speed;
	}

	public ItemStack getNextFiller(Coinflip c, UUID next){
		ItemStack item;
		if(c.getOwner().equals(next)){
			item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		}else{
			item = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
		}
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.c("&7"));
		item.setItemMeta(meta);
		return item;
	}
}
