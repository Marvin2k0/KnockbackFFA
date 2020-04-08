package de.marvin2k0.knockbackffa;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.marvin2k0.knockbackffa.commands.KnockbackFFACommands;
import de.marvin2k0.knockbackffa.manage.FFAUser;
import de.marvin2k0.knockbackffa.manage.SpawnManager;

/* spiele als spawn speichern.
 */

public class KnockbackFFA extends JavaPlugin
{
	private static KnockbackFFA knockbackFFA;
	private SpawnManager spawnManager;

	private File configFile = new File(this.getDataFolder().getPath() + "\\" + this.getName() + ".yml");
	private FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

	private HashMap<Player, FFAUser> users = new HashMap<>();
	private ArrayList<KnockbackFFAGame> games = new ArrayList<>();

	@Override
	public void onEnable()
	{
		knockbackFFA = this;
		this.spawnManager = new SpawnManager();

		this.getConfig().options().copyDefaults(true);

		this.getConfig().addDefault("prefix", "&a[&bKnockbackFFA&a] §f");
		this.getConfig().addDefault("plugin.loadUpMessage", " Erfolgreich geladen!");
		this.getConfig().addDefault("noperm", " &cDafür hast du keine Berechtigung!");
		this.saveConfig();

		this.getCommand("knockbackffa").setExecutor(new KnockbackFFACommands());

		System.out.println("Config in " + this.getDataFolder().getPath() + "\\" + this.getName() + " gespeichert!");
		System.out.println(this.get("plugin.loadUpMessage"));

		
	}

	public ArrayList<KnockbackFFAGame> getGames()
	{
		return this.games;
	}
	
	public KnockbackFFAGame getGame(String name)
	{
		for (KnockbackFFAGame game : games)
		{
			if (game.getName().equalsIgnoreCase(name))
				return game;
		}
		
		return null;
	}
	
	public void addGame(String name, Location spawn, int slots)
	{
		for (KnockbackFFAGame game : games)
		{
			if (game.getName().equalsIgnoreCase(name))
				break;
		}
		
		this.games.add(new KnockbackFFAGame(name, spawn, slots));
	}

	public FFAUser createUser(Player player)
	{
		if (!this.users.containsKey(player))
		{
			this.users.put(player, new FFAUser(player));

			return this.users.get(player);
		}

		return this.users.get(player);
	}

	public FFAUser getUser(Player player)
	{
		return (this.users.containsKey(player)) ? this.users.get(player) : null;
	}

	public SpawnManager getSpawnManager()
	{
		return this.spawnManager;
	}

	public FileConfiguration getConfig()
	{
		return this.config;
	}

	public void saveConfig()
	{
		try
		{
			config.save(configFile);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public String get(String path)
	{
		return ChatColor.translateAlternateColorCodes('&',
				this.getConfig().getString(path).replace("{prefix}", this.getPrefix()));
	}

	public String getPrefix()
	{
		return ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("prefix"));
	}

	public static KnockbackFFA getKnockbackFFA()
	{
		return knockbackFFA;
	}
}
