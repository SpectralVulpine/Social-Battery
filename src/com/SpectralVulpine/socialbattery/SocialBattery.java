package com.SpectralVulpine.socialbattery;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.SpectralVulpine.socialbattery.listeners.PlayerListener;
import com.SpectralVulpine.socialbattery.runnables.SocialTick;

public class SocialBattery extends JavaPlugin {
	
	SocialTick sTick;
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
		sTick = new SocialTick();
		sTick.runTaskTimer(this, 0, 20);
	}
}
