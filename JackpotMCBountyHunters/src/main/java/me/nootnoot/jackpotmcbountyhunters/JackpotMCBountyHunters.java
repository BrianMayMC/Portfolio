package me.nootnoot.jackpotmcbountyhunters;

import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.Getter;
import me.nootnoot.framework.basesystem.BasePlugin;
import me.nootnoot.framework.registrysystem.Initializer;
import me.nootnoot.framework.utils.CurrencyUtils;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmcbountyhunters.commands.BountyCommand;
import me.nootnoot.jackpotmcbountyhunters.files.BountyTopMenuConfig;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.*;

public final class JackpotMCBountyHunters extends BasePlugin {

	@Getter
	private static JackpotMCBountyHunters instance;

	@Getter
	private Economy econ;

	@Getter private BountyTopMenuConfig bountyTopMenuConfig;

	@Override
	public void onEnable() {
		// Plugin startup logic
		super.onEnable();
		instance = this;
		bountyTopMenuConfig = new BountyTopMenuConfig();
		if (!setupEconomy() ) {
			Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		createFiles();
		createCommands();
		new Initializer(this, "me.nootnoot.jackpotmcbountyhunters");
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

	public void createCommands(){
		getCommand("bounty").setExecutor(new BountyCommand());
	}


	public void createFiles(){
		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public List<ItemStack> getTop10Bounties(){
		LinkedHashMap<Double, Player> bounties = new LinkedHashMap<>();
		for(Player p : Bukkit.getOnlinePlayers()){
			double bounty = p.getPersistentDataContainer().getOrDefault(new NamespacedKey(JackpotMCBountyHunters.getInstance(), "bounty"), PersistentDataType.DOUBLE, 0d);
			if(bounty != 0) {
				bounties.put(bounty, p);
			}
		}

		LinkedList<Map.Entry<Double, Player>> entries = new LinkedList<>(bounties.entrySet());
		entries.sort(((o1, o2) -> Double.compare(o2.getKey(), o1.getKey())));

		bounties.clear();
		for(Map.Entry<Double, Player> entry : entries){
			bounties.put(entry.getKey(), entry.getValue());
		}


		List<ItemStack> top10 = new ArrayList<>();
		int i = 0;
		for(double bounty : bounties.keySet()){
			if(i == 13) break;
			ItemStack item = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			meta.setOwningPlayer(Bukkit.getOfflinePlayer(bounties.get(bounty).getUniqueId()));
			if(i == 0){
				meta.setDisplayName(Utils.c("&e#1 (" + bounties.get(bounty).getName() + ")"));
			}else if(i == 1){
				meta.setDisplayName(Utils.c("&6#2 (" + bounties.get(bounty).getName() + ")"));
			}else if(i == 2){
				meta.setDisplayName(Utils.c("&c#3 (" + bounties.get(bounty).getName() + ")"));
			}else{
				meta.setDisplayName(Utils.c("&7#" + i + " (" + bounties.get(bounty).getName() + ")"));
			}
			meta.setLore(Utils.cL(List.of("&7Bounty: $" + CurrencyUtils.prettyMoney(bounty, true, false))));
			item.setItemMeta(meta);
			top10.add(item);
			i++;
		}
		return top10;
	}
}
