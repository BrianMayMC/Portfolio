package me.nootnoot.luckyblockservericon;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerPing;
import com.velocitypowered.api.util.Favicon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Plugin(id = "luckyblockservericon", name = "LuckyblockServerIcon", version = "1",
		url = "https://avalondev.net", description = "Alternating server icon plugin for Luckyblock", authors = {"nootnoot"})
public final class LuckyblockServerIcon {

	private final File iconFolder;
	private int tempOrder = 0;

	private final ProxyServer server;

	@Inject
	public LuckyblockServerIcon(ProxyServer proxyServer, Logger logger){
		this.server = proxyServer;
		iconFolder = new File("plugins/LuckyblockServerIcon/Icons");
		iconFolder.mkdirs();
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		// Do some operation demanding access to the Velocity API here.
		// For instance, we could register an event:

		server.getScheduler().buildTask(this, ()->{
				try {
					forcePing();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}).delay(15000, TimeUnit.MILLISECONDS);
	}



	private void forcePing() throws IOException {
		try (Socket socket = new Socket(server.getBoundAddress().getAddress(), server.getBoundAddress().getPort())) {
			socket.setSoTimeout(1000);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream(socket.getInputStream());
			int version = 47;
			int nextState = 1;
			int handshakePacketId = 0x00;
			PacketUtils.writeVarInt(out, PacketUtils.getVarIntSize(version) + PacketUtils.getStringSize(server.getBoundAddress().getHostString()) + 2 + PacketUtils.getVarIntSize(nextState) + PacketUtils.getVarIntSize(handshakePacketId));
			PacketUtils.writeVarInt(out, handshakePacketId);
			PacketUtils.writeVarInt(out, version);
			PacketUtils.writeString(out, server.getBoundAddress().getAddress().getHostAddress());
			out.writeShort(server.getBoundAddress().getPort());
			PacketUtils.writeVarInt(out, nextState);
			int writePacketId = 0x00;
			PacketUtils.writeVarInt(out, PacketUtils.getVarIntSize(writePacketId));
			PacketUtils.writeVarInt(out, writePacketId);
			PacketUtils.readVarInt(in);
			int pingPacketId = 0x01;
			long time = System.currentTimeMillis();
			PacketUtils.writeVarInt(out, PacketUtils.getVarIntSize(pingPacketId) + 8);
			PacketUtils.writeVarInt(out, pingPacketId);
			out.writeLong(time);
			PacketUtils.readVarInt(in);
			in.readLong();
		}
	}

	@Subscribe
	public void on(ProxyPingEvent event) {
		System.out.println("testing proxypingevent");
		changeIcon(event);
	}

	private void changeIcon(ProxyPingEvent event) {
		if (iconFolder.listFiles().length == 0) {
			return;
		}
		File iconFile;
		if (tempOrder == iconFolder.listFiles().length) {
			tempOrder = 0;
		}
		iconFile = iconFolder.listFiles()[tempOrder];
		tempOrder += 1;

		if (!iconFile.getName().toLowerCase().endsWith(".png")) {
			return;
		}
		BufferedImage big;
		try {
			big = ImageIO.read(iconFile);
			int width = big.getWidth();
			int height = big.getHeight();
			if (width != 64 || height != 64) {
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Favicon favicon = Favicon.create(iconFile.toPath());
			event.setPing(ServerPing.builder().favicon(favicon).description(event.getPing().getDescriptionComponent()).version(event.getPing().getVersion()).build());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
