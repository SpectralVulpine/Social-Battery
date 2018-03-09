package com.SpectralVulpine.socialbattery.battery;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.SpectralVulpine.socialbattery.Personality;
import com.SpectralVulpine.socialbattery.SocialBattery;
import com.SpectralVulpine.socialbattery.events.BatteryEvent;
import com.SpectralVulpine.socialbattery.events.ChargeLevel;

public class BatteryManager {
	public static SocialBattery plugin = (SocialBattery) Bukkit.getPluginManager().getPlugin("SocialBattery");
	private static String batteryMetaName = "sbBattery";
	private static String personalityMetaName = "sbPersonality";

	public static void assignPersonality(Player p) {
		if (!p.hasMetadata(personalityMetaName)) {
			Random rand = new Random();
			MetadataValue personalityMeta;
			switch (rand.nextInt(2)) {
			case 0:
				personalityMeta = new FixedMetadataValue(plugin, Personality.INTROVERT);
				break;
			case 1:
				personalityMeta = new FixedMetadataValue(plugin, Personality.EXTRAVERT);
				break;
			default:
				personalityMeta = new FixedMetadataValue(plugin, Personality.INTROVERT);
				break;
			}
			p.setMetadata(personalityMetaName, personalityMeta);
			p.setMetadata(batteryMetaName, new FixedMetadataValue(plugin, SocialBattery.defaultCharge));
		}
	}
	
	public static void chargeBattery(Player p) {
		int charge = getCharge(p);
		if (charge < SocialBattery.defaultCharge) {
			charge++;
			p.setMetadata(batteryMetaName, new FixedMetadataValue(plugin, charge));
			checkCharge(p);
		}
	}
	
	public static void dischargeBattery(Player p) {
		int charge = getCharge(p);
		if (charge > 0) {
			charge--;
			p.setMetadata(batteryMetaName, new FixedMetadataValue(plugin, charge));
			checkCharge(p);
		}
	}
	
	public static int getCharge(Player p) {
		if (!p.hasMetadata(batteryMetaName)) {
			assignPersonality(p);
		}
		return (int) p.getMetadata(batteryMetaName).get(0).value();
	}
	
	private static void checkCharge(Player p) {
		int charge = getCharge(p);
		BatteryEvent event;
		if (charge == SocialBattery.defaultCharge) {
			event = new BatteryEvent(p, ChargeLevel.FULL);
		}
		else if (charge == SocialBattery.defaultCharge / 2) {
			event = new BatteryEvent(p, ChargeLevel.HALF);
		}
		else if (charge == SocialBattery.defaultCharge / 5) {
			event = new BatteryEvent(p, ChargeLevel.LOW);
		}
		else if (charge == SocialBattery.defaultCharge / 10) {
			event = new BatteryEvent(p, ChargeLevel.CRITICAL);
		}
		else {
			return;
		}
			Bukkit.getServer().getPluginManager().callEvent(event);
	}
}
