package me.nootnoot.luckyblockchestrefill.entities;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;

@Getter
@Setter
public class SimpleLocation {
	private int x;
	private int y;
	private int z;
	private String world;
	private float pitch;
	private float yaw;


	public SimpleLocation(Location loc){
		this.x = (int)loc.getX();
		this.y = (int)loc.getY();
		this.z = (int)loc.getZ();
		this.world = loc.getWorld().getName();
		this.pitch = loc.getPitch();
		this.yaw = loc.getYaw();
	}

	public SimpleLocation(int x, int y, int z, String world, float pitch, float yaw) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
		this.pitch = pitch;
		this.yaw = yaw;
	}

	public SimpleLocation(int x, int y, int z, float yaw) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
	}

	public Location toLocationFull(){
		if(Bukkit.getWorld(world) == null){
			new WorldCreator(world).createWorld();
		}
		return new Location(Bukkit.getWorld(world), x, y, z, pitch, yaw);
	}
	public Location toLocation(){
		if(Bukkit.getWorld(world) == null){
			new WorldCreator(world).createWorld();
		}
		return new Location(Bukkit.getWorld(world), x, y, z);
	}

	public Block getBlock(){
		return toLocation().getBlock();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof SimpleLocation other)) {
			return false;
		} else {
			if (!other.canEqual(this)) {
				return false;
			} else if (Double.compare(this.getX(), other.getX()) != 0) {
				return false;
			} else if (Double.compare(this.getY(), other.getY()) != 0) {
				return false;
			} else if (Double.compare(this.getZ(), other.getZ()) != 0) {
				return false;
			} else if (Float.compare(this.getYaw(), other.getYaw()) != 0) {
				return false;
			} else if (Float.compare(this.getPitch(), other.getPitch()) != 0) {
				return false;
			} else {
				Object this$world = this.getWorld();
				Object other$world = other.getWorld();
				if (this$world == null) {
					return other$world == null;
				} else return this$world.equals(other$world);
			}
		}
	}
	protected boolean canEqual(Object other) {
		return other instanceof SimpleLocation;
	}

	public String toString() {
		return "SimpleLocation(world=" + this.getWorld() + ", x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ", yaw=" + this.getYaw() + ", pitch=" + this.getPitch() + ")";
	}
}
