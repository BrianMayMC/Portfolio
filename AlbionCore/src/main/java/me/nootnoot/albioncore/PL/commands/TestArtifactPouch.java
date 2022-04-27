package me.nootnoot.albioncore.PL.commands;

import me.nootnoot.albioncore.AlbionCore;
import me.nootnoot.albioncore.BLL.artifactsystem.menus.ArtifactMenu;
import me.nootnoot.albioncore.BLL.artifactsystem.menus.ArtifactPouchMenu;
import me.nootnoot.albioncore.BLL.playersystem.entities.ABPlayer;
import me.nootnoot.albioncore.BLL.utils.ui.gui.GUIManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestArtifactPouch implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = (Player) sender;

		GUIManager.getInstance().openInterface(p, new ArtifactPouchMenu());

		return true;
	}
}
