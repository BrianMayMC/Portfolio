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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static String c(String s){
		return ChatColor.translateAlternateColorCodes('&', getHex(s));
	}
	private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

	public static String getHex(String msg) {
		Matcher matcher = pattern.matcher(msg);
		while (matcher.find()) {
			String color = msg.substring(matcher.start(), matcher.end());
			msg = msg.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
			matcher = pattern.matcher(msg);
		}
		return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg);
	}

	public static List<String> cL(List<String> strings){
		List<String> newList = new ArrayList<>();
		for(String s : strings){
			newList.add(c(s));
		}
		return newList;
	}

	public static String formatTime(long playtime){
		long sec = playtime % 60;
		long min = (playtime / 60)%60;
		long hours = (playtime/60)/60;

		String strSec=(sec<10)?"0"+Long.toString(sec):Long.toString(sec);
		String strmin=(min<10)?"0"+Long.toString(min):Long.toString(min);
		String strHours=(hours<10)?"0"+Long.toString(hours):Long.toString(hours);

		return strHours + ":" + strmin + ":" + strSec;
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
//		net.minecraft.server.level.WorldServer nmsWorld = ((CraftWorld) world).getHandle();
//		BlockPosition bp = new BlockPosition(x, y, z);
//		IBlockData data = CraftMagicNumbers.getBlock(material).m();
//		nmsWorld.a(bp, data, applyPhysics ? 3 : 2);
//	}

//	public static void setBlockNMSNoChunkLoading(World world, int x, int y, int z, Material material, boolean applyPhysics) {
//		net.minecraft.server.level.WorldServer nmsWorld = ((CraftWorld) world).getHandle();
//		net.minecraft.world.level.chunk.Chunk nmsChunk = nmsWorld.d(x >> 4, z >> 4);
//		IBlockData ibd = CraftMagicNumbers.getBlock(material).n();
//
//		ChunkSection cs = nmsChunk.d()[y >> 4];
//		if (cs == nmsChunk.a() || cs == null) {
//			cs = new ChunkSection(y >> 4 << 4, );
//			nmsChunk.d()[y >> 4] = cs;
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

//	public static void setWorldBorder(Player p, int warningDistance, double size, double centerx, double centerz){
//		WorldBorder border = new WorldBorder();
//		border.world = ((CraftWorld) p.getWorld()).getHandle();
//		border.setWarningDistance(warningDistance);
//		border.setSize(size + 1);
//		border.setCenter(centerx, centerz);
//		PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(border, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);
//		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
//	}
}
