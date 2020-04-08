package de.marvin2k0.knockbackffa;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import de.marvin2k0.knockbackffa.manage.FFAUser;

public class KnockbackFFAGame
{
	KnockbackFFA kb = KnockbackFFA.getKnockbackFFA();

	private ArrayList<FFAUser> users = new ArrayList<>();
	private Location spawn;
	private GameState state;
	private String name;
	private int slots, players;

	public KnockbackFFAGame(String name, Location spawn, int slots)
	{
		this.spawn = spawn;
		this.name = name;
		this.slots = slots;
		this.players = 0;

		if (!kb.getGames().contains(this))
		{
			kb.getGames().add(this);
			this.state = GameState.LOBBY;
		}
	}

	public void join(FFAUser user)
	{
		if (!users.contains(user))
		{
			// TODO: spielereigenschaften verändern.
			players++;

			users.add(user);
			user.setGame(this);

			for (FFAUser current : users)
				current.getPlayer().sendMessage(kb.getPrefix() + "§b" + user.getPlayer().getName()
						+ " §eist dem Spiel beigetreten. (" + players + "/" + slots + ")");

			if (players >= ((int) slots / 3))
			{
				this.state = GameState.STARTING;
			}
		}
	}

	public void leave(FFAUser user)
	{
		if (users.contains(user))
		{
			// TODO: spielereigenschaften zurücksetzen
			players--;

			users.remove(user);
			user.setGame(null);

			for (FFAUser current : users)
				current.getPlayer().sendMessage(
						kb.getPrefix() + "§a" + user.getPlayer().getName() + " §ehat das Spiel verlassen. (" + players + "/" + slots + ")");

			if (players <= ((int) slots / 3))
			{
				this.state = GameState.LOBBY;
			}
		}
	}

	public void start()
	{
		// TODO: countdown + teleport

		Bukkit.getScheduler().runTaskTimer(kb, new Runnable()
		{
			int time = 30;

			@Override
			public void run()
			{
				if (this.time == 0)
				{
					return;
				}

				for (FFAUser user : users)
				{
					user.getPlayer().sendMessage(kb.getPrefix() + "§b" + this.time + " §eSekunden noch!");
				}

				this.time--;
			}
		}, 0, 20);
	}

	public String getName()
	{
		return name;
	}

	public GameState getState()
	{
		return this.state;
	}

	public Location getSpawn()
	{
		return this.spawn;
	}

	public int getSlots()
	{
		return this.slots;
	}
}

enum GameState
{
	LOBBY, STARTING, INGAME, ENDING;
}
