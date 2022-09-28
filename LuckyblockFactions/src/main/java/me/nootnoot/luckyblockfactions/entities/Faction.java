package me.nootnoot.luckyblockfactions.entities;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.framework.utils.Cuboid;
import me.nootnoot.framework.utils.SimpleLocation;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
public class Faction {
	private final List<FactionPlayer> players;
	private final List<UUID> invites = new ArrayList<>();
	private final List<FactionClaim> areas = new ArrayList<>();
	private transient List<Player> playersInChat;
	private SimpleLocation home;
	private String name;
	private String description = "&7Default Description";
	private int kills;
	private double balance;
	private int rank;
	private boolean open = false;
	private int sizeUpgrade = 0;
	private int powerUpgrade = 0;
	private double power = 1;

	public Faction(FactionPlayer owner, String name) {
		playersInChat = new ArrayList<>();
		this.players = new ArrayList<>();
		players.add(owner);
		this.name = name;
	}

	public void removeArea(Location l, Player player){
		FactionClaim a = null;
		for(FactionClaim c : areas){
			if(Utils.isInside(l, c.getCuboid().getLowerNE(), c.getCuboid().getUpperSW())){
				a = c;
			}
		}
		LuckyblockFactions.getInstance().getEcon().depositPlayer(player, a.getPrice());
		a.getMarker().deleteMarker();
		areas.remove(a);
	}

	public double getPower(){
		return power;
	}

	public int getPlayersOnline(){
		int i = 0;
		for(FactionPlayer p : players){
			if(Bukkit.getPlayer(p.getUuid()) != null){
				i++;
			}
		}
		return i;
	}

	public double getMaxPower(){
		final FileConfiguration config = LuckyblockFactions.getInstance().getUpgradeMenuConfig().getConfig();
		double maxIncrease = LuckyblockFactions.getInstance().getConfig().getDouble("max-power-increase-per-player");
		if(getPowerUpgrade() == 0){
			return Math.min(maxIncrease * getPlayers().size() + 1, LuckyblockFactions.getInstance().getConfig().getDouble("max-power-per-faction"));
		}else{
			return Math.min(maxIncrease * getPlayers().size() + 1, config.getDouble("upgrades.power." + getPowerUpgrade() + ".value"));
		}
	}

	public double getMaxSize(){
		final FileConfiguration config = LuckyblockFactions.getInstance().getUpgradeMenuConfig().getConfig();
		return config.getDouble("upgrades.size." + getPowerUpgrade() + ".value");

	}

	public FactionPlayer getPlayer(Player p){
		for(FactionPlayer f : players){
			if(f.getUuid().equals(p.getUniqueId())){
				return f;
			}
		}
		return null;
	}
	public FactionPlayer getPlayer(UUID uuid){
		for(FactionPlayer f : players){
			if(f.getUuid().equals(uuid)){
				return f;
			}
		}
		return null;
	}

	public String getLeaderString(){
		FactionPlayer leader = null;
		for(FactionPlayer fp : players){
			if(fp.getRole().equals(FactionRole.LEADER)){
				leader = fp;
			}
		}
		OfflinePlayer l = Bukkit.getOfflinePlayer(leader.getUuid());
		if(l.isOnline()){
			return ChatColor.GREEN + l.getName() + ChatColor.GRAY;
		}else{
			return ChatColor.RED + l.getName() + ChatColor.GRAY;
		}
	}

	public String getMembers(){
		StringBuilder names = new StringBuilder();
		for(FactionPlayer player : getPlayers()){
			if(player.getRole() == FactionRole.MEMBER){
				Player p = Bukkit.getPlayer(player.getUuid());
				if(p != null) {
					names.append(ChatColor.GREEN).append(p.getName()).append(ChatColor.GRAY).append("&f, ");
				}else{
					names.append(ChatColor.RED).append(p.getName()).append(ChatColor.GRAY).append("&f, ");
				}
			}
		}
		return names.toString();
	}

	public String getOfficers(){
		StringBuilder names = new StringBuilder();
		for(FactionPlayer player : getPlayers()){
			if(player.getRole() == FactionRole.OFFICER){
				OfflinePlayer p = Bukkit.getOfflinePlayer(player.getUuid());
				if(p.isOnline()) {
					names.append(ChatColor.GREEN).append(p.getName()).append(ChatColor.GRAY).append("&f, ");
				}else{
					names.append(ChatColor.RED).append(p.getName()).append(ChatColor.GRAY).append("&f, ");
				}
			}
		}
		return names.toString();
	}

	public String getCoLeaders(){
		StringBuilder names = new StringBuilder();
		for(FactionPlayer player : getPlayers()){
			if(player.getRole() == FactionRole.CO_LEADER){
				OfflinePlayer p = Bukkit.getOfflinePlayer(player.getUuid());
				if(p.isOnline()) {
					names.append(ChatColor.GREEN).append(p.getName()).append(ChatColor.GRAY).append("&f, ");
				}else{
					names.append(ChatColor.RED).append(p.getName()).append(ChatColor.GRAY).append("&f, ");
				}
			}
		}
		return names.toString();
	}

	public int getAmountOfOnlinePlayers(){
		int i = 0;
		for(FactionPlayer p : players){
			Player pp = Bukkit.getPlayer(p.getUuid());
			if(pp != null){
				i++;
			}
		}
		return i;
	}
}
