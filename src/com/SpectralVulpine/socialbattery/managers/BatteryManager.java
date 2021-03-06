package com.SpectralVulpine.socialbattery.managers;

import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.SpectralVulpine.socialbattery.Personality;
import com.SpectralVulpine.socialbattery.SocialBattery;
import com.SpectralVulpine.socialbattery.battery.BatteryBar;
import com.SpectralVulpine.socialbattery.events.BatteryEvent;
import com.SpectralVulpine.socialbattery.events.ChargeLevel;

public class BatteryManager {
	public static SocialBattery plugin = (SocialBattery) Bukkit.getPluginManager().getPlugin("SocialBattery");
	private static String batteryStatusMetaName = "sbBattery";
	private static String batteryChargeMetaName = "sbBatteryIsCharging";
	private static String personalityMetaName = "sbPersonality";

	public static void assignPersonality(Player p) {
		if (!p.hasMetadata(personalityMetaName) || !p.hasMetadata(batteryChargeMetaName) || !p.hasMetadata(batteryStatusMetaName)) {
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
				personalityMeta = new FixedMetadataValue(plugin, Personality.PSYCHOPATH);
				break;
			}
			p.setMetadata(personalityMetaName, personalityMeta);
			p.setMetadata(batteryStatusMetaName, new FixedMetadataValue(plugin, SocialBattery.fullCharge));
			p.setMetadata(batteryChargeMetaName, new FixedMetadataValue(plugin, true));
		}
	}
	
	public static void swapPersonality(Player p) {
		if (p.hasMetadata(personalityMetaName)) {
			Personality current = (Personality) p.getMetadata(personalityMetaName).get(0).value();
			if (current == Personality.EXTRAVERT) {
				p.setMetadata(personalityMetaName, new FixedMetadataValue(plugin, Personality.INTROVERT));
			}
			else if (current == Personality.EXTRAVERT) {
				p.setMetadata(personalityMetaName, new FixedMetadataValue(plugin, Personality.EXTRAVERT));
			}
		}
		else {
			assignPersonality(p);
		}
	}
	
	public static void setExempt(Player p) {
		p.setMetadata(personalityMetaName, new FixedMetadataValue(plugin, Personality.PSYCHOPATH));
		p.setMetadata(batteryStatusMetaName, new FixedMetadataValue(plugin, SocialBattery.fullCharge));
		p.setMetadata(batteryChargeMetaName, new FixedMetadataValue(plugin, false));
	}
	
	public static void runBatteries(Set<Player> players, boolean nearOthers) {
		for (Player p : players) {
			Personality personality = getPersonality(p);
			if (personality != Personality.PSYCHOPATH) {
				if ((personality == Personality.INTROVERT && nearOthers) || (personality == Personality.EXTRAVERT && !nearOthers)) {
					dischargeBattery(p);
				}
				else if ((personality == Personality.INTROVERT && !nearOthers) || (personality == Personality.EXTRAVERT && nearOthers)) {
					chargeBattery(p);
				}
				BatteryBar.updateBar(p);
			}
		}
	}

	private static void chargeBattery(Player p) {
		double charge = getCharge(p);
		if (charge < SocialBattery.fullCharge) {
			charge++;
			p.setMetadata(batteryStatusMetaName, new FixedMetadataValue(plugin, charge));
			p.setMetadata(batteryChargeMetaName, new FixedMetadataValue(plugin, true));
			checkCharge(p);
		}
	}

	private static void dischargeBattery(Player p) {
		double charge = getCharge(p);
		if (charge > 0) {
			charge--;
			p.setMetadata(batteryStatusMetaName, new FixedMetadataValue(plugin, charge));
			p.setMetadata(batteryChargeMetaName, new FixedMetadataValue(plugin, false));
			checkCharge(p);
		}
	}

	public static double getCharge(Player p) {
		if (!p.hasMetadata(batteryStatusMetaName)) {
			assignPersonality(p);
		}
		return (double) p.getMetadata(batteryStatusMetaName).get(0).value();
	}
	
	public static Personality getPersonality(Player p) {
		if (!p.hasMetadata(personalityMetaName)) {
			assignPersonality(p);
		}
		return (Personality) p.getMetadata(personalityMetaName).get(0).value();
	}
	
	public static boolean isCharging(Player p) {
		if (!p.hasMetadata(batteryChargeMetaName)) {
			assignPersonality(p);
		}
		return (boolean) p.getMetadata(batteryChargeMetaName).get(0).value();
	}

	private static void checkCharge(Player p) {
		double charge = getCharge(p);
		BatteryEvent event;
		if (charge == SocialBattery.fullCharge) {
			event = new BatteryEvent(p, ChargeLevel.FULL);
		}
		else if (charge == SocialBattery.fullCharge / 2) {
			event = new BatteryEvent(p, ChargeLevel.HALF);
		}
		else if (charge == SocialBattery.fullCharge / 5) {
			event = new BatteryEvent(p, ChargeLevel.LOW);
		}
		else if (charge == SocialBattery.fullCharge / 10) {
			event = new BatteryEvent(p, ChargeLevel.CRITICAL);
		}
		else if (charge == 0) {
			event = new BatteryEvent(p, ChargeLevel.EMPTY);
		}
		else {
			return;
		}
		Bukkit.getServer().getPluginManager().callEvent(event);
	}
	
	public static void rechargePlayer(Player p) {
		p.setMetadata(batteryStatusMetaName, new FixedMetadataValue(plugin, SocialBattery.fullCharge));
		BatteryBar.updateBar(p);
	}
}
