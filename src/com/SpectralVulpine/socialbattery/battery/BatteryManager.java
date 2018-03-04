package com.SpectralVulpine.socialbattery.battery;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.SpectralVulpine.socialbattery.Personality;
import com.SpectralVulpine.socialbattery.SocialBattery;

public class BatteryManager {
	public static SocialBattery plugin = (SocialBattery) Bukkit.getPluginManager().getPlugin("SocialBattery");

//	private static List<Player> activePlayers;

	public static void assignPersonality(Player p) {
		if (!p.hasMetadata("sbPersonality")) {
			Random rand = new Random();
			MetadataValue meta;
			switch (rand.nextInt(2)) {
			case 0:
				meta = new FixedMetadataValue(plugin, Personality.INTROVERT);
				break;
			case 1:
				meta = new FixedMetadataValue(plugin, Personality.EXTRAVERT);
				break;
			default:
				meta = new FixedMetadataValue(plugin, Personality.INTROVERT);
				break;
			}
			p.setMetadata("sbPersonality", meta);
		}
	}
}
