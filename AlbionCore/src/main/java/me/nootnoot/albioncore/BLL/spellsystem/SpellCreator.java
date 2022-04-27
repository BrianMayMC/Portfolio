package me.nootnoot.albioncore.BLL.spellsystem;

import java.util.concurrent.ThreadLocalRandom;

public class SpellCreator {

	public static String GetRandomSpell(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 15; i++){
			int randomNumber = ThreadLocalRandom.current().nextInt(0, SpellEnum.values().length);
			sb.append(SpellEnum.values()[randomNumber]);
			if(i != 14){
				sb.append(" ");
			}
		}
		return sb.toString();
	}
}
