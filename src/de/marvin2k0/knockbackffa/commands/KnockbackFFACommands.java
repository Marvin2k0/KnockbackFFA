package de.marvin2k0.knockbackffa.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.marvin2k0.knockbackffa.KnockbackFFA;

public class KnockbackFFACommands implements CommandExecutor
{
	KnockbackFFA kb = KnockbackFFA.getKnockbackFFA();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (args.length < 1)
		{
			sender.sendMessage("§a======" + kb.getPrefix() + "======");

			if (sender.hasPermission("kb.admin"))

			{
				sender.sendMessage("§e/kb set spawn <name> §f- §7Setzt einen Spawn");
				sender.sendMessage("§e/kb del spawn <name> §f- §7Löscht einen Spawn");
				sender.sendMessage("§e/kb kit <name> §f- §7Erstell ein Kit");
				sender.sendMessage("§e/kb del kit <name> §f- §7Löscht ein Kit");
			}

			sender.sendMessage("§e/kb join §f- §7Tritt einem Spiel bei");
			sender.sendMessage("§e/kb leave §f- §7Verlasse ein Spiel");
			sender.sendMessage("§a======" + kb.getPrefix() + "======");

			return true;
		}
		if (args[0].equalsIgnoreCase("join"))
		{
			if (!(sender instanceof Player))
			{
				sender.sendMessage(kb.getPrefix() + " §cNur für Spieler!");

				return true;
			}

			Player player = (Player) sender;

			if (!player.hasPermission("kb.join"))
			{
				player.sendMessage(kb.getPrefix() + kb.get("noperm"));

				return true;
			}

			if (args.length != 2)
			{
				player.sendMessage(kb.getPrefix() + " §eBitte benutze §c/kb join <name>");

				return true;
			}

			String name = args[1];

			if (!kb.getSpawnManager().isSet(name))
			{
				player.sendMessage(kb.getPrefix() + "§c" + name + " §eist kein existierendes Spiel!");

				return true;
			}

			Location loc = kb.getSpawnManager().getSpawn(name);

			kb.getGame(name).join(kb.createUser(player));
			player.teleport(loc);
			player.sendMessage(kb.getPrefix() + " §eDu bist dem Spiel §a" + name + " §ebeigetreten!");

			// TODO: Spiel joinen + User-Klasse erstellen.

			return true;
		}
		else if (args[0].equalsIgnoreCase("leave"))
		{
			if (!(sender instanceof Player))
			{
				sender.sendMessage(kb.getPrefix() + " §cNur für Spieler!");

				return true;
			}

			Player player = (Player) sender;

			if (!player.hasPermission("kb.leave"))
			{
				player.sendMessage(kb.getPrefix() + kb.get("noperm"));

				return true;
			}

			if (args.length != 2)
			{
				player.sendMessage(kb.getPrefix() + " §eBitte benutze §c/kb leave <name>");

				return true;
			}

			String name = args[1];

			player.sendMessage(kb.getPrefix() + " §eDu hast das Spiel §a" + name + " §everlassen!");
			kb.getGame(name).leave(kb.createUser(player));
			//TODO: spielerposition vor joinen spiechern, dann hier zurückteleportieren : player.teleport(loc);
			return true;
		}
		else if (args[0].equalsIgnoreCase("set"))
		{
			if (!sender.hasPermission("kb.set"))
			{
				sender.sendMessage(kb.getPrefix() + kb.get("noperm"));

				return true;
			}

			if (!(sender instanceof Player))
			{
				sender.sendMessage(kb.getPrefix() + " §cDas können nur Spieler!");

				return true;
			}

			if (args.length != 3)
			{
				sender.sendMessage(kb.getPrefix() + " §eBitte benutze §c/kb set spawn <name>");

				return true;
			}

			Player player = (Player) sender;
			String name = args[2];

			/* Spawn setzen */
			kb.getSpawnManager().setSpawn(player.getLocation().getX(), player.getLocation().getY(),
					player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch(),
					player.getLocation().getWorld().getName(), name);

			player.sendMessage(kb.getPrefix() + " §eSpawn §a" + name + " §egesetzt!");

			return true;
		}
		else if (args[0].equalsIgnoreCase("del"))
		{
			if (!sender.hasPermission("kb.del"))
			{
				sender.sendMessage(kb.getPrefix() + kb.get("noperm"));

				return true;
			}

			if (args.length != 3)
			{
				sender.sendMessage(kb.getPrefix() + " §eBitte benutze §c/kb del spawn <name>");

				return true;
			}

			String name = args[2];

			if (kb.getSpawnManager().del(name))
			{
				sender.sendMessage(kb.getPrefix() + " §eSpawn §a" + name + " §ewurde gelöscht!");
			}
			else
			{
				sender.sendMessage(kb.getPrefix() + " §a" + name + " §eist kein existierender Spawn!");
			}

			return true;
		}

		// TODO: Andere Befehle hinzufügen

		return false;
	}
}
