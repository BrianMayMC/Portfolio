package me.nootnoot.albioncore.PL.commands;

import me.nootnoot.albioncore.AlbionCore;
import me.nootnoot.albioncore.BLL.artifactsystem.entities.Artifact;
import me.nootnoot.albioncore.BLL.artifactsystem.enums.ArtifactNames;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestSpawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Artifact artifact = AlbionCore.getInstance().getArtifactManager().GetArtifactByType(ArtifactNames.ARTIFACT_OF_THE_DAMNED);
		Player p = (Player) sender;
		if(!AlbionCore.getInstance().getArtifactManager().getSpawnedArtifacts().contains(artifact)){
			artifact.setP(p);
			AlbionCore.getInstance().getArtifactManager().SpawnArtifact(artifact, (Player) sender);
		}else{
			Location loc = artifact.getCoreLocation();
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Your artifact is already spawned at x: " + loc.getX() +  " y: " + loc.getY() + " z: " + loc.getZ());
		}

		return true;
	}


}
