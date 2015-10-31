package me.octxne.tmcpermissions.core.managers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import me.octxne.tmcpermissions.core.TMCPermissions;
import me.octxne.tmcpermissions.core.file.files.RanksFile;

public class PermissionsManager
{
	public static List<String> getRankPermissions(String name)
	{
		return RanksFile.getFile().getConfiguration().getStringList("tmcpermissions.ranks." + name + ".permissions");
	}
	
	public static void updatePermissions()
	{
		for (Player player : Bukkit.getServer().getOnlinePlayers())
		{
			try
			{
				for (String permissions : getRankPermissions(RankManager.getPlayerRank(player).toLowerCase()))
				{
					PermissionAttachment attachment = player.addAttachment(TMCPermissions.getInstance());
						
					attachment.setPermission(permissions, true);
				}
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}
		}
	}
	
	public static void removePermission(String permission)
	{
		for (Player player : Bukkit.getServer().getOnlinePlayers())
		{
			try
			{
				PermissionAttachment attachment = player.addAttachment(TMCPermissions.getInstance());
				
				attachment.setPermission(permission, false);
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}
		}
	}
}
