package com.SpectralVulpine.socialbattery;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.SpectralVulpine.socialbattery.listeners.BatteryListener;
import com.SpectralVulpine.socialbattery.listeners.PlayerListener;
import com.SpectralVulpine.socialbattery.runnables.SocialTick;

public class SocialBattery extends JavaPlugin {
	
	SocialTick sTick;
	public static double defaultCharge = 900;
	public static int defaultDistance = 16;
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
		Bukkit.getPluginManager().registerEvents(new BatteryListener(this), this);
		sTick = new SocialTick();
		sTick.runTaskTimer(this, 0, 20);
	}
}
