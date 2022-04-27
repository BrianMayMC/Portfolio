package me.nootnoot.albioncore.BLL.artifactsystem.tasks;

import me.nootnoot.albioncore.AlbionCore;
import me.nootnoot.albioncore.BLL.artifactsystem.entities.Artifact;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveTask extends BukkitRunnable {
	private final Artifact artifact;

	public RemoveTask(Artifact artifact){
		this.artifact = artifact;
	}
	@Override
	public void run() {
		AlbionCore.getInstance().getArtifactManager().RemoveArtifact(artifact);
	}
}
