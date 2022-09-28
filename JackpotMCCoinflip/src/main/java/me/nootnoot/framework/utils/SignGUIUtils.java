package me.nootnoot.framework.utils;

import com.comphenix.packetwrapper.WrapperPlayClientUpdateSign;
import com.comphenix.packetwrapper.WrapperPlayServerBlockChange;
import com.comphenix.packetwrapper.WrapperPlayServerOpenSignEditor;
import com.comphenix.packetwrapper.WrapperPlayServerTileEntityData;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.WeakHashMap;

public class SignGUIUtils {
	// Maps the UUID of a Player to the Location of the SignGUI that he opened
	private static final WeakHashMap<Player, SignGUIListener> mSignGUILocationMap = new WeakHashMap<>();

	/**
	 * Register a Listener to react to the {@link Player} closing the GUI.
	 */
	public static void registerSignGUIListener(JavaPlugin instance) {
		ProtocolManager manager = ProtocolLibrary.getProtocolManager();
		manager.addPacketListener(new PacketAdapter(instance, PacketType.Play.Client.UPDATE_SIGN) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				WrapperPlayClientUpdateSign wrapper = new WrapperPlayClientUpdateSign(event.getPacket());
				BlockPosition blockPos = wrapper.getLocation();
				SignGUIListener listener = mSignGUILocationMap.get(event.getPlayer());

				if(listener != null && blockPos.getX() == listener.location.getX() && blockPos.getY() == listener.location.getY() && blockPos.getZ() == listener.location.getZ()) {
					// Do anything here
					fixFakeBlockFor(event.getPlayer(), listener.location);
					listener.onSignDone(event.getPlayer(), wrapper.getLines());
					mSignGUILocationMap.remove(event.getPlayer());
				}
			}
		});
	}

	/**
	 * Fixes the Block at the {@link Location} of a SignGUI a {@link Player} opened.
	 * @param player The {@link Player}.
	 * @param loc The {@link Location} of the Block that has to be fixed for the {@link Player}.
	 */
	static void fixFakeBlockFor(Player player, Location loc) {
		// Check if the Player is in the same World of the fake Block.
		// If the Player is in another world, the fake Block will be fixed automatically when the Player loads its world.
		if(loc.getWorld() != null && player.getWorld().equals(loc.getWorld())) {
			ProtocolManager manager = ProtocolLibrary.getProtocolManager();


			WrapperPlayServerBlockChange wrapperBlockChange = new WrapperPlayServerBlockChange(manager.createPacket(PacketType.Play.Server.BLOCK_CHANGE));
			// WrapperPlayServerTileEntityData wrapperTileEntityData = new WrapperPlayServerTileEntityData(manager.createPacket(PacketType.Play.Server.TILE_ENTITY_DATA));

			Material material = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()).getType();
			WrappedBlockData blockData = WrappedBlockData.createData(material, loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()).getData());
			wrapperBlockChange.setLocation(new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
			wrapperBlockChange.setBlockData(blockData);

			wrapperBlockChange.sendPacket(player);
		}
	}

	/**
	 * Opens a Sign GUI for a{@link Player}.
	 * @param player The {@link Player}
	 * @param line1 The first line which should be displayed on the GUI.
	 * @param line2 The second line which should be displayed on the GUI.
	 * @param line3 The third line which should be displayed on the GUI.
	 */
	public static void openGUIFor(Player player, String line1, String line2, String line3, String line4, SignGUIListener listener) {
		ProtocolManager manager = ProtocolLibrary.getProtocolManager();

		// The Position has to be near the Player. Otherwise the BlockChange Packet will be ignore by the Client.
		BlockPosition pos = new BlockPosition(player.getLocation().getBlockX(), 0, player.getLocation().getBlockZ());

		// The Nbt Data used to display custom Text in the GUI
		NbtCompound signNbt = NbtFactory.ofCompound("AnyStringHere");
		signNbt.put("Text1", "{\"text\":\"" + line1 + "\"}");
		signNbt.put("Text2", "{\"text\":\"" + line2 + "\"}");
		signNbt.put("Text3", "{\"text\":\"" + line3 + "\"}");
		signNbt.put("Text4", "{\"text\":\"" + line4 + "\"}");
		signNbt.put("id", "minecraft:sign");
		signNbt.put("x", pos.getX());
		signNbt.put("y", pos.getY());
		signNbt.put("z", pos.getZ());

		// Wrapper that sends the Player a fake Sign Block
		WrapperPlayServerBlockChange wrapperBlockChange = new WrapperPlayServerBlockChange(manager.createPacket(PacketType.Play.Server.BLOCK_CHANGE));
		// Wrapper that lets the Player open a Sign GUI
		WrapperPlayServerOpenSignEditor wrapperOpenSignEditor = new WrapperPlayServerOpenSignEditor(manager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR));
		WrapperPlayServerTileEntityData data = new WrapperPlayServerTileEntityData(manager.createPacket(PacketType.Play.Server.TILE_ENTITY_DATA));
		data.setNbtData(signNbt);

		// Add the Position and BlockData to the BlockChange Packet
		wrapperBlockChange.setLocation(pos);
		wrapperBlockChange.setBlockData(WrappedBlockData.createData(Material.OAK_SIGN));

		// Add the Position to the OpenSignEditor Packet
		wrapperOpenSignEditor.setLocation(pos);


		// Send the Packets
		wrapperBlockChange.sendPacket(player);
		wrapperOpenSignEditor.sendPacket(player);
		data.sendPacket(player);
		// Save the Position where the Sign was placed
		listener.location = new Location(player.getWorld(), pos.getX(), pos.getY(), pos.getZ());
		mSignGUILocationMap.put(player, listener);
	}

	public static abstract class SignGUIListener {
		public Location location;

		public abstract void onSignDone(Player player, String[] lines);
	}
}