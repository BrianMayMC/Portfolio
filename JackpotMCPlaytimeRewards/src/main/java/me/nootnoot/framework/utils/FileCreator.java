package me.nootnoot.framework.utils;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public abstract class FileCreator {

	@Getter
	private final String configName;

	@Getter
	protected YamlConfiguration config = new YamlConfiguration();

	private final JavaPlugin instance;

	public FileCreator(String path, String configName, JavaPlugin instance){
		this.configName = configName;
		this.instance = instance;

		try{
			createFiles(path);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void createFiles(String path) throws IOException {
		File configF = new File(instance.getDataFolder().getAbsolutePath() + path, configName + ".yml");

		if(!configF.exists()){
			configF.getParentFile().mkdirs();
			String replaced = path.replaceFirst("/", "");
			instance.saveResource(replaced + "/" + configName + ".yml", false);
		}

		try{
			config.load(configF);
		}catch(IOException | InvalidConfigurationException e){
			e.printStackTrace();
		}
	}

	public void reload() throws IOException, InvalidConfigurationException {
		File configF = new File(instance.getDataFolder(), configName + ".yml");
		config.load(configF);
	}

}
