package me.nootnoot.albioncore.BLL.artifactsystem.menus.listeners;

import lombok.Getter;
import me.nootnoot.albioncore.AlbionCore;
import me.nootnoot.albioncore.BLL.artifactsystem.entities.Artifact;
import me.nootnoot.albioncore.BLL.artifactsystem.entities.ArtifactObject;
import me.nootnoot.albioncore.BLL.artifactsystem.enums.ArtifactNames;
import me.nootnoot.albioncore.BLL.artifactsystem.menus.ArtifactMenu;
import me.nootnoot.albioncore.BLL.artifactsystem.tasks.ParticleTask;
import me.nootnoot.albioncore.BLL.artifactsystem.tasks.RemoveTask;
import me.nootnoot.albioncore.BLL.playersystem.entities.ABPlayer;
import me.nootnoot.albioncore.BLL.spellsystem.Spell;
import me.nootnoot.albioncore.BLL.utils.Cuboid;
import me.nootnoot.albioncore.BLL.utils.ui.gui.GUIManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ArtifactHandler implements Listener {

	@Getter
	private static HashSet<Player> inSpellMode;

	private List<Player> shovedPlayers = new ArrayList<>();
	private Artifact artifact;

	public ArtifactHandler() {
		inSpellMode = new HashSet<>();
	}

	@EventHandler
	public void OnInteract(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (!e.getClickedBlock().hasMetadata("artifactType")) return;
		e.setCancelled(true);


		if(artifact.getP() != e.getPlayer()){
			e.getPlayer().sendMessage(ChatColor.RED + "This artifact does not belong to you! Away with you!");
			Vector vector = new Vector();
			double randomNumber = ThreadLocalRandom.current().nextDouble(3);
			vector.setZ(randomNumber);
			vector.setX(randomNumber);
			e.getPlayer().setVelocity(vector);
			shovedPlayers.add(e.getPlayer());
			return;
		}

		GUIManager.getInstance().openInterface(e.getPlayer(), new ArtifactMenu());
	}

	@EventHandler
	public void onFall(EntityDamageEvent e){
		if(e.getCause() != EntityDamageEvent.DamageCause.FALL) return;
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(shovedPlayers.contains(p)){
				e.setCancelled(true);
				shovedPlayers.remove(p);
			}
		}
	}

	@EventHandler
	public void OnBreak(BlockBreakEvent e) {
		if (e.getBlock().hasMetadata("artifactType") || e.getBlock().hasMetadata("artifactPlatform")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void OnChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		ABPlayer ABP = AlbionCore.getInstance().getAbPlayerManager().GetPlayer(p);
		if (!inSpellMode.contains(p)) return;
		e.setCancelled(true);
		inSpellMode.remove(p);
		for (Spell spell : ABP.getSpellPouch().getArtifactSpell().values()) {
			if (e.getMessage().equalsIgnoreCase(spell.getSpell())) {
				p.sendMessage(ChatColor.GREEN + "You've successfully opened the artifact!");
				artifact.setStatus("Unlocked");
				ABP.getArtifactPouch().getClaimedArtifacts().add(artifact);
				Bukkit.getScheduler().runTask(AlbionCore.getInstance(), ()->{
					AlbionCore.getInstance().getArtifactManager().RemoveArtifact(artifact);
				});
				return;
			}
		}
		p.sendMessage(ChatColor.RED + "Wrong spell!");
		p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 1, 1);
	}

	/*
	This next section handles the spawning part of the artifact.
	 */
	@EventHandler
	public void entityChange(EntityChangeBlockEvent e) {
		if (e.getEntity() instanceof FallingBlock) {
			FallingBlock fallingBlock = (FallingBlock) e.getEntity();

			if (!fallingBlock.hasMetadata("artifactType")) return;
			if (e.getTo() == Material.BEACON) {

				if (!e.getBlock().canPlace(fallingBlock.getBlockData())) {
					e.getBlock().setType(Material.BEACON);
				}

				Bukkit.getWorld(e.getBlock().getWorld().getUID()).strikeLightningEffect(e.getBlock().getLocation());
				e.getBlock().setMetadata("artifactType", new FixedMetadataValue(AlbionCore.getInstance(), fallingBlock.getMetadata("artifactType")));

				Location minus = new Location(e.getBlock().getWorld(), e.getBlock().getX() - 1, e.getBlock().getY() - 1, e.getBlock().getZ() - 1);
				Location plus = new Location(e.getBlock().getWorld(), e.getBlock().getX() + 1, e.getBlock().getY() - 1, e.getBlock().getZ() + 1);
				Cuboid cuboid = new Cuboid(minus, plus);

				HashSet<ArtifactObject> blocks = new HashSet<>();
				for (Block block : cuboid.getBlocks()) {
					blocks.add(new ArtifactObject(Material.OBSIDIAN, block.getType(), block.getLocation()));

					block.setType(Material.OBSIDIAN);
					block.setMetadata("artifactPlatform", new FixedMetadataValue(AlbionCore.getInstance(), true));
				}


				List<MetadataValue> name = fallingBlock.getMetadata("artifactType");
				ArtifactNames artifactName = ArtifactNames.valueOf(name.get(0).asString());
				artifact = AlbionCore.getInstance().getArtifactManager().GetArtifactByType(artifactName);

				Bukkit.getWorld(e.getBlock().getWorld().getUID()).playSound(e.getBlock().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, 1);

				artifact.setCoreLocation(e.getBlock().getLocation());
				artifact.setBlocksPlaced(blocks);

				new ParticleTask(artifact).runTaskTimer(AlbionCore.getInstance(), 0L, 20L);
				new RemoveTask(artifact).runTaskLater(AlbionCore.getInstance(), 10L * 20L);

			}
		}
	}

}
