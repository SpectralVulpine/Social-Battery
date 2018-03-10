/*
 * Configuration manager for Social Battery plugin
 * Written by SpectralVulpine
 */

package com.SpectralVulpine.socialbattery.managers;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import com.SpectralVulpine.socialbattery.SocialBattery;

public class ConfigManager {
	
	private static double batteryCapacity, effectDistance;
	public static SocialBattery plugin = (SocialBattery) Bukkit.getPluginManager().getPlugin("SocialBattery");
	
	public static void load() {
		// Validate inputs
		plugin.saveDefaultConfig();
		plugin.reloadConfig();
		FileConfiguration config = plugin.getConfig();
		try {
			if (config.getInt("batteryCapacity") < 1) {
				throw new Exception();
			} else {
				batteryCapacity = config.getDouble("batteryCapacity");
			}
		} catch(Exception e) {
			Bukkit.getLogger().log(Level.WARNING, "[Social Battery] batteryCapacity in the configuration file is set to an illegal value! Using default.");
			batteryCapacity = 900;
		}

		try {
			if (config.getInt("effectDistance") < 1) {
				throw new Exception();
			} else {
				effectDistance = config.getDouble("effectDistance");
			}
		} catch(Exception e) {
			Bukkit.getLogger().log(Level.WARNING, "[Social Battery] effectDistance in the configuration file is set to an illegal value! Using default.");
			effectDistance = 16;
		}
	}
	
	public static void reload() {
		// Reload config file from disk
		try {
			load();
			Bukkit.getLogger().log(Level.INFO, "[Social Battery] Configuration reloaded.");
		} catch(Exception e) {
			plugin.saveDefaultConfig();
			plugin.reloadConfig();
			load();
			Bukkit.getLogger().log(Level.WARNING, "[Social Battery] Configuration file invalid! Using defaults instead.");
		}
	}
	
	public static void reset() {
		// Reset config file to default
		File config = new File(plugin.getDataFolder(), "config.yml");
		config.delete();
		plugin.saveDefaultConfig();
		plugin.reloadConfig();
		load();
		Bukkit.getLogger().log(Level.INFO, "[Social Battery] Configuration reset to defaults.");
	}

	public static double getBatteryCapacity() {
		return batteryCapacity;
	}
	
	public static double getEffectDistance() {
		return effectDistance;
	}
}
