package com.SpectralVulpine.socialbattery.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.SpectralVulpine.socialbattery.Personality;
import com.SpectralVulpine.socialbattery.SocialBattery;
import com.SpectralVulpine.socialbattery.battery.BatteryBar;
import com.SpectralVulpine.socialbattery.managers.BatteryManager;

public class PlayerListener implements Listener {
	
	public SocialBattery plugin;
	
	public PlayerListener(SocialBattery sb) {
		plugin = sb;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission("socialbattery.exempt")) {
			BatteryManager.setExempt(e.getPlayer());
			e.getPlayer().sendMessage("§a[§oSocial §e§lBattery§e] §aWelcome to " + Bukkit.getServerName() + "! You are exempt due to permissions. "
					+ "Contact an op if you believe this is in error.");
			return;
		}
		BatteryBar.createBar(e.getPlayer());
		BatteryManager.assignPersonality(e.getPlayer());
		Personality personality = (Personality) e.getPlayer().getMetadata("sbPersonality").get(0).value();
		e.getPlayer().sendMessage("§a[§oSocial §e§lBattery§e] §aWelcome to " + Bukkit.getServerName() + "! You are an " + personality.name() + ".");
		if (personality.name() == Personality.INTROVERT.name()) {
			e.getPlayer().sendMessage("§a[§oSocial §e§lBattery§e] §fAs an §1introvert§f, you will recharge your social battery by §oavoiding people.");
		}
		else if (personality.name() == Personality.EXTRAVERT.name()) {
			e.getPlayer().sendMessage("§a[§oSocial §e§lBattery§e] §fAs an §6extravert§f, you will recharge your social battery by §oseeking out people.");
		}
	}
}
