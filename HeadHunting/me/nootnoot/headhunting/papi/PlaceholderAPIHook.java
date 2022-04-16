package me.nootnoot.headhunting.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.managers.HHPlayerManager;
import nootnoot.spigotlib.StringUtils;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;

public class PlaceholderAPIHook extends PlaceholderExpansion {
	@Override
	public @NotNull String getIdentifier() {
		return "headhunting";
	}

	@Override
	public @NotNull String getAuthor() {
		return HeadHunting.getInstance().getDescription().getAuthors().get(0);
	}

	@Override
	public @NotNull String getVersion() {
		return HeadHunting.getInstance().getDescription().getVersion();
	}

	@Override
	public String onRequest(OfflinePlayer player, String params){
		if(params.equalsIgnoreCase("souls")){
			return format(HHPlayerManager.getInstance().getHHPlayer(player.getPlayer()).getSouls());
		}
		return null;
	}

	public static String format(int c) {
		return NumberFormat.getNumberInstance(Locale.US).format(c);
	}
}
