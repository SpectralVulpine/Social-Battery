package com.SpectralVulpine.socialbattery.managers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SpectralVulpine.socialbattery.SocialBattery;

public class CommandManager implements CommandExecutor {
	SocialBattery plugin;
	String help = "§r------------- §a§oSocial §e§lBattery §eHelp §r-------------\n"
			+ "§e/sobat recharge <player> §r- recharge someone's social battery\n";

	public CommandManager(SocialBattery sobat) {
		plugin = sobat;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("sobat") || cmd.getName().equalsIgnoreCase("socialbattery")
				&& sender.hasPermission("socialbattery.op")) {
			if (args.length == 1 && args[0].equalsIgnoreCase("recharge")) {
				sender.sendMessage("§a[§oSocial §e§lBattery§e] §cPlease specify a player to recharge.");
			}
			else if (args.length > 1 && args[0].equalsIgnoreCase("recharge")) {
				Player targetPlayer = Bukkit.getPlayer(args[1]);
				if (targetPlayer == null) {
					sender.sendMessage("§a[§oSocial §e§lBattery§e] §cPlayer not found. Check your spelling and try again.");
				}
				else if (targetPlayer.hasPermission("socialbattery.exempt")) {
					sender.sendMessage("§a[§oSocial §e§lBattery§e] §cThat player does not have a social battery because of permissions.");
				}
				else {
					BatteryManager.rechargePlayer(targetPlayer);
					sender.sendMessage("§a[§oSocial §e§lBattery§e] §aPlayer recharged.");
				}
			}
			else if (args.length == 1 && args[0].equalsIgnoreCase("swap")) {
				sender.sendMessage("§a[§oSocial §e§lBattery§e] §cPlease specify a player to swap.");
			}
			else if (args.length > 1 && args[0].equalsIgnoreCase("swap")) {
				Player targetPlayer = Bukkit.getPlayer(args[1]);
				if (targetPlayer == null) {
					sender.sendMessage("§a[§oSocial §e§lBattery§e] §cPlayer not found. Check your spelling and try again.");
				}
				else if (targetPlayer.hasPermission("socialbattery.exempt")) {
					sender.sendMessage("§a[§oSocial §e§lBattery§e] §cThat player does not have a personality because of permissions.");
				}
				else {
					BatteryManager.swapPersonality(targetPlayer);
					sender.sendMessage("§a[§oSocial §e§lBattery§e] §aPlayer swapped.");
					targetPlayer.sendMessage("§a[§oSocial §e§lBattery§e] §6Your personality has been swapped to §f" + BatteryManager.getPersonality(targetPlayer).name() + ".");
				}
			}
			else if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
				ConfigManager.reload();
				sender.sendMessage("§a[§oSocial §e§lBattery§e] §aConfiguration reloaded.");
			}
			else if (args.length > 0 && args[0].equalsIgnoreCase("reset")) {
				ConfigManager.reset();
				sender.sendMessage("§a[§oSocial §e§lBattery§e] §aConfiguration reset to defaults.");
			}
			else {
				sender.sendMessage(help);
			}
			return true;
		}
		else { return false; }
	}	
}
