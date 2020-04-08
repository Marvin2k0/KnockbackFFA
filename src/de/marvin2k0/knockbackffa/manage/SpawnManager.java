package de.marvin2k0.knockbackffa.manage;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.marvin2k0.knockbackffa.KnockbackFFA;
import de.marvin2k0.knockbackffa.KnockbackFFAGame;

public class SpawnManager
{
	KnockbackFFA kb = KnockbackFFA.getKnockbackFFA();

	File spawnConfigFile = new File(kb.getDataFolder().getPath() + "\\spawn.yml");
	FileConfiguration spawnConfig = YamlConfiguration.loadConfiguration(spawnConfigFile);

	public Location getSpawn(String name)
	{
		if (!isSet(name))
			return null;

		return new Location(Bukkit.getWorld(spawnConfig.getString(name + ".world")), spawnConfig.getDouble(name + ".x"),
				spawnConfig.getDouble(name + ".y"), spawnConfig.getDouble(name + ".z"),
				(float) spawnConfig.getDouble(name + ".yaw"), (float) spawnConfig.getDouble(name + ".pitch"));
	}

	public boolean isSet(String name)
	{
		return spawnConfig.isSet(name);
	}

	public void setSpawn(double x, double y, double z, float yaw, float pitch, String world, String name)
	{
		Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
		
		spawnConfig.set(name + ".world", world);
		spawnConfig.set(name + ".x", x);
		spawnConfig.set(name + ".y", y);
		spawnConfig.set(name + ".z", z);
		spawnConfig.set(name + ".yaw", yaw);
		spawnConfig.set(name + ".pitch", pitch);
		
		kb.addGame(name, loc, 10);
		
		for (KnockbackFFAGame game : kb.getGames())
			System.out.println(game.getName() + "\n");

		saveConfig();
	}

	public boolean del(String name)
	{
		if (!spawnConfig.isSet(name))
			return false;

		spawnConfig.set(name, null);

		saveConfig();

		return true;
	}

	private void saveConfig()
	{
		try
		{
			spawnConfig.save(spawnConfigFile);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
