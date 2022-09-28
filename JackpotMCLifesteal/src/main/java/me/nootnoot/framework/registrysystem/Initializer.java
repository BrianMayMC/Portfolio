package me.nootnoot.framework.registrysystem;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Initializer {

	public Initializer(JavaPlugin instance, String fullPackage) {
		this.instance = instance;
		this.fullPackage = fullPackage;
		initialize();
	}

	private final JavaPlugin instance;
	private final String fullPackage;

	public void initialize(){
		List<RegistryType> types = Arrays.stream(RegistryType.values()).collect(Collectors.toList());

		types.forEach(type -> getClasses().stream().filter(aClass -> aClass.getDeclaredAnnotation(Registry.class).type().equals(type)).forEach(aClass -> type.register(aClass, instance)));
	}

	public List<Class<?>> getClasses(){
		return new Reflections(fullPackage).getTypesAnnotatedWith(Registry.class).stream().toList();
	}
}
