package me.nootnoot.framework.eventsystem;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class CustomEvent extends Event {
	private static final HandlerList handlers = new HandlerList();

	public CustomEvent() {
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
}
