package com.SpectralVulpine.socialbattery.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.SpectralVulpine.socialbattery.SocialBattery;
import com.SpectralVulpine.socialbattery.events.BatteryEvent;
import com.SpectralVulpine.socialbattery.events.ChargeLevel;

public class BatteryListener implements Listener {

public SocialBattery plugin;
	
	public BatteryListener(SocialBattery sb) {
		plugin = sb;
	}
	
	@EventHandler
	public void onBatteryEvent(BatteryEvent e) throws Exception {
		ChargeLevel charge = e.getCharge();
		switch (charge) {
		case FULL:
			batteryFull(e);
			break;
			
		case HALF:
			batteryHalf(e);
			break;
			
		case LOW:
			batteryLow(e);
			break;
			
		case CRITICAL:
			batteryCritical(e);
			break;
			
		case EMPTY:
			batteryEmpty(e);
			break;
			
		default:
			throw new Exception();
		}
	}
	
	private void batteryFull(BatteryEvent e) {
		if (e.getCharging() == true) {
			Player p = e.getPlayer();
			p.sendMessage("§a[§oSocial §e§lBattery§e] §aYour battery is fully charged.");
		}
	}
	
	private void batteryHalf(BatteryEvent e) {
		if (e.getCharging() == false) {
			Player p = e.getPlayer();
			p.sendMessage("§a[§oSocial §e§lBattery§e] §eYour battery is half charged.");
		}
	}
	
	private void batteryLow(BatteryEvent e) {
		if (e.getCharging() == false) {
			Player p = e.getPlayer();
			p.sendMessage("§a[§oSocial §e§lBattery§e] §6Your battery is running low.");
		}
	}
	
	private void batteryCritical(BatteryEvent e) {
		if (e.getCharging() == false) {
			Player p = e.getPlayer();
			p.sendMessage("§a[§oSocial §e§lBattery§e] §c§oYour battery is at critical levels!");
		}
	}
	
	private void batteryEmpty(BatteryEvent e) {
		Player p = e.getPlayer();
		p.sendMessage("§a[§oSocial §e§lBattery§e] §c§oYour battery has depleted!");
	}
}
