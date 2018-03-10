package com.SpectralVulpine.socialbattery;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.SpectralVulpine.socialbattery.listeners.BatteryListener;
import com.SpectralVulpine.socialbattery.listeners.PlayerListener;
import com.SpectralVulpine.socialbattery.managers.CommandManager;
import com.SpectralVulpine.socialbattery.managers.ConfigManager;
import com.SpectralVulpine.socialbattery.runnables.SocialTick;

public class SocialBattery extends JavaPlugin {
	
	SocialTick sTick;
	CommandManager exe;
	public static double fullCharge;
	public static double effectDistance;
	
	public void onEnable() {
		ConfigManager.load();
		fullCharge = ConfigManager.getBatteryCapacity();
		effectDistance = ConfigManager.getEffectDistance();
		exe = new CommandManager(this);
		this.getCommand("sobat").setExecutor(exe);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
		Bukkit.getPluginManager().registerEvents(new BatteryListener(this), this);
		sTick = new SocialTick();
		sTick.runTaskTimer(this, 0, 20);
	}
}
