package me.nootnoot.albioncore.BLL.playersystem.playermenusystem.menus;

import me.nootnoot.albioncore.BLL.artifactsystem.menus.ArtifactMenu;
import me.nootnoot.albioncore.BLL.artifactsystem.menus.ArtifactPouchMenu;
import me.nootnoot.albioncore.BLL.playersystem.playermenusystem.PlayerMenuItem;
import me.nootnoot.albioncore.BLL.utils.ui.gui.GUIManager;
import me.nootnoot.albioncore.BLL.utils.ui.gui.interfaces.NewUserInterface;
import me.nootnoot.albioncore.BLL.utils.ui.gui.interfaces.Slot;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenu extends NewUserInterface {

	private final PlayerMenuItem playerMenuItem;

	public MainMenu(){
		super(54);
		setUIName("Main Menu");
		playerMenuItem = new PlayerMenuItem();
	}

	@Override
	public void define() {
		setSlot(new Slot(10, playerMenuItem.CreateArtifactItem(), ()->{
			GUIManager.getInstance().openInterface(getPlayer(), new ArtifactPouchMenu());
		}));

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
