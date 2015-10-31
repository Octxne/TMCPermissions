package me.octxne.tmcpermissions.utilities;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messages
{
	public static void inGameCommand(CommandSender sender)
	{
		sender.sendMessage(ChatColor.RED + "That command can only be executed in-game!");
	}
	
	public static void usage(CommandSender sender, String syntax)
	{
		sender.sendMessage(ChatColor.RED + "Usage:\n" + syntax);
	}
	
	public static void insufficientPermissions(CommandSender sender)
	{
		sender.sendMessage(ChatColor.RED + "You do not have the permission to execute that command!");
	}
	
	public static void playerNotFound(CommandSender sender, String player)
	{
		sender.sendMessage(ChatColor.RED + "Player " + player + " not found!");
	}
}
