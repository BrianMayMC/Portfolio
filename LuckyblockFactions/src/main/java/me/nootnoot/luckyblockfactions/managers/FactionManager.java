package me.nootnoot.luckyblockfactions.managers;

import lombok.Getter;
import me.nootnoot.framework.utils.Cuboid;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionClaim;
import me.nootnoot.luckyblockfactions.entities.FactionPlayer;
import me.nootnoot.luckyblockfactions.storage.FactionStorage;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.dynmap.markers.AreaMarker;

import java.util.*;

public class FactionManager {

	@Getter private final List<Faction> factions;

	@Getter private List<Faction> factionsOrderedKills = new ArrayList<>();
	@Getter private List<Faction> factionsOrderedOnlinePlayers = new ArrayList<>();
	@Getter private final List<Player> playerTeleporting = new ArrayList<>();

	@Getter private final Map<Faction, Long> frozenFactions = new HashMap<>();

	private final FactionStorage db;

	public FactionManager(FactionStorage db) {
		this.db = db;
		factions = db.get();


		new BukkitRunnable(){
			@Override
			public void run(){
				for(Faction f : factions){
					for(FactionClaim fc : f.getAreas()){
						Cuboid c = fc.getCuboid();
						AreaMarker marker = LuckyblockFactions.getInstance().getMarker().createAreaMarker(UUID.randomUUID().toString(), f.getName(), true, c.getWorld().getName(), new double[]{c.getUpperSW().getX(), c.getLowerNE().getX()}, new double[]{c.getUpperSW().getZ(), c.getLowerNE().getZ()}, false);
						fc.setMarker(marker);
					}
				}
			}
		}.runTaskLater(LuckyblockFactions.getInstance(), 200L);

	}

	public void clear(){
		factions.clear();
		playerTeleporting.clear();
		factionsOrderedKills.clear();
		factionsOrderedOnlinePlayers.clear();
		frozenFactions.clear();
	}

	public Faction getFaction(Player p){
		for(Faction f : factions){
			for(FactionPlayer fp : f.getPlayers()){
				if(fp.getUuid().equals(p.getUniqueId())){
					return f;
				}
			}
		}
		return null;
	}

	public Faction getFactionByPlayerName(String playerName){
		for(Faction f : factions){
			for(FactionPlayer fp : f.getPlayers()){
				if(fp.getPlayerName().equalsIgnoreCase(playerName)){
					return f;
				}
			}
		}
		return null;
	}

	public Faction getFactionByName(String factionName){
		for(Faction f : factions){
			if(f.getName().equalsIgnoreCase(factionName)){
				return f;
			}
		}
		return null;
	}

	public Faction getFaction(UUID uuid){
		for(Faction f : factions){
			for(FactionPlayer fp : f.getPlayers()){
				if(fp.getUuid().equals(uuid)){
					return f;
				}
			}
		}
		return null;
	}

	public Faction getFaction(Location location){
		for(Faction f : factions){
			for(FactionClaim c : f.getAreas()){
				if(Utils.isInside(location, c.getCuboid().getLowerNE(), c.getCuboid().getUpperSW())){
					return f;
				}
			}
		}
		return null;
	}

	public void createFaction(Faction faction){
		factions.add(faction);
		db.createFaction(faction);
	}

	public void disbandFaction(Faction faction){
		factions.remove(faction);
		db.disbandFaction(faction);
	}

	public void updateRanksByKills(){
		List<Faction> list = new LinkedList<>(factions);
		list.sort((o1, o2) -> Integer.compare(o2.getKills(), o1.getKills()));
		for(int i = 1; i <= list.size(); i++){
			Faction f = list.get(i-1);
			f.setRank(i);
		}
		this.factionsOrderedKills = new ArrayList<>(list);
	}

	public void updateFactionsByOnlinePlayers(){
		List<Faction> list = new LinkedList<>(factions);
		list.sort((o1, o2) -> Integer.compare(o2.getAmountOfOnlinePlayers(), o1.getAmountOfOnlinePlayers()));
		this.factionsOrderedOnlinePlayers = new ArrayList<>(list);
	}


}
