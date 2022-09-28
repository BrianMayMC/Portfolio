package me.nootnoot.jackpotmcdailyrewards.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class RewardCooldown {
	private String UUID;
	private String id;
	@Setter private long cooldown;
}
