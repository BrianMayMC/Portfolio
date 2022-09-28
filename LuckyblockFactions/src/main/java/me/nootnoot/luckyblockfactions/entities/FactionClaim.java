package me.nootnoot.luckyblockfactions.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.nootnoot.framework.utils.Cuboid;
import org.dynmap.markers.AreaMarker;

@Getter
@RequiredArgsConstructor
@Setter
public class FactionClaim {
	private final Cuboid cuboid;
	private final double price;
	private transient AreaMarker marker;
}
