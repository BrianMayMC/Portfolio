package me.nootnoot.jackpotmccoinflip.menus;

import me.nootnoot.framework.menusystem.MenuInterface;
import me.nootnoot.framework.menusystem.entities.Slot;
import me.nootnoot.framework.utils.SignGUIUtils;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmccoinflip.JackpotMCCoinflip;
import me.nootnoot.jackpotmccoinflip.entities.Coinflip;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Locale;

public class MainMenu extends MenuInterface {
	public MainMenu() {
		super(Utils.c(JackpotMCCoinflip.getInstance().getMainMenuConfig().getConfig().getString("menu.name")), JackpotMCCoinflip.getInstance().getMainMenuConfig().getConfig().getInt("menu.size"));
	}

	private final FileConfiguration config = JackpotMCCoinflip.getInstance().getMainMenuConfig().getConfig();
	private static int page = 1;

	@Override
	public void define() {
		id = 20;
		setFiller(false);
		int j = 0;
		for(int i = getSlotByPage(page - 1); i < getSlotByPage(page); i++){
			if(j == 26) break;
			if(i >= JackpotMCCoinflip.getInstance().getCoinflipManager().getCoinflips().size()) break;
			Coinflip c = JackpotMCCoinflip.getInstance().getCoinflipManager().getCoinflips().get(i);
			if(c == null) break;
			ItemStack head = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta meta = (SkullMeta) head.getItemMeta();
			meta.setOwningPlayer(Bukkit.getOfflinePlayer(c.getOwner()));
			meta.setDisplayName(Utils.c("&e" + Bukkit.getOfflinePlayer(c.getOwner()).getName()));
			List<String> lore = Utils.cL(List.of("", "&e&lWager", "  &f" + Utils.prettyMoney(c.getBet(), true, false)));
			meta.setLore(lore);
			head.setItemMeta(meta);
			Slot slot = new Slot(i, head);
			slot.setAction(()->{
				if(c.getOwner().equals(getOwner().getUniqueId())){
					JackpotMCCoinflip.getInstance().getCoinflipManager().removeCoinflip(c);
					JackpotMCCoinflip.getInstance().getMenuManager().closeInterface(getOwner());
					List<Player> players = JackpotMCCoinflip.getInstance().getMenuManager().getPlayersInMenu().keySet().stream().toList();
					for(Player p : players){
						MenuInterface menu = JackpotMCCoinflip.getInstance().getMenuManager().getCurrentMenu(p);
						if(menu.getId() == 20){
							JackpotMCCoinflip.getInstance().getMenuManager().closeInterface(p);
							JackpotMCCoinflip.getInstance().getMenuManager().openInterface(p, new MainMenu());
						}
					}
					getOwner().sendMessage(Utils.c("&a&l(!)&a Successfully removed your coinflip."));
					JackpotMCCoinflip.getInstance().getEcon().depositPlayer(getOwner(), c.getBet());
					return;
				}
				if(JackpotMCCoinflip.getInstance().getEcon().getBalance(getOwner()) < c.getBet()){
					getOwner().sendMessage(Utils.c("&C&l(!)&c You do not have enough money for this."));
					return;
				}
				JackpotMCCoinflip.getInstance().getCoinflipManager().removeCoinflip(c);
				List<Player> players = JackpotMCCoinflip.getInstance().getMenuManager().getPlayersInMenu().keySet().stream().toList();
				for(Player p : players){
					MenuInterface menu = JackpotMCCoinflip.getInstance().getMenuManager().getCurrentMenu(p);
					if(menu.getId() == 20){
						JackpotMCCoinflip.getInstance().getMenuManager().closeInterface(p);
						JackpotMCCoinflip.getInstance().getMenuManager().openInterface(p, new MainMenu());
					}
				}
				JackpotMCCoinflip.getInstance().getEcon().withdrawPlayer(getOwner(), c.getBet());
				JackpotMCCoinflip.getInstance().getMenuManager().closeInterface(getOwner());
				JackpotMCCoinflip.getInstance().getCoinflipManager().startCoinflip(c, getOwner().getUniqueId());
			});
			setSlot(slot);
			j++;
		}
		for(String path : config.getConfigurationSection("menu.items.").getKeys(false)){
			ItemStack item = new ItemStack(Material.matchMaterial(config.getString("menu.items." + path + ".material")));
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(Utils.c(config.getString("menu.items." + path + ".name")));
			meta.setLore(Utils.cL(config.getStringList("menu.items." + path + ".lore")));
			item.setItemMeta(meta);
			Slot slot = new Slot(config.getInt("menu.items." + path + ".slot"), item);
			String function = config.getString("menu.items." + path + ".functionality");
			slot.setAction(()->{
				switch(function.toLowerCase(Locale.ROOT)){
					case "previous" -> {
						if(page != 1) {
							page--;
							JackpotMCCoinflip.getInstance().getMenuManager().closeInterface(getOwner());
							JackpotMCCoinflip.getInstance().getMenuManager().openInterface(getOwner(), new MainMenu());
						}
					}
					case "next" -> {
						if(JackpotMCCoinflip.getInstance().getCoinflipManager().getCoinflips().size() - 1 > getSlotByPage(page - 1)) {
							page++;
							JackpotMCCoinflip.getInstance().getMenuManager().closeInterface(getOwner());
							JackpotMCCoinflip.getInstance().getMenuManager().openInterface(getOwner(), new MainMenu());
						}
					}
					case "create" -> {
						if(JackpotMCCoinflip.getInstance().getCoinflipManager().hasCoinflip(getOwner().getUniqueId())){
							getOwner().sendMessage(Utils.c("&c&l(!)&c You already have an active coinflip!"));
							return;
						}
						SignGUIUtils.openGUIFor(getOwner(), "", "", "", "", new SignGUIUtils.SignGUIListener() {
							@Override
							public void onSignDone(Player player, String[] lines) {
								if (lines[0].equalsIgnoreCase("")) {
									player.sendMessage(Utils.c("&c&l(!)&c Please fill in a valid amount."));
									return;
								}

								double bet;
								try {
									bet = Double.parseDouble(lines[0]);
								} catch (NumberFormatException e) {
									player.sendMessage(Utils.c("&c&l(!)&c Please fill in a valid amount."));
									return;
								}

								if(bet > JackpotMCCoinflip.getInstance().getEcon().getBalance(player)){
									player.sendMessage(Utils.c("&c&l(!)&c You do not have enough money for this."));
									return;
								}

								if(bet < JackpotMCCoinflip.getInstance().getConfig().getDouble("min-amount")){
									player.sendMessage(Utils.c("&c&l(!)&c You need at least &l&n$" + Utils.prettyMoney(JackpotMCCoinflip.getInstance().getConfig().getDouble("min-amount"), true, false) + "&c to make a coinflip!"));
									return;
								}

								if(bet > JackpotMCCoinflip.getInstance().getConfig().getDouble("max-amount")){
									player.sendMessage(Utils.c("&c&l(!)&c The maximum amount for a coinflip is &l&n$" + Utils.prettyMoney(JackpotMCCoinflip.getInstance().getConfig().getDouble("max-amount"), true, false) + "&c!"));
									return;
								}

								player.sendMessage(Utils.c("&a&l(!)&a Successfully created coinflip."));
								JackpotMCCoinflip.getInstance().getEcon().withdrawPlayer(player, bet);
								JackpotMCCoinflip.getInstance().getCoinflipManager().createCoinflip(new Coinflip(player.getUniqueId(), bet));
								new BukkitRunnable(){
									@Override
									public void run(){
										slots.clear();
										JackpotMCCoinflip.getInstance().getMenuManager().closeInterface(getOwner());
										JackpotMCCoinflip.getInstance().getMenuManager().openInterface(getOwner(), new MainMenu());
									}
								}.runTask(JackpotMCCoinflip.getInstance());
							}
						});
						SignGUIUtils.registerSignGUIListener(JackpotMCCoinflip.getInstance());
					}
				}
			});
			setSlot(slot);
		}

		ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta meta = filler.getItemMeta();
		meta.setDisplayName(Utils.c("&7"));
		filler.setItemMeta(meta);
		setSlot(new Slot(27, filler));
		setSlot(new Slot(28, filler));
		setSlot(new Slot(29, filler));
		setSlot(new Slot(33, filler));
		setSlot(new Slot(34, filler));
		setSlot(new Slot(35, filler));
	}

	public int getSlotByPage(int page){
		return page * 26;
	}
}
