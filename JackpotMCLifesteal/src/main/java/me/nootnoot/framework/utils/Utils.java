package me.nootnoot.framework.utils;

import com.google.common.base.Strings;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.NonNull;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.*;

public class Utils {

	public static String c(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public static List<String> cL(List<String> strings){
		List<String> newList = new ArrayList<>();
		for(String s : strings){
			newList.add(c(s));
		}
		return newList;
	}

	public static String getProgressBar(double current, double requirement, int totalBars, char symbol) {
		float percent = (float) (current / requirement);
		int progressBars = (int) (totalBars * percent);
		if(current > requirement){
			progressBars = totalBars;
		}
		if(totalBars - progressBars <=0){
			return ChatColor.GREEN + Strings.repeat(String.valueOf(symbol), progressBars);
		}
		return ChatColor.GREEN + Strings.repeat(String.valueOf(symbol), progressBars) + ChatColor.RED + Strings.repeat(String.valueOf(symbol), totalBars - progressBars);
	}

	public static String getPercentage(double current, double requirement){
		return String.format("%.2f", (current / requirement) * 100) + "%";
	}

	public static boolean isInside(@NonNull Location loc, @NonNull Location l1, @NonNull Location l2) {
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		int x1 = Math.min(l1.getBlockX(), l2.getBlockX());
		int y1 = Math.min(l1.getBlockY(), l2.getBlockY());
		int z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
		int x2 = Math.max(l1.getBlockX(), l2.getBlockX());
		int y2 = Math.max(l1.getBlockY(), l2.getBlockY());
		int z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
		return x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2;
	}

//	public static void setBlockNMS(World world, int x, int y, int z, Material material, boolean applyPhysics) {
//		net.minecraft.server.v1_16_R3.World nmsWorld = ((CraftWorld) world).getHandle();
//		BlockPosition bp = new BlockPosition(x, y, z);
//		IBlockData data = CraftMagicNumbers.getBlock(material).getBlockData();
//		nmsWorld.setTypeAndData(bp, data, applyPhysics ? 3 : 2);
//	}
//
//	public static void setBlockNMSNoChunkLoading(World world, int x, int y, int z, Material material, boolean applyPhysics) {
//		net.minecraft.server.v1_16_R3.World nmsWorld = ((CraftWorld) world).getHandle();
//		net.minecraft.server.v1_16_R3.Chunk nmsChunk = nmsWorld.getChunkAt(x >> 4, z >> 4);
//		IBlockData ibd = CraftMagicNumbers.getBlock(material).getBlockData();
//
//		ChunkSection cs = nmsChunk.getSections()[y >> 4];
//		if (cs == nmsChunk.a() || cs == null) {
//			cs = new ChunkSection(y >> 4 << 4);
//			nmsChunk.getSections()[y >> 4] = cs;
//		}
//
//		if (applyPhysics)
//			cs.getBlocks().setBlock(x & 15, y & 15, z & 15, ibd);
//		else{
//			cs.getBlocks().b(x & 15, y & 15, z & 15, ibd);
//		}
//	}

	public static String capitalize(String s){
		return WordUtils.capitalize(s.replaceAll("_", " "));
	}


	public static void callEvent(Event event){
		Bukkit.getPluginManager().callEvent(event);
	}

	public static ItemStack getSkull(String url, int amount, boolean nostack){
		ItemStack is = new ItemStack(Material.PLAYER_HEAD, amount);
		SkullMeta is_meta = (SkullMeta) is.getItemMeta();
		GameProfile profile;
		if(nostack) {
			profile = new GameProfile(UUID.randomUUID(), null);
		}else{
			profile = new GameProfile(UUID.fromString("d884c8de-0ac0-11ed-861d-0242ac120002"), null);
		}

		byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField;
		try{
			profileField = is_meta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(is_meta, profile);
		}catch(NoSuchFieldException | IllegalArgumentException | IllegalAccessException e){
			e.printStackTrace();
		}
		is.setItemMeta(is_meta);
		return is;
	}

	public static String formatCurrency(double amount){
		return NumberFormat.getNumberInstance(Locale.US).format(amount);
	}

	public static Color getColor(String color){
		switch(color){
			case "RED" -> {
				return Color.RED;
			}
			case "BLUE" ->{
				return Color.BLUE;
			}
			case "PURPLE" -> {
				return Color.PURPLE;
			}
			case "LIGHT_BLUE" ->{
				return Color.AQUA;
			}
			case "YELLOW" ->{
				return Color.YELLOW;
			}
			case "ORANGE" ->{
				return Color.ORANGE;
			}
			default -> {
				return Color.GREEN;
			}
		}
	}
}
