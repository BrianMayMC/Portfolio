package me.nootnoot.ventedmasks.utils;

import me.nootnoot.ventedmasks.VentedMasks;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {

	public static void sendTrenchActionText(Player p, String radius){
		String message = ChatColor.translateAlternateColorCodes('&', VentedMasks.getInstance().getConfig().getString("trench-pick-actionbar"));
		PlayerConnection connection = ((CraftPlayer) p.getPlayer()).getHandle().playerConnection;
		message = message.replace("%range%", radius);
		IChatBaseComponent text = IChatBaseComponent.ChatSerializer.a("{'text': '" + message + "'}");
		PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(text.getText()), (byte) 2);
		connection.sendPacket(packet);
	}

	public static void sendTrayActionText(Player p, String radius){
		String message = ChatColor.translateAlternateColorCodes('&', VentedMasks.getInstance().getConfig().getString("tray-pick-actionbar"));
		PlayerConnection connection = ((CraftPlayer) p.getPlayer()).getHandle().playerConnection;
		message = message.replace("%range%", radius);
		IChatBaseComponent text = IChatBaseComponent.ChatSerializer.a("{'text': '" + message + "'}");
		PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(text.getText()), (byte) 2);
		connection.sendPacket(packet);
	}
}
