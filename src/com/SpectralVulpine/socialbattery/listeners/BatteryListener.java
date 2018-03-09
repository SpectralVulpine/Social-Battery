package com.SpectralVulpine.socialbattery.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.SpectralVulpine.socialbattery.Personality;
import com.SpectralVulpine.socialbattery.SocialBattery;
import com.SpectralVulpine.socialbattery.battery.BatteryManager;
import com.SpectralVulpine.socialbattery.events.BatteryEvent;
import com.SpectralVulpine.socialbattery.events.ChargeLevel;

public class BatteryListener implements Listener {

	public SocialBattery plugin;
	private List<String> introDeaths;
	private List<String> extraDeaths;
	Random rand;

	public BatteryListener(SocialBattery sb) {
		plugin = sb;
		introDeaths = new ArrayList<String>();
		extraDeaths = new ArrayList<String>();
		rand = new Random();
		
		introDeaths.add(" was hugged to death");
		introDeaths.add(" was smothered to death");
		introDeaths.add(" was social'd to death");
		
		extraDeaths.add(" died of loneliness");
		extraDeaths.add(" died alone and friendless");
		extraDeaths.add(" was bored to death");
	}

	@EventHandler
	public void onBatteryEvent(BatteryEvent e) {
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
			break;
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();
		if (BatteryManager.getCharge(player) == 0 && e.getDeathMessage().endsWith("died")) {
			Personality personality = BatteryManager.getPersonality(player);
			if (personality == Personality.INTROVERT) {
				e.setDeathMessage(player.getDisplayName() + introDeaths.get(rand.nextInt(introDeaths.size())));
			}
			else if (personality == Personality.EXTRAVERT) {
				e.setDeathMessage(player.getDisplayName() + extraDeaths.get(rand.nextInt(extraDeaths.size())));
			}
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		if (BatteryManager.getPersonality(e.getPlayer()) != Personality.PSYCHOPATH) {
			BatteryManager.rechargePlayer(e.getPlayer());
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
		p.sendMessage("§a[§oSocial §e§lBattery§e] §c§o§lYour battery has depleted!");
		p.setHealth(0);
	}
}
