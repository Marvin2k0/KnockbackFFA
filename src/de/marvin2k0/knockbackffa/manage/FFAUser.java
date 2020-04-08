package de.marvin2k0.knockbackffa.manage;

import org.bukkit.entity.Player;

import de.marvin2k0.knockbackffa.KnockbackFFAGame;

public class FFAUser
{
	private final Player player;
	private KnockbackFFAGame game = null;
	
	public FFAUser(Player player)
	{
		this.player = player;
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	public void setGame(KnockbackFFAGame game)
	{
		this.game = game;
	}
	
	public KnockbackFFAGame getGame()
	{
		return this.game;
	}
}
