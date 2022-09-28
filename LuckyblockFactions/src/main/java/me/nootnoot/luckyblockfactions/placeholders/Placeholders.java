package me.nootnoot.luckyblockfactions.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionRole;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {
	@Override
	public @NotNull String getIdentifier() {
		return "faction";
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
		if(params.equalsIgnoreCase("name")){
			Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(player.getPlayer());
			if(f == null){
				return "N/A";
			}else{
				return f.getName();
			}
		}else if(params.equalsIgnoreCase("power")){
			Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(player.getPlayer());
			if(f == null){
				return "N/A";
			}else{
				return String.valueOf(f.getPower());
			}
		}else if(params.equalsIgnoreCase("balance")){
			Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(player.getPlayer());
			if(f == null){
				return "N/A";
			}else{
				return Utils.formatCurrency(f.getBalance());
			}
		}else if(params.equalsIgnoreCase("role")){
			Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(player.getPlayer());
			if(f == null){
				return "N/A";
			}else{
				return FactionRole.getName(f.getPlayer(player.getPlayer()).getRole());
			}
		}else{
			return "";
		}
	}
}
