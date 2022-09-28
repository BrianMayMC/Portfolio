package me.nootnoot.jackpotmccrashgambling.menus;

import me.nootnoot.framework.menusystem.MenuInterface;
import me.nootnoot.framework.menusystem.entities.Slot;
import me.nootnoot.framework.utils.SignGUIUtils;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmccrashgambling.JackpotMCCrashGambling;
import me.nootnoot.jackpotmccrashgambling.entities.CrashGame;
import me.nootnoot.jackpotmccrashgambling.entities.Result;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureTwistingVines;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainMenu extends MenuInterface {
	public MainMenu() {
		super(Utils.c(JackpotMCCrashGambling.getInstance().getMainMenuConfig().getConfig().getString("menu.name")), JackpotMCCrashGambling.getInstance().getMainMenuConfig().getConfig().getInt("menu.size"));
	}

	private final FileConfiguration config = JackpotMCCrashGambling.getInstance().getMainMenuConfig().getConfig();

	@Override
	public void define() {
		for(String path : config.getConfigurationSection("menu.items.").getKeys(false)){
			ItemStack item = new ItemStack(Material.matchMaterial(config.getString("menu.items." + path + ".material")));
			ItemMeta meta = item.getItemMeta();
			String functionality = config.getString("menu.items." + path + ".functionality");
			meta.setDisplayName(Utils.c(config.getString("menu.items." + path + ".name")));
			if(!(functionality.equalsIgnoreCase("previous"))) {
				meta.setLore(Utils.cL(config.getStringList("menu.items." + path + ".lore")));
			}else{
				List<String> lore = new ArrayList<>();
				int i = 1;
				NumberFormat format = new DecimalFormat("#0.00");
				for(CrashGame game : JackpotMCCrashGambling.getInstance().getCrashManager().getGames(getOwner().getUniqueId())){
					lore.add(Utils.c("&7" + i + ") &ex" + format.format(game.getTempIncrease()) + " &7| " + Result.getNiceName(game.getResult())));
					i++;
					if(i == 6){
						break;
					}
				}
				meta.setLore(lore);
			}
			item.setItemMeta(meta);
			Slot slot = new Slot(config.getInt("menu.items." + path + ".slot"), item);
			slot.setAction(()->{
				switch(functionality.toLowerCase(Locale.ROOT)){
					case "close" -> {
						JackpotMCCrashGambling.getInstance().getMenuManager().closeInterface(getOwner());
					}
					case "bet" -> {
						SignGUIUtils.openGUIFor(getOwner(), "", "", "", "", new SignGUIUtils.SignGUIListener() {
							@Override
							public void onSignDone(Player player, String[] lines) {
								if (lines[0].equalsIgnoreCase("")) {
									player.sendMessage(Utils.c("&c&l(!)&c Please fill in a valid amount."));
									return;
								}

								double amount;
								try {
									amount = Double.parseDouble(lines[0]);
								} catch (NumberFormatException e) {
									player.sendMessage(Utils.c("&c&l(!)&c Please fill in a valid amount."));
									return;
								}

								if(amount > JackpotMCCrashGambling.getInstance().getConfig().getDouble("max-money")){
									player.sendMessage(Utils.c("&c&l(!)&c You can not bet more than $1M"));
									return;
								}
								if(amount > JackpotMCCrashGambling.getInstance().getEcon().getBalance(player)){
									player.sendMessage(Utils.c("&c&l(!)&c You do not have enough money for this"));
									return;
								}

								player.sendMessage(Utils.c("&a&l(!)&a You have successfully set your bet to $" + Utils.formatCurrency(amount)));
								new BukkitRunnable(){
									@Override
									public void run(){
										CrashGame game = new CrashGame(getOwner().getUniqueId(), amount);
										JackpotMCCrashGambling.getInstance().getEcon().withdrawPlayer(getOwner(), amount);
										JackpotMCCrashGambling.getInstance().getCrashManager().startGame(game, player);
										JackpotMCCrashGambling.getInstance().getCrashManager().registerGame(game);
									}
								}.runTask(JackpotMCCrashGambling.getInstance());
							}
						});
						SignGUIUtils.registerSignGUIListener(JackpotMCCrashGambling.getInstance());
					}

				}
			});
			setSlot(slot);
		}
	}
}
