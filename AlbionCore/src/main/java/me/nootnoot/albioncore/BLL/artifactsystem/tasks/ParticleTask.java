package me.nootnoot.albioncore.BLL.artifactsystem.tasks;

import me.nootnoot.albioncore.AlbionCore;
import me.nootnoot.albioncore.BLL.artifactsystem.entities.Artifact;
import me.nootnoot.albioncore.BLL.artifactsystem.entities.ArtifactObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleTask extends BukkitRunnable {

	private final Artifact artifact;

	public ParticleTask(Artifact artifact){
		this.artifact = artifact;
	}

	@Override
	public void run() {
		if(AlbionCore.getInstance().getArtifactManager().getSpawnedArtifacts().contains(artifact)){
			Bukkit.getWorld(artifact.getCoreLocation().getWorld().getUID()).spawnParticle(Particle.SMOKE_LARGE, artifact.getCoreLocation(), 5);
			Bukkit.getWorld(artifact.getCoreLocation().getWorld().getUID()).spawnParticle(Particle.SMOKE_LARGE, artifact.getCoreLocation(), 5);
			Bukkit.getWorld(artifact.getCoreLocation().getWorld().getUID()).spawnParticle(Particle.SMOKE_LARGE, artifact.getCoreLocation(), 5);
			Bukkit.getWorld(artifact.getCoreLocation().getWorld().getUID()).spawnParticle(Particle.SMOKE_LARGE, artifact.getCoreLocation(), 5);
			Bukkit.getWorld(artifact.getCoreLocation().getWorld().getUID()).spawnParticle(Particle.SMOKE_LARGE, artifact.getCoreLocation(), 5);
			Bukkit.getWorld(artifact.getCoreLocation().getWorld().getUID()).spawnParticle(Particle.SMOKE_LARGE, artifact.getCoreLocation(), 5);
			Bukkit.getWorld(artifact.getCoreLocation().getWorld().getUID()).spawnParticle(Particle.SMOKE_LARGE, artifact.getCoreLocation(), 5);
			Bukkit.getWorld(artifact.getCoreLocation().getWorld().getUID()).spawnParticle(Particle.FLAME, artifact.getCoreLocation(), 5);
			Bukkit.getWorld(artifact.getCoreLocation().getWorld().getUID()).spawnParticle(Particle.FLAME, artifact.getCoreLocation(), 5);
			Bukkit.getWorld(artifact.getCoreLocation().getWorld().getUID()).spawnParticle(Particle.FLAME, artifact.getCoreLocation(), 5);
			Bukkit.getWorld(artifact.getCoreLocation().getWorld().getUID()).spawnParticle(Particle.FLAME, artifact.getCoreLocation(), 5);
			Bukkit.getWorld(artifact.getCoreLocation().getWorld().getUID()).spawnParticle(Particle.FLAME, artifact.getCoreLocation(), 5);
			Bukkit.getWorld(artifact.getCoreLocation().getWorld().getUID()).spawnParticle(Particle.FLAME, artifact.getCoreLocation(), 5);
			Bukkit.getWorld(artifact.getCoreLocation().getWorld().getUID()).spawnParticle(Particle.FLAME, artifact.getCoreLocation(), 5);
		}
	}
}
