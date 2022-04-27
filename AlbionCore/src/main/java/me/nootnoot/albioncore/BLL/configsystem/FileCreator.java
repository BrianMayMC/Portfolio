package me.nootnoot.albioncore.BLL.configsystem;

import lombok.Getter;
import me.nootnoot.albioncore.AlbionCore;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class FileCreator {

	@Getter
	private final String configName;
	private final String parentDirectory;


	@Getter
	protected YamlConfiguration config = new YamlConfiguration();

	public FileCreator(String configName, String parentDirectory){
		this.configName = configName;
		this.parentDirectory = parentDirectory;

		createFiles();
	}

	private void createFiles(){
		File configF = new File(AlbionCore.getInstance().getDataFolder(), configName + ".yml");

		if(!configF.exists()){
			configF.getParentFile().mkdirs();
			AlbionCore.getInstance().saveResource(configName + ".yml", false);
		}

		try{
			config.load(configF);
		}catch(IOException | InvalidConfigurationException e){
			e.printStackTrace();
		}
	}

}
