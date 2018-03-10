package com.SpectralVulpine.socialbattery.battery;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import com.SpectralVulpine.socialbattery.SocialBattery;
import com.SpectralVulpine.socialbattery.managers.BatteryManager;

public class BatteryBar {
	
	private static Map<Player, BossBar> bars = new HashMap<Player, BossBar>();
	
	public static void createBar(Player p) {
		BossBar bbar = Bukkit.createBossBar("Social Battery", BarColor.GREEN, BarStyle.SEGMENTED_10);
		bbar.addPlayer(p);
		bars.put(p, bbar);
	}
	
	public static void updateBar(Player p) {
		double charge = BatteryManager.getCharge(p);
		double percentage = charge / SocialBattery.fullCharge;
		BossBar bar = bars.get(p);
		bar.setProgress(percentage);
		if (percentage <= 0.1) {
			bar.setColor(BarColor.RED);
		}
		else if (percentage <= 0.5) {
			bar.setColor(BarColor.YELLOW);
		}
		else if (percentage < 1) {
			bar.setColor(BarColor.GREEN);
		}
		else {
			bar.setColor(BarColor.WHITE);
		}
	}
	
	public static void removeBar(Player p) {
		BossBar bar = bars.get(p);
		bar.removeAll();
		bars.remove(p);
	}
}
