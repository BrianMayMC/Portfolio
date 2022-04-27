package me.nootnoot.albioncore.PL.commands;

import me.nootnoot.albioncore.AlbionCore;
import me.nootnoot.albioncore.BLL.playersystem.entities.ABPlayer;
import me.nootnoot.albioncore.BLL.spellsystem.Spell;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestSpell implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = (Player) sender;
		ABPlayer ABP = AlbionCore.getInstance().getAbPlayerManager().GetPlayer(p);

		for(Spell spell : ABP.getSpellPouch().getArtifactSpell().values()){
			p.sendMessage(spell.getSpell());
		}
		p.sendMessage("-------------");
		p.sendMessage(ABP.getSpellPouch().getCageSpell());






		return true;
	}
}
