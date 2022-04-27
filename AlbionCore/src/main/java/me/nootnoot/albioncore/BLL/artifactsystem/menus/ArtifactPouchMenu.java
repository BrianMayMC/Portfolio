package me.nootnoot.albioncore.BLL.artifactsystem.menus;

import me.nootnoot.albioncore.AlbionCore;
import me.nootnoot.albioncore.BLL.artifactsystem.entities.Artifact;
import me.nootnoot.albioncore.BLL.playersystem.entities.ABPlayer;
import me.nootnoot.albioncore.BLL.utils.ui.gui.interfaces.NewUserInterface;
import me.nootnoot.albioncore.BLL.utils.ui.gui.interfaces.Slot;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ArtifactPouchMenu extends NewUserInterface {

	public ArtifactPouchMenu(){
		super(27);
		setUIName("Artifact Pouch");
	}
	@Override
	public void define() {
		int counter = 10;

		for(Artifact artifact : AlbionCore.getInstance().getArtifactManager().getArtifacts()){
			ItemStack item;
			ItemMeta itemMeta;

			if(artifact.getStatus().equalsIgnoreCase("locked")){
				item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
				itemMeta = item.getItemMeta();
				ArrayList<String> lore = new ArrayList<>();
				for(String s : artifact.getLore()){
					lore.add(s);
				}
				lore.add("");
				lore.add(ChatColor.RED + "This artifact is still locked.");
				itemMeta.setLore(lore);
			}else{
				item = new ItemStack(Material.BEACON);
				itemMeta = item.getItemMeta();
				ArrayList<String> lore = new ArrayList<>();
				for(String s : artifact.getLore()){
					lore.add(s);
				}
				lore.add("");
				lore.add(ChatColor.GREEN + "This artifact is unlocked.");
				itemMeta.setLore(lore);
			}
			itemMeta.setDisplayName(artifact.getName());

			item.setItemMeta(itemMeta);
			setSlot(new Slot(counter, item));
			counter = counter + 2;
		}

		ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta fillerMeta = filler.getItemMeta();
		fillerMeta.setDisplayName(ChatColor.GRAY + " ");
		filler.setItemMeta(fillerMeta);
		setDefault(filler);
	}

	@Override
	public void select(int slot) {

	}
}
