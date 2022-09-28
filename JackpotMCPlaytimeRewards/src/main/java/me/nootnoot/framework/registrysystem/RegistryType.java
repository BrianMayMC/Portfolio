package me.nootnoot.framework.registrysystem;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public enum RegistryType {

	LISTENER("Listener");

	@Getter private final String name;

	RegistryType(String name){
		this.name = name;
	}

	@SneakyThrows
	public void register(Class<?> clazz, JavaPlugin instance){
		switch(this){
			case LISTENER -> instance.getServer().getPluginManager().registerEvents((Listener) clazz.getDeclaredConstructor().newInstance(), instance);
		}
	}
}
