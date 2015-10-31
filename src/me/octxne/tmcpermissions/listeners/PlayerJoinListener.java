package me.octxne.tmcpermissions.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.octxne.tmcpermissions.core.file.files.PlayersFile;
import me.octxne.tmcpermissions.core.managers.PermissionsManager;
import me.octxne.tmcpermissions.core.managers.RankManager;

public class PlayerJoinListener implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		
		if (!RankManager.hasRank(player))
		{
			RankManager.setPlayerRank(player, RankManager.getDefaultRank());
		}
		
		RankManager.setupChat();
		RankManager.setupPlayerList();
		
		PermissionsManager.updatePermissions();
		
		if (PlayersFile.getFile().getConfiguration().contains("tmcpermissions.players." + player.getUniqueId() + ".name"))
		{
			if (!PlayersFile.getFile().getConfiguration().getString("tmcpermissions.players." + player.getUniqueId() + ".name").equals(player.getName()))
			{
				PlayersFile.getFile().getConfiguration().set("tmcpermissions.players." + player.getUniqueId() + ".name", player.getName());
				PlayersFile.getFile().saveConfiguration();
			}
		}
	}
}
