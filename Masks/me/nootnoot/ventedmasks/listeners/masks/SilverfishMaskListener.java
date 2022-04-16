package me.nootnoot.ventedmasks.listeners.masks;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import de.tr7zw.nbtapi.NBTItem;
import me.nootnoot.ventedmasks.VentedMasks;
import me.nootnoot.ventedmasks.entities.Mask;
import me.nootnoot.ventedmasks.listeners.EquipListener;
import me.nootnoot.ventedmasks.utils.Cuboid;
import me.nootnoot.ventedmasks.utils.Utils;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;

public class SilverfishMaskListener implements Listener {

	private NBTItem nbtHelmet = null;
	private Mask helmetMask = null;

	@EventHandler
	public void onPickaxeMove(InventoryClickEvent e){
		if(e.getView().getTopInventory().getSize() != 5){
			if(e.getCurrentItem() == null) return;
			if(e.getCurrentItem().getType() == Material.AIR) return;
			if(e.getCurrentItem().equals(EquipListener.returnPickaxe())){
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() != Material.AIR) {
			nbtHelmet = new NBTItem(p.getInventory().getHelmet());
			String id = nbtHelmet.getString("id");
			helmetMask = VentedMasks.getMaskManager().byName(id);
		}
		if (nbtHelmet != null) {
			if (nbtHelmet.hasKey("mask")) {
				if (nbtHelmet.getItem().getItemMeta().getDisplayName().contains("Silverfish")) {
					if (p.getInventory().getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
						if (e.getAction() == Action.RIGHT_CLICK_AIR && e.getPlayer().isSneaking() || e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().isSneaking()) {
							if (e.getPlayer().getInventory().getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
								NBTItem pickaxe = new NBTItem(e.getPlayer().getInventory().getItemInHand());
								if(!pickaxe.hasNBTData()) return;
								if(!pickaxe.hasKey("maskspickaxe")) return;
								ItemStack is = e.getItem();
								ItemMeta is_meta = is.getItemMeta();

								int level = nbtHelmet.getInteger("level");
								String radiusname = is_meta.getDisplayName();

								if (level == 1) {
									if (radiusname.contains("Trench 1x1x1")) {
										radiusname = radiusname.replace("Trench 1x1x1", "Tray 1x1x1");
										Utils.sendTrayActionText(p, "1x1x1");
									} else if (radiusname.contains("Tray 1x1x1")) {
										radiusname = radiusname.replace("Tray 1x1x1", "Trench 1x1x1");
										Utils.sendTrenchActionText(p, "1x1x1");
									}
								} else if (level == 2) {
									if (radiusname.contains("Trench 1x1x1")) {
										radiusname = radiusname.replace("Trench 1x1x1", "Trench 3x3x3");
										Utils.sendTrenchActionText(p, "3x3x3");
									} else if (radiusname.contains("Trench 3x3x3")) {
										radiusname = radiusname.replace("Trench 3x3x3", "Tray 1x1x1");
										Utils.sendTrayActionText(p, "1x1x1");
									} else if (radiusname.contains("Tray 1x1x1")) {
										radiusname = radiusname.replace("Tray 1x1x1", "Tray 3x3x3");
										Utils.sendTrayActionText(p, "3x3x3");
									} else if (radiusname.contains("Tray 3x3x3")) {
										radiusname = radiusname.replace("Tray 3x3x3", "Trench 1x1x1");
										Utils.sendTrenchActionText(p, "1x1x1");
									}
								} else if (level == 3) {
									if (radiusname.contains("Trench 1x1x1")) {
										radiusname = radiusname.replace("Trench 1x1x1", "Trench 3x3x3");
										Utils.sendTrenchActionText(p, "3x3x3");
									} else if (radiusname.contains("Trench 3x3x3")) {
										radiusname = radiusname.replace("Trench 3x3x3", "Trench 5x5x5");
										Utils.sendTrenchActionText(p, "5x5x5");
									} else if (radiusname.contains("Trench 5x5x5")) {
										radiusname = radiusname.replace("Trench 5x5x5", "Tray 1x1x1");
										Utils.sendTrayActionText(p, "1x1x1");
									} else if (radiusname.contains("Tray 1x1x1")) {
										radiusname = radiusname.replace("Tray 1x1x1", "Tray 3x3x3");
										Utils.sendTrayActionText(p, "3x3x3");
									} else if (radiusname.contains("Tray 3x3x3")) {
										radiusname = radiusname.replace("Tray 3x3x3", "Tray 5x5x5");
										Utils.sendTrayActionText(p, "5x5x5");
									} else if (radiusname.contains("Tray 5x5x5")) {
										radiusname = radiusname.replace("Tray 5x5x5", "Trench 1x1x1");
										Utils.sendTrenchActionText(p, "1x1x1");
									}
								} else if (level == 4 || level == 5) {
									if (radiusname.contains("Trench 1x1x1")) {
										radiusname = radiusname.replace("Trench 1x1x1", "Trench 3x3x3");
										Utils.sendTrenchActionText(p, "3x3x3");
									} else if (radiusname.contains("Trench 3x3x3")) {
										radiusname = radiusname.replace("Trench 3x3x3", "Trench 5x5x5");
										Utils.sendTrenchActionText(p, "5x5x5");
									} else if (radiusname.contains("Trench 5x5x5")) {
										radiusname = radiusname.replace("Trench 5x5x5", "Trench 7x7x7");
										Utils.sendTrenchActionText(p, "7x7x7");
									} else if (radiusname.contains("Trench 7x7x7")) {
										radiusname = radiusname.replace("Trench 7x7x7", "Tray 1x1x1");
										Utils.sendTrayActionText(p, "1x1x1");
									} else if (radiusname.contains("Tray 1x1x1")) {
										radiusname = radiusname.replace("Tray 1x1x1", "Tray 3x3x3");
										Utils.sendTrayActionText(p, "3x3x3");
									} else if (radiusname.contains("Tray 3x3x3")) {
										radiusname = radiusname.replace("Tray 3x3x3", "Tray 5x5x5");
										Utils.sendTrayActionText(p, "5x5x5");
									} else if (radiusname.contains("Tray 5x5x5")) {
										radiusname = radiusname.replace("Tray 5x5x5", "Tray 7x7x7");
										Utils.sendTrayActionText(p, "7x7x7");
									} else if (radiusname.contains("Tray 7x7x7")) {
										radiusname = radiusname.replace("Tray 7x7x7", "Trench 1x1x1");
										Utils.sendTrenchActionText(p, "1x1x1");
									}
								}

								is_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', radiusname));
								is.setItemMeta(is_meta);
								int index = p.getInventory().getHeldItemSlot();
								p.getInventory().remove(is);
								p.getInventory().setItem(index, is);
							}
						}
					}
				}
			}
		}
	}


	@EventHandler
	public void onBlockBreak(BlockDamageEvent e) {
		Player p = e.getPlayer();

		int level = 0;
		int x = e.getBlock().getX();
		int y = e.getBlock().getY();
		int z = e.getBlock().getZ();

		ArrayList<Block> blocks = new ArrayList<>();



		if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() != Material.AIR) {
			nbtHelmet = new NBTItem(p.getInventory().getHelmet());
			String id = nbtHelmet.getString("id");
			helmetMask = VentedMasks.getMaskManager().byName(id);
			level = nbtHelmet.getInteger("level");
		}
		if (helmetMask != null) {
			if (nbtHelmet.hasKey("mask")) {
				if (nbtHelmet.getItem().getItemMeta().getDisplayName().contains("Silverfish")) {
					if (p.getInventory().getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
						if (e.getPlayer().getInventory().getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
							String pickaxeName = e.getPlayer().getItemInHand().getItemMeta().getDisplayName();
							NBTItem pickaxe = new NBTItem(e.getPlayer().getInventory().getItemInHand());
							if(!pickaxe.hasNBTData()) return;
							if(!pickaxe.hasKey("maskspickaxe")) return;

							Faction faction = FPlayers.getInstance().getByPlayer(e.getPlayer()).getFaction();

							if (level == 1) {
								if(Board.getInstance().getFactionAt(new FLocation(e.getBlock().getLocation())).getId().equalsIgnoreCase(faction.getId())){
									blocks.add(e.getBlock());
								}
							} else if (level == 2 && pickaxeName.contains("1x1x1")) {
								if(Board.getInstance().getFactionAt(new FLocation(e.getBlock().getLocation())).getId().equalsIgnoreCase(faction.getId())){
									blocks.add(e.getBlock());
								}
							} else if (level == 2 && pickaxeName.contains("3x3x3")) {
								Cuboid cuboid = new Cuboid(p.getWorld(), x - 1, y - 1, z - 1, x + 1, y + 1, z + 1);
								for(Block block : cuboid.getBlocks()){
									if(Board.getInstance().getFactionAt(new FLocation(e.getBlock().getLocation())).getId().equalsIgnoreCase(faction.getId())){
										blocks.add(block);
									}
								}
							} else if (level == 3 && pickaxeName.contains("1x1x1")) {
								if(Board.getInstance().getFactionAt(new FLocation(e.getBlock().getLocation())).getId().equalsIgnoreCase(faction.getId())){
									blocks.add(e.getBlock());
								}
							} else if (level == 3 && pickaxeName.contains("3x3x3")) {
								Cuboid cuboid = new Cuboid(p.getWorld(), x - 1, y - 1, z - 1, x + 1, y + 1, z + 1);
								for(Block block : cuboid.getBlocks()){
									if(Board.getInstance().getFactionAt(new FLocation(e.getBlock().getLocation())).getId().equalsIgnoreCase(faction.getId())){
										blocks.add(block);
									}
								}
							} else if (level == 3 && pickaxeName.contains("5x5x5")) {
								Cuboid cuboid = new Cuboid(p.getWorld(), x - 2, y - 2, z - 2, x + 1, y + 1, z + 1);
								for(Block block : cuboid.getBlocks()){
									if(Board.getInstance().getFactionAt(new FLocation(e.getBlock().getLocation())).getId().equalsIgnoreCase(faction.getId())){
										blocks.add(block);
									}
								}
							} else if (level == 4 && pickaxeName.contains("1x1x1") || level == 5 && pickaxeName.contains("1x1x1")) {
								if(Board.getInstance().getFactionAt(new FLocation(e.getBlock().getLocation())).getId().equalsIgnoreCase(faction.getId())){
									blocks.add(e.getBlock());
								}
							} else if (level == 4 && pickaxeName.contains("3x3x3") || level == 5 && pickaxeName.contains("3x3x3")) {
								Cuboid cuboid = new Cuboid(p.getWorld(), x - 1, y - 1, z - 1, x + 1, y + 1, z + 1);
								for(Block block : cuboid.getBlocks()){
									if(Board.getInstance().getFactionAt(new FLocation(e.getBlock().getLocation())).getId().equalsIgnoreCase(faction.getId())){
										blocks.add(block);
									}
								}
							} else if (level == 4 && pickaxeName.contains("5x5x5") || level == 5 && pickaxeName.contains("5x5x5")) {
								Cuboid cuboid = new Cuboid(p.getWorld(), x - 2, y - 2, z - 2, x + 1, y + 1, z + 1);
								for(Block block : cuboid.getBlocks()){
									if(Board.getInstance().getFactionAt(new FLocation(e.getBlock().getLocation())).getId().equalsIgnoreCase(faction.getId())){
										blocks.add(block);
									}
								}
							} else if (level == 4 && pickaxeName.contains("7x7x7") || level == 5 && pickaxeName.contains("7x7x7")) {
								Cuboid cuboid = new Cuboid(p.getWorld(), x - 3, y - 3, z - 3, x + 3, y + 3, z + 3);
								for(Block block : cuboid.getBlocks()){
									if(Board.getInstance().getFactionAt(new FLocation(e.getBlock().getLocation())).getId().equalsIgnoreCase(faction.getId())){
										blocks.add(block);
									}
								}
							}

							for (Block block : blocks) {
								if (pickaxeName.contains("Trench")){
									if(block.getType() == Material.CHEST || block.getType() == Material.MOB_SPAWNER || block.getType() == Material.BEDROCK) return;

									block.setType(Material.AIR);

								}
								else if (pickaxeName.contains("Tray")) {
									if (block.getType() == Material.NETHERRACK || block.getType() == Material.DIRT || block.getType() == Material.SNOW_BLOCK || block.getType() == Material.SNOW) {
										block.setType(Material.AIR);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void OnBlockBreak(BlockBreakEvent e){
		Player p = e.getPlayer();
		if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() != Material.AIR) {
			nbtHelmet = new NBTItem(p.getInventory().getHelmet());
			String id = nbtHelmet.getString("id");
			helmetMask = VentedMasks.getMaskManager().byName(id);
		}

		if (helmetMask != null) {
			if (nbtHelmet.hasKey("mask")) {
				if (nbtHelmet.getItem().getItemMeta().getDisplayName().contains("Silverfish")) {
					if (p.getInventory().getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
						if (e.getPlayer().getInventory().getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
	public static void removePickaxe(Player p){
		p.getInventory().remove(EquipListener.returnPickaxe());
	}
}
