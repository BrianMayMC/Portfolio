package me.nootnoot.jackpotmccombat.listeners;

import com.jackpotmc.teams.JackpotTeams;
import com.jackpotmc.teams.team.JackpotTeam;
import me.nootnoot.jackpotmccombat.JackpotMCCombat;
import me.nootnoot.jackpotmccombat.entities.CombatPlayer;
import me.nootnoot.jackpotmccombat.utils.Utils;
import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class CombatTagListener implements Listener {


	@EventHandler
	public void onBowHit(ProjectileHitEvent e){
		if(!(e.getHitEntity() instanceof Player target)) return;
		if(!(e.getEntity().getShooter() instanceof Player p)) return;

		if(JackpotMCCombat.getInstance().isGrace()) {
			PersistentDataContainer targetContainer = target.getPersistentDataContainer();
			PersistentDataContainer playerContainer = p.getPersistentDataContainer();

			if (playerContainer.has(Utils.key, PersistentDataType.INTEGER)) {
				if (playerContainer.get(Utils.key, PersistentDataType.INTEGER) > 0) {
					p.sendMessage(Utils.c(JackpotMCCombat.getInstance().getConfig().getString("player-on-grace-message")));
					e.setCancelled(true);
					return;
				}
			}

			if (targetContainer.has(Utils.key, PersistentDataType.INTEGER)) {
				if (targetContainer.get(Utils.key, PersistentDataType.INTEGER) > 0) {
					p.sendMessage(Utils.c(JackpotMCCombat.getInstance().getConfig().getString("target-on-grace-message")));
					e.setCancelled(true);
					return;
				}
			}
		}
		if(checkTeams(p, target)){
			e.setCancelled(true);
			return;
		}

		//check if damager is on cooldown, if yes update cooldown, if not make cooldown.
		CombatPlayer combatP = JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(p);
		CombatPlayer combatTarget = JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(target);

		if(!Utils.isPvpEnabledAt(target.getLocation())) return;
		if(!Utils.isPvpEnabledAt(p.getLocation())) return;

		CheckElytra(p);
		if(combatP == null){
			p.sendMessage(Utils.c(JackpotMCCombat.getInstance().getConfig().getString("combat-tag-message").replace("%player_name%", target.getName()).replace("%seconds%", String.valueOf(Utils.cooldown))));
			JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayers().add(new CombatPlayer(p, Utils.cooldown));
			//send wall blocks
			Utils.executeTask(p);
			//check if player is next to border
			if(!Utils.checkPvP(p.getLocation())){
				Location location = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
				location.setYaw(p.getLocation().getYaw() + 180);
				p.teleport(location);
			}
		}else{
			combatP.setCooldown(Utils.cooldown);
		}
		CheckElytra(target);
		if(combatTarget == null){
			target.sendMessage(Utils.c(JackpotMCCombat.getInstance().getConfig().getString("combat-tag-message").replace("%player_name%", p.getName()).replace("%seconds%", String.valueOf(Utils.cooldown))));
			JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayers().add(new CombatPlayer(target, Utils.cooldown));
			//send wall blocks
			Utils.executeTask(target);

			//check if player is next to border
			if(!Utils.checkPvP(target.getLocation())){
				Location location = new Location(target.getWorld(), target.getLocation().getX(), target.getLocation().getY(), target.getLocation().getZ());
				location.setYaw(target.getLocation().getYaw() + 180);
				target.teleport(location);
			}
		}else{
			combatTarget.setCooldown(Utils.cooldown);
		}
	}

	@EventHandler
	public void damage(EntityDamageEvent e){
		if(!(e.getEntity() instanceof Player target)) return;
		if(JackpotMCCombat.getInstance().isGrace()) {
			PersistentDataContainer targetContainer = target.getPersistentDataContainer();
			if (targetContainer.has(Utils.key, PersistentDataType.INTEGER)) {
				if (targetContainer.get(Utils.key, PersistentDataType.INTEGER) > 0) {
					e.setCancelled(true);
				}
			}
		}

	}

	@EventHandler
	public void onHit(EntityDamageByEntityEvent e){

		if(!(e.getDamager() instanceof Player p)) return;
		if(!(e.getEntity() instanceof Player target)) return;

		if(JackpotMCCombat.getInstance().isGrace()) {
			PersistentDataContainer targetContainer = target.getPersistentDataContainer();
			PersistentDataContainer playerContainer = p.getPersistentDataContainer();

			if (playerContainer.has(Utils.key, PersistentDataType.INTEGER)) {
				if (playerContainer.get(Utils.key, PersistentDataType.INTEGER) > 0) {
					p.sendMessage(Utils.c(JackpotMCCombat.getInstance().getConfig().getString("player-on-grace-message")));
					e.setCancelled(true);
					return;
				}
			}

			if (targetContainer.has(Utils.key, PersistentDataType.INTEGER)) {
				if (targetContainer.get(Utils.key, PersistentDataType.INTEGER) > 0) {
					p.sendMessage(Utils.c(JackpotMCCombat.getInstance().getConfig().getString("target-on-grace-message")));
					e.setCancelled(true);
					return;
				}
			}
		}

		if(checkTeams(p, target)){
			e.setCancelled(true);
			return;
		}

		//check if damager is on cooldown, if yes update cooldown, if not make cooldown.
		CombatPlayer combatP = JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(p);
		CombatPlayer combatTarget = JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayer(target);

		if(!Utils.isPvpEnabledAt(target.getLocation())) return;
		if(!Utils.isPvpEnabledAt(p.getLocation())) return;

		CheckElytra(p);

		if(combatP == null){
			p.sendMessage(Utils.c(JackpotMCCombat.getInstance().getConfig().getString("combat-tag-message").replace("%player_name%", target.getName()).replace("%seconds%", String.valueOf(Utils.cooldown))));
			JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayers().add(new CombatPlayer(p, Utils.cooldown));
			Utils.executeTask(p);
			if(!Utils.checkPvP(p.getLocation())){
				Location location = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
				location.setYaw(p.getLocation().getYaw() + 180);
				p.teleport(location);
			}
		}else{
			combatP.setCooldown(Utils.cooldown);
		}
		CheckElytra(target);
		if(combatTarget == null){
			JackpotMCCombat.getInstance().getCombatPlayerManager().getCombatPlayers().add(new CombatPlayer(target, Utils.cooldown));
			target.sendMessage(Utils.c(JackpotMCCombat.getInstance().getConfig().getString("combat-tag-message").replace("%player_name%", p.getName()).replace("%seconds%", String.valueOf(Utils.cooldown))));
			Utils.executeTask(target);
			if(!Utils.checkPvP(target.getLocation())){
				Location location = new Location(target.getWorld(), target.getLocation().getX(), target.getLocation().getY(), target.getLocation().getZ());
				location.setYaw(target.getLocation().getYaw() + 180);
				target.teleport(location);
			}
		}else{
			combatTarget.setCooldown(Utils.cooldown);
		}
	}

	private boolean checkTeams(Player p, Player target){
		if(JackpotMCCombat.getInstance().checkNootTeams()){
			Team pTeam = JackpotMCTeams.getInstance().getTeamManager().hasTeam(p);
			Team tTeam = JackpotMCTeams.getInstance().getTeamManager().hasTeam(target);

			if(pTeam == null || tTeam == null) return false;

			return pTeam.getTeamID().equalsIgnoreCase(tTeam.getTeamID());
		}
		if(JackpotMCCombat.getInstance().checkFaction()){
			Faction pF = LuckyblockFactions.getInstance().getFactionManager().getFaction(p);
			Faction tF = LuckyblockFactions.getInstance().getFactionManager().getFaction(target);
			if(pF == null || tF == null) return false;

			return tF.getName().equalsIgnoreCase(pF.getName());
		}
		return false;
	}

	public void CheckElytra(Player p){
		ItemStack elytra = p.getInventory().getChestplate();

		if(elytra == null) return;

		if(elytra.getType() != Material.ELYTRA) return;

		p.getInventory().setChestplate(new ItemStack(Material.AIR));

		if(p.getInventory().firstEmpty() == -1){
			p.getWorld().dropItemNaturally(p.getLocation(), elytra);
			return;
		}

		p.getInventory().addItem(elytra);
	}
}
