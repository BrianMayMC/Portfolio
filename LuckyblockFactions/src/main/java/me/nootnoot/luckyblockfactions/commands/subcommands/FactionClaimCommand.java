package me.nootnoot.luckyblockfactions.commands.subcommands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import io.netty.handler.codec.compression.SnappyFrameDecoder;
import me.nootnoot.framework.commandsystem.SubCommand;
import me.nootnoot.framework.utils.Cuboid;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionClaim;
import me.nootnoot.luckyblockfactions.entities.FactionPlayer;
import me.nootnoot.luckyblockfactions.entities.FactionRole;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.dynmap.markers.AreaMarker;

import java.util.List;
import java.util.UUID;

public class FactionClaimCommand implements SubCommand {
	@Override
	public String getName() {
		return "claim";
	}

	@Override
	public int getNeededArgs() {
		return 0;
	}

	@Override
	public String getSyntax() {
		return Utils.c("&c/faction claim");
	}

	@Override
	public String getDescription() {
		return Utils.c("&7 claim a chunk of land");
	}

	@Override
	public String getPermission() {
		return "";
	}

	@Override
	public void execute(Player p, String[] args) {


		Faction f = LuckyblockFactions.getInstance().getFactionManager().getFaction(p);
		if(f == null){
			p.sendMessage(Utils.c("&C&l(!)&c You do not have a faction."));
			return;
		}
		FactionPlayer fp = f.getPlayer(p);
		if(fp.getRole() != FactionRole.LEADER){
			p.sendMessage(Utils.c("&C&l(!)&c You have to be the leader of the faction to claim area."));
			return;
		}

		if(p.getWorld().getEnvironment() != World.Environment.NORMAL){
			p.sendMessage(Utils.c("&c&l(!)&c You can only claim land in the overworld."));
			return;
		}

		if(getRegions(p.getLocation()).size() != 0){
			p.sendMessage(Utils.c("&C&l(!)&c You cannot claim here!"));
			return;
		}

		double max = LuckyblockFactions.getInstance().getConfig().getDouble("max-land-per-player");

		if(f.getAreas().size() >= max){
			p.sendMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.max-land-reached")));
		}

		Cuboid c = getArea(p.getLocation());
		Faction here = LuckyblockFactions.getInstance().getFactionManager().getFaction(p.getLocation());
		if(here != null){
			if(here.equals(f)){
				p.sendMessage(Utils.c("&C&l(!)&c Your faction already owns this piece of land."));
			}else{
				p.sendMessage(Utils.c("&C&l(!)&c This piece of land is already claimed by another faction!"));
			}
			return;
		}


		double cost = LuckyblockFactions.getInstance().getConfig().getDouble("claim-cost");
		double increase = LuckyblockFactions.getInstance().getConfig().getDouble("claim-cost-increase-per-chunk");
		cost += increase*(f.getAreas().size()-1);

		if(LuckyblockFactions.getInstance().getEcon().getBalance(p) < cost){
			p.sendMessage(Utils.c("&C&l(!)&c You do not have enough money for this."));
			return;
		}
		LuckyblockFactions.getInstance().getEcon().withdrawPlayer(p, cost);

		p.sendMessage(Utils.c(LuckyblockFactions.getInstance().getConfig().getString("messages.claim-message")));

		AreaMarker marker = LuckyblockFactions.getInstance().getMarker().createAreaMarker(UUID.randomUUID().toString(), f.getName(), true, c.getWorld().getName(), new double[]{c.getUpperSW().getX(), c.getLowerNE().getX()}, new double[]{c.getUpperSW().getZ(), c.getLowerNE().getZ()}, false);
		FactionClaim fc = new FactionClaim(c, cost);
		fc.setMarker(marker);
		f.getAreas().add(fc);

	}

	@Override
	public void executeConsole(ConsoleCommandSender p, String[] args) {

	}

	private ApplicableRegionSet getRegions(Location loc){
		com.sk89q.worldedit.util.Location loc1 = new com.sk89q.worldedit.util.Location(BukkitAdapter.adapt(loc.getWorld()), loc.getX(), loc.getY(), loc.getZ(), 0, 0);

		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionQuery query = container.createQuery();
		return query.getApplicableRegions(loc1);
	}


	public Cuboid getArea(Location location){
		Chunk c = location.getChunk();
		int xC;
		int zC;

		xC = (int)Math.ceil(c.getX()) * 16 + 15;
		zC = (int)Math.ceil(c.getZ()) * 16 + 15;
		Location down;
		Location up;
		down = new Location(location.getWorld(), Math.ceil(c.getX()) * 16, -200, zC);
		up = new Location(location.getWorld(), xC, 400, Math.ceil(c.getZ()) * 16);
		return new Cuboid(down, up);
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		return null;
	}
}
