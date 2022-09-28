package me.nootnoot.jackpotmcitems.interfaces;

import lombok.Getter;
import me.nootnoot.jackpotmcitems.JackpotMCItems;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class FileCreator {

	@Getter
	private final String configName;


	@Getter
	protected YamlConfiguration config = new YamlConfiguration();

	public FileCreator(String configName){
		this.configName = configName;

		createFiles();
	}

	private void createFiles(){
		File configF = new File(JackpotMCItems.getInstance().getDataFolder(), configName + ".yml");

		if(!configF.exists()){
			configF.getParentFile().mkdirs();
			JackpotMCItems.getInstance().saveResource(configName + ".yml", false);
		}

		try{
			config.load(configF);
		}catch(IOException | InvalidConfigurationException e){
			e.printStackTrace();
		}
	}

	public void reload() throws IOException, InvalidConfigurationException {
		File configF = new File(JackpotMCItems.getInstance().getDataFolder(), configName + ".yml");
		config.load(configF);
	}

}
