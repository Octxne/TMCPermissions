package me.octxne.tmcpermissions.core.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.octxne.tmcpermissions.core.TMCPermissions;
import me.octxne.tmcpermissions.core.file.files.ConfigurationFile;
import me.octxne.tmcpermissions.core.file.files.PlayersFile;
import me.octxne.tmcpermissions.core.file.files.RanksFile;

public class RankManager
{
	public static String getRank(String name)
	{
		return RanksFile.getFile().getConfiguration().getString("tmcpermissions.ranks." + name);
	}
	
	public static String getPlayerRank(Player player)
	{
		return PlayersFile.getFile().getConfiguration().getString("tmcpermissions.players." + player.getUniqueId() + ".rank");
	}
	
	public static String getDefaultRank()
	{
		return ConfigurationFile.getFile().getConfiguration().getString("tmcpermissions.settings.defaultRank");
	}
	
	public static String getDefaultRankColor()
	{
		return ConfigurationFile.getFile().getConfiguration().getString("tmcpermissions.settings.defaultRankColor").replaceAll("&", "§");
	}
	
	public static String getRankColor(String name)
	{
		return RanksFile.getFile().getConfiguration().getString("tmcpermissions.ranks." + name + ".color").replaceAll("&", "§");
	}
	
	public static void addRank(String name)
	{
		RanksFile.getFile().getConfiguration().getConfigurationSection("tmcpermissions.ranks").createSection(name);
		RanksFile.getFile().getConfiguration().set("tmcpermissions.ranks." + name + ".color", getDefaultRankColor());
		RanksFile.getFile().saveConfiguration();
	}
	
	public static void removeRank(String name)
	{
		RanksFile.getFile().getConfiguration().set("tmcpermissions.ranks." + name, null);
		RanksFile.getFile().saveConfiguration();
	}
	
	public static void setPlayerRank(Player player, String name)
	{
		PlayersFile.getFile().getConfiguration().set("tmcplayers.players." + player.getUniqueId() + ".rank", name);
		PlayersFile.getFile().saveConfiguration();
	}
	
	public static void setUnconfiguredPlayerRanks()
	{
		for (Player player : Bukkit.getServer().getOnlinePlayers())
		{
			if (!PlayersFile.getFile().getConfiguration().contains("tmcpermissions.players." + player.getUniqueId() + ".rank"))
			{
				setPlayerRank(player, getDefaultRank());
			}
		}
	}
	
	public static void setDefaultRank(String name)
	{
		ConfigurationFile.getFile().getConfiguration().set("tmcpermissions.settings.defaultRank", name);
		ConfigurationFile.getFile().saveConfiguration();
	}
	
	public static void setDefaultRankColor(String color)
	{
		ConfigurationFile.getFile().getConfiguration().set("tmcpermissions.settings.defaultRankColor", color);
		ConfigurationFile.getFile().saveConfiguration();
	}
	
	public static boolean isRank(Player player, String name)
	{
		return PlayersFile.getFile().getConfiguration().getString("tmcpermissions.players." + player.getUniqueId() + ".rank") == name;
	}
	
	public static boolean hasRank(Player player)
	{
		return PlayersFile.getFile().getConfiguration().contains("tmcpermissions.players." + player.getUniqueId() + ".rank");
	}
	
	public static void setupPlayerList()
	{
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TMCPermissions.getInstance(), new Runnable()
		{
			public void run()
			{
				for (Player player : Bukkit.getServer().getOnlinePlayers())
				{
					player.setPlayerListName(getRankColor(getPlayerRank(player)) + player.getName());
				}
			}
		}, 20L);
	}
	
	public static void setupChat()
	{
		for (Player player : Bukkit.getServer().getOnlinePlayers())
		{
			player.setDisplayName(getRankColor(getPlayerRank(player)) + player.getName());
		}
	}
}
