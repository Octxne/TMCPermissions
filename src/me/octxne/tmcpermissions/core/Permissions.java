package me.octxne.tmcpermissions.core;

import org.bukkit.entity.Player;

public class Permissions
{
	public static boolean hasPermission(Player player, String permission)
	{
		return player.hasPermission(permission);
	}
}
