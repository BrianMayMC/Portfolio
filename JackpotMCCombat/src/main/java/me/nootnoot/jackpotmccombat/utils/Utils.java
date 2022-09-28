package me.nootnoot.jackpotmccombat.utils;

import com.google.common.collect.ImmutableList;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import lombok.Getter;
import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.*;

import static org.bukkit.block.BlockFace.*;
import static org.bukkit.block.BlockFace.WEST;

public class Utils {
	@Getter
	private static final Map<UUID, Set<Location>> previousUpdates = new HashMap<>();

	private static final List<BlockFace> ALL_DIRECTIONS = ImmutableList.of(NORTH, EAST, SOUTH, WEST);


	public static String c(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public static void SendActionBar(Player p, String s){
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(s));
	}

	public static boolean isPvpEnabledAt(Location loc){
		//return getRegions(loc).queryState(null, Flags.PVP) != StateFlag.State.DENY;
		return getRegions(loc).testState(null, Flags.PVP);
	}



	private static ApplicableRegionSet getRegions(Location loc){
		com.sk89q.worldedit.util.Location loc1 = new com.sk89q.worldedit.util.Location(BukkitAdapter.adapt(loc.getWorld()), loc.getX(), loc.getY(), loc.getZ(), 0, 0);

		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionQuery query = container.createQuery();
		return query.getApplicableRegions(loc1);
	}

	public static void executeTask(Player player){
			// Stop processing if player has logged off
			UUID uuid = player.getUniqueId();
			if(!Bukkit.getPlayer(uuid).isOnline()){
				previousUpdates.remove(uuid);
				return;
			}

			// Update the players force field perspective and find all blocks to stop spoofing
			Set<Location> changedBlocks = getChangedBlocks(player);

			Set<Location> removeBlocks;
			if (previousUpdates.containsKey(uuid)) {
				removeBlocks = previousUpdates.get(uuid);
			} else {
				removeBlocks = new HashSet<>();
			}

			for (Location location : changedBlocks) {
				player.sendBlockChange(location, Bukkit.createBlockData(Material.RED_STAINED_GLASS));
				removeBlocks.remove(location);
			}

			// Remove no longer used spoofed blocks
			for (Location location : removeBlocks) {
				Block block = location.getBlock();
				player.sendBlockChange(location, block.getBlockData());
			}

			previousUpdates.put(uuid, changedBlocks);
	}

	public static Set<Location> getChangedBlocks(Player player) {
		Set<Location> locations = new HashSet<>();

		// Do nothing if player is not tagged
		if(JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(player) == null) return locations;

		// Find the radius around the player
		int r = 10;
		Location l = player.getLocation();
		Location loc1 = l.clone().add(r, 0, r);
		Location loc2 = l.clone().subtract(r, 0, r);
		int topBlockX = loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX();
		int bottomBlockX = loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX();
		int topBlockZ = loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ();
		int bottomBlockZ = loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ();

		// Iterate through all blocks surrounding the player
		for (int x = bottomBlockX; x <= topBlockX; x++) {
			for (int z = bottomBlockZ; z <= topBlockZ; z++) {
				// Location corresponding to current loop
				Location location = new Location(l.getWorld(), x, l.getY(), z);

				// PvP is enabled here, no need to do anything else
				if (isPvpEnabledAt(location)) continue;

				// Check if PvP is enabled in a location surrounding this
				if (!isPvpSurrounding(location)) continue;

				for (int i = -r; i < r; i++) {
					Location loc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());

					loc.setY(loc.getY() + i);

					// Do nothing if the block at the location is not air
					if (!loc.getBlock().getType().equals(Material.AIR) && !(loc.getBlock().getType().equals(Material.CAVE_AIR)) && !(loc.getBlock().getType().equals(Material.VOID_AIR))
					 && !(loc.getBlock().getType().equals(Material.WATER))) continue;

					// Add this location to locations
					locations.add(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
				}
			}
		}

		return locations;
	}

	public static boolean isPvpSurrounding(Location loc) {
		for (BlockFace direction : ALL_DIRECTIONS) {
			if (isPvpEnabledAt(loc.getBlock().getRelative(direction).getLocation())) {
				return true;
			}
		}

		return false;
	}

	public static boolean isPvPDeactivatedNear(Location loc){
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++) {
				Location loc2 = new Location(loc.getWorld(), loc.getX() + i, loc.getY(), loc.getZ() + j);
				if (!(isPvpEnabledAt(loc2))){
					return true;
				}
			}
		}
		return false;
	}

	public static void shutdown(){
		// Go through all previous updates and revert spoofed blocks
		for (UUID uuid : previousUpdates.keySet()) {
			Player player = Bukkit.getPlayer(uuid);
			if (player == null) continue;

			for (Location location : previousUpdates.get(uuid)) {
				Block block = location.getBlock();
				player.sendBlockChange(location, block.getBlockData());
			}
		}
	}

	private static List<Location> getFloor(Location center, int radius) {
		List<Location> locations = new ArrayList<>();
		for (int xMod = -radius; xMod <= radius; xMod++) {
			for (int zMod = -radius; zMod <= radius; zMod++) {
				locations.add(center.getBlock().getRelative(xMod, 0, zMod).getLocation());
			}
		}
		return locations;
	}

	public static boolean checkPvP(Location playerLocation){
		Location loc = playerLocation.clone().add(0.0, -1.0, 0.0);
		for(Location location : getFloor(loc, 1)){
			if(!isPvpEnabledAt(location)){
				return false;
			}
		}
		return true;
	}


	public static String convertItemName(Material material){
		return WordUtils.capitalizeFully(material.toString().replaceAll("_", " "));
	}

	public static long cooldown = JackpotMCCombat.getInstance().getConfig().getLong("combat-tag");
	public static int enderpearl_cooldown = JackpotMCCombat.getInstance().getConfig().getInt("enderpearl-cooldown");

	public static NamespacedKey key = new NamespacedKey(JackpotMCCombat.getInstance(), "combat-key");
}
