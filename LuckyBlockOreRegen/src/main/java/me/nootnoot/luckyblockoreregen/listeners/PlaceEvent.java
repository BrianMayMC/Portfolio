package me.nootnoot.luckyblockoreregen.listeners;

import de.tr7zw.nbtapi.NBTBlock;
import me.nootnoot.luckyblockoreregen.LuckyBlockOreRegen;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceEvent implements Listener {

	@EventHandler
	public void OnPlace(BlockPlaceEvent e){
		if(!LuckyBlockOreRegen.getInstance().getRegenBlockManager().getPlayersInMode().contains(e.getPlayer())) return;

		e.setCancelled(true);

		e.getBlockAgainst().setType(e.getBlock().getType());
		NBTBlock block = new NBTBlock(e.getBlockAgainst());
		block.getData().setString("customblock", "yes");
		LuckyBlockOreRegen.getInstance().getRegenBlockStorage().createBlock(e.getBlock().getType(), e.getBlockAgainst().getLocation());
	}
}
