package com.SpectralVulpine.socialbattery.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.SpectralVulpine.socialbattery.SocialBattery;
import com.SpectralVulpine.socialbattery.battery.BatteryBar;
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
		BatteryBar.fullBattery(e.getPlayer());
	}
	
	private void batteryHalf(BatteryEvent e) {
		
	}
	
	private void batteryLow(BatteryEvent e) {
		
	}
	
	private void batteryCritical(BatteryEvent e) {
		
	}
	
	private void batteryEmpty(BatteryEvent e) {
		
	}
}
