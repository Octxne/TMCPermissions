package me.octxne.tmcpermissions.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.octxne.tmcpermissions.core.Permissions;
import me.octxne.tmcpermissions.core.file.files.RanksFile;
import me.octxne.tmcpermissions.core.managers.PermissionsManager;
import me.octxne.tmcpermissions.core.managers.RankManager;
import me.octxne.tmcpermissions.utilities.Messages;

public class TMCPermissionsCommand implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments)
	{
		if (!(sender instanceof Player))
		{
			Messages.inGameCommand(sender);
		}
		else
		{
			Player player = (Player) sender;
			
			if (arguments.length == 0 || arguments.length > 4)
			{
				Messages.usage(player, "/tmcpermissions <player>\n/tmcpermissions set <player> <rank>\n/tmcpermissions add permission <rank> <permission>\n/tmcpermissions remove permission <rank> <permission>\n/tmcpermissions add <rank>\n/tmcpermissions remove <rank>\n/tmcpermissions set default <rank>\n/tmcpermissions set default color <color>\n/tmcpermissions set color <rank>");
			}
			else if (arguments.length == 1)
			{
				Player target = Bukkit.getServer().getPlayer(arguments[0]);
				
				if (target == null || !target.isOnline())
				{
					Messages.playerNotFound(player, arguments[0]);
				}
				else
				{
					if (Permissions.hasPermission(player, "tmcpermissions.view"))
					{
						player.sendMessage(ChatColor.GRAY + "Player " + target.getDisplayName() + ChatColor.GRAY + "'s rank" + ChatColor.DARK_GRAY + ": " + RankManager.getRankColor(RankManager.getPlayerRank(player)) + RankManager.getPlayerRank(player));
					}
					else
					{
						Messages.insufficientPermissions(player);
					}
				}
			}
			else if (arguments.length == 2)
			{
				if (arguments[0].equalsIgnoreCase("add"))
				{
					if (Permissions.hasPermission(player, "tmcpermissions.add"))
					{
						if (RankManager.rankExists(arguments[1]))
						{
							player.sendMessage(ChatColor.RED + "Rank " + arguments[1] + " already exists!");
						}
						else
						{
							RankManager.addRank(arguments[1]);
							
							player.sendMessage(ChatColor.GRAY + "Rank " + ChatColor.RED + arguments[1] + ChatColor.GRAY + " has been " + ChatColor.GREEN + "created" + ChatColor.GRAY + "!");
						}
					}
					else
					{
						Messages.insufficientPermissions(player);
					}
				}
				else if (arguments[0].equalsIgnoreCase("remove"))
				{
					if (Permissions.hasPermission(player, "tmcpermissions.remove"))
					{
						if (!RankManager.rankExists(arguments[1]))
						{
							player.sendMessage(ChatColor.RED + "Rank " + arguments[1] + " does not exist!");
						}
						else
						{
							RankManager.removeRank(arguments[1]);
							
							player.sendMessage(ChatColor.GRAY + "Rank " + ChatColor.RED + arguments[1] + ChatColor.GRAY + " has been " + ChatColor.RED + "removed" + ChatColor.GRAY + "!");
						}
					}
					else
					{
						Messages.insufficientPermissions(player);
					}
				}
			}
			else if (arguments.length == 3)
			{
				if (arguments[0].equalsIgnoreCase("set"))
				{
					if (arguments[1].equalsIgnoreCase("default"))
					{
						if (RankManager.rankExists(arguments[2]))
						{
							RankManager.setDefaultRank(arguments[2]);
							
							player.sendMessage(ChatColor.GRAY + "You have " + ChatColor.GREEN + "set " + ChatColor.GRAY + "the default rank to" + ChatColor.DARK_GRAY + ": " + RankManager.getRankColor(RankManager.getDefaultRank()));
						}
						else
						{
							player.sendMessage(ChatColor.RED + "Rank " + arguments[2] + " does not exist!");
						}
					}
					else
					{
						Player target = Bukkit.getServer().getPlayer(arguments[1]);
						
						if (target == null || !target.isOnline())
						{
							Messages.playerNotFound(player, arguments[1]);
						}
						else
						{
							if (RankManager.rankExists(arguments[2]))
							{
								RankManager.setPlayerRank(target, arguments[2]);
								RankManager.setupChat();
								RankManager.setupPlayerList();
								
								Bukkit.getServer().broadcastMessage(ChatColor.GRAY + "Player " + player.getDisplayName() + ChatColor.GRAY + " has " + ChatColor.GREEN + "set " + ChatColor.GRAY + "player " + target.getDisplayName() + ChatColor.GRAY + "'s rank to" + ChatColor.DARK_GRAY + ": " + RankManager.getRankColor(RankManager.getPlayerRank(target)) + RankManager.getPlayerRank(target));
							}
							else
							{
								player.sendMessage(ChatColor.RED + "Rank " + arguments[2] + " does not exist!");
							}
						}
					}
				}
				else
				{
					Messages.usage(player, "/tmcpermissions <player>\n/tmcpermissions set <player> <rank>\n/tmcpermissions add permission <rank> <permission>\n/tmcpermissions remove permission <rank> <permission>\n/tmcpermissions add <rank>\n/tmcpermissions remove <rank>\n/tmcpermissions set default <rank>\n/tmcpermissions set default color <color>\n/tmcpermissions set color <rank>");
				}
			}
			else if (arguments.length == 4)
			{
				if (arguments[0].equalsIgnoreCase("add"))
				{
					if (arguments[1].equalsIgnoreCase("permission"))
					{
						if (Permissions.hasPermission(player, "tmcpermissions.permissions.add"))
						{
							if (RankManager.rankExists(arguments[2]))
							{
								if (PermissionsManager.getRankPermissions(arguments[2]).contains(arguments[3]))
								{
									player.sendMessage(ChatColor.RED + "Rank " + arguments[2] + " already has the following permission: " + arguments[3]);
								}
								else
								{
									PermissionsManager.getRankPermissions(arguments[2]).add(arguments[3]);
									
									RanksFile.getFile().getConfiguration().set("tmcpermissions.ranks." + arguments[2] + ".permissions", PermissionsManager.getRankPermissions(arguments[2]));
									RanksFile.getFile().saveConfiguration();
									
									PermissionsManager.updatePermissions();
									
									player.sendMessage(ChatColor.GRAY + "You have " + ChatColor.GREEN + "added " + ChatColor.GRAY + "the following permission to rank" + RankManager.getRankColor(arguments[2]) + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + arguments[3]);
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "Rank " + arguments[2] + " does not exist!");
							}
						}
						else
						{
							Messages.insufficientPermissions(player);
						}
					}
				}
				else if (arguments[0].equalsIgnoreCase("remove"))
				{
					if (arguments[1].equalsIgnoreCase("permission"))
					{
						if (Permissions.hasPermission(player, "tmcpermissions.permissions.remove"))
						{
							if (RankManager.rankExists(arguments[2]))
							{
								if (!PermissionsManager.getRankPermissions(arguments[2]).contains(arguments[3]))
								{
									player.sendMessage(ChatColor.RED + "Rank " + arguments[2] + " does not have the following permission: " + arguments[3]);
								}
								else
								{
									PermissionsManager.removePermission(arguments[3]);
									
									PermissionsManager.getRankPermissions(arguments[2]).remove(arguments[3]);
									
									RanksFile.getFile().getConfiguration().set("tmcpermissions.ranks." + arguments[2] + ".permissions", PermissionsManager.getRankPermissions(arguments[2]));
									RanksFile.getFile().saveConfiguration();
									
									player.sendMessage(ChatColor.GRAY + "You have " + ChatColor.RED + "removed " + ChatColor.GRAY + "the following permission to rank" + RankManager.getRankColor(arguments[2]) + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + arguments[3]);
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "Rank " + arguments[2] + " does not exist!");
							}
						}
						else
						{
							Messages.insufficientPermissions(player);
						}
					}
				}
				else if (arguments[0].equalsIgnoreCase("set"))
				{
					if (arguments[1].equalsIgnoreCase("default"))
					{
						if (arguments[2].equalsIgnoreCase("color"))
						{
							RankManager.setDefaultRankColor(arguments[3]);
							
							player.sendMessage(ChatColor.GRAY + "You have " + ChatColor.GREEN + "set " + ChatColor.GRAY + "the default rank color to" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + arguments[3]);
						}
					}
					else if (arguments[1].equalsIgnoreCase("color"))
					{
						if (RankManager.rankExists(arguments[2]))
						{
							RankManager.setRankColor(arguments[2], arguments[3]);
							
							player.sendMessage(ChatColor.GRAY + "You have " + ChatColor.GREEN + "set " + ChatColor.GRAY + "rank " + RankManager.getRankColor(arguments[2]) + arguments[2] + ChatColor.GRAY + "'s color to" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + arguments[3]);
						}
						else
						{
							player.sendMessage(ChatColor.RED + "Rank " + arguments[2] + " does not exist!");
						}
					}
					else
					{
						Messages.usage(player, "/tmcpermissions <player>\n/tmcpermissions set <player> <rank>\n/tmcpermissions add permission <rank> <permission>\n/tmcpermissions remove permission <rank> <permission>\n/tmcpermissions add <rank>\n/tmcpermissions remove <rank>\n/tmcpermissions set default <rank>\n/tmcpermissions set default color <color>\n/tmcpermissions set color <rank>");
					}
				}
				else
				{
					Messages.usage(player, "/tmcpermissions <player>\n/tmcpermissions set <player> <rank>\n/tmcpermissions add permission <rank> <permission>\n/tmcpermissions remove permission <rank> <permission>\n/tmcpermissions add <rank>\n/tmcpermissions remove <rank>\n/tmcpermissions set default <rank>\n/tmcpermissions set default color <color>\n/tmcpermissions set color <rank>");
				}
			}
		}
		
		return false;
	}
}
