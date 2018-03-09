package com.SpectralVulpine.socialbattery.runnables;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.SpectralVulpine.socialbattery.SocialBattery;
import com.SpectralVulpine.socialbattery.battery.BatteryManager;

public class SocialTick extends BukkitRunnable {

	@Override
	public void run() {
		Set<Player> nearOthers = new HashSet<Player>();
		Set<Player> notNearOthers = new HashSet<Player>();
		for (World world : Bukkit.getWorlds()) {
			for (Player player : world.getPlayers()) {
				for (Player other : world.getPlayers()) {
					if (!player.equals(other) && player.canSee(other) && player.getLocation().distance(other.getLocation()) < SocialBattery.defaultDistance) {
						nearOthers.add(player);
						nearOthers.add(other);
					}
				}
				if (!nearOthers.contains(player)) {
					// If they haven't been found to be near someone yet, they are all alone.
					notNearOthers.add(player);
				}
			}
		}
		BatteryManager.runBatteries(nearOthers, true);
		BatteryManager.runBatteries(notNearOthers, false);
	}
}
