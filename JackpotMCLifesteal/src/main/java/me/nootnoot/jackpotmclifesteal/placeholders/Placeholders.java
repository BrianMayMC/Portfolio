package me.nootnoot.jackpotmclifesteal.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nootnoot.jackpotmclifesteal.JackpotMCLifesteal;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class Placeholders extends PlaceholderExpansion {
	@Override
	public @NotNull String getIdentifier() {
		return "lifesteal";
	}

	@Override
	public @NotNull String getAuthor() {
		return "nootnoot";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0.0";
	}
	@Override
	public String onRequest(OfflinePlayer player, String params) {
		if(params.equalsIgnoreCase("hearts")){
			DecimalFormat format = new DecimalFormat("0.#");
			return format.format(player.getPlayer().getPersistentDataContainer().getOrDefault(new NamespacedKey(JackpotMCLifesteal.getInstance(), "hearts"), PersistentDataType.DOUBLE, 20D) / 2);
		}
		return null;
	}
}
