package me.nootnoot.albioncore.BLL.artifactsystem.menus;

import me.nootnoot.albioncore.BLL.artifactsystem.menus.listeners.ArtifactHandler;
import me.nootnoot.albioncore.BLL.utils.ui.gui.interfaces.NewUserInterface;
import me.nootnoot.albioncore.BLL.utils.ui.gui.interfaces.Slot;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArtifactMenu extends NewUserInterface{

	public ArtifactMenu(){
		super(27);
		setUIName("Artifact Unlock Menu");
	}

	@Override
	public void define() {
		ItemStack item = new ItemStack(Material.OAK_SIGN);
		ItemMeta item_meta = item.getItemMeta();
		item_meta.setDisplayName(ChatColor.AQUA + "Click to fill in your spell.");
		item.setItemMeta(item_meta);

		setSlot(new Slot(13, item, () -> {
			getPlayer().sendMessage(ChatColor.GRAY + "Please type the spell.");
			getPlayer().closeInventory();
			ArtifactHandler.getInSpellMode().add(getPlayer());
		}));

		ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta fillerMeta = filler.getItemMeta();
		fillerMeta.setDisplayName(ChatColor.GRAY + " ");
		filler.setItemMeta(fillerMeta);
		setDefault(filler);
	}

	@Override
	public void select(int i) {
	}
}
