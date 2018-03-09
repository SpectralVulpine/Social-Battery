package com.SpectralVulpine.socialbattery.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BatteryEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private String message;
	private Player player;
	private ChargeLevel charge;
	
	public BatteryEvent(Player p, ChargeLevel c) {
		player = p;
		charge = c;
		message = player.getName() + "'s battery is ";
		switch (charge) {
		case FULL:
			message += "fully charged.";
			break;
			
		case HALF:
			message += "at half charge.";
			break;
			
		case LOW:
			message += "at low charge.";
			break;
			
		case CRITICAL:
			message += "at critical charge.";
			break;
			
		case EMPTY:
			message += "depleted.";
			break;
			
		default:
			message += "unknown.";
			break;
		}
	}
	
	public BatteryEvent(Player p, ChargeLevel t, String msg) {
		player = p;
		charge = t;
		message = msg;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public ChargeLevel getCharge() {
		return charge;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
