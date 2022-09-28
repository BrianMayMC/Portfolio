package me.nootnoot.jackpotmcitems.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class Utils {

	public static TextComponent c(String s){
		return Component.text("").decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE).append(LegacyComponentSerializer.legacyAmpersand().deserialize(s));
	}

}
