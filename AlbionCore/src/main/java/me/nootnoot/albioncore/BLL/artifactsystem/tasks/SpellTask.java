package me.nootnoot.albioncore.BLL.artifactsystem.tasks;

import me.nootnoot.albioncore.AlbionCore;
import me.nootnoot.albioncore.BLL.artifactsystem.entities.Artifact;
import me.nootnoot.albioncore.BLL.artifactsystem.menus.listeners.ArtifactHandler;
import me.nootnoot.albioncore.BLL.utils.ui.gui.GUIManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpellTask extends BukkitRunnable {

	private final Player p;
	private final Artifact artifact;

	public SpellTask(Player p, Artifact artifact){
		this.p = p;
		this.artifact = artifact;
	}
	@Override
	public void run(){
		if(!ArtifactHandler.getInSpellMode().contains(p)){
			AlbionCore.getInstance().getArtifactManager().RemoveArtifact(artifact);
		}
	}
}
