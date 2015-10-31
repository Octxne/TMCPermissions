package me.octxne.tmcpermissions.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.octxne.tmcpermissions.core.file.files.ConfigurationFile;
import me.octxne.tmcpermissions.core.managers.RankManager;

public class AsyncPlayerChatListener implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		
		String message = event.getMessage();
		
		RankManager.setupChat();
		RankManager.setupPlayerList();
		
		event.setFormat(player.getDisplayName() + " " + ConfigurationFile.getFile().getConfiguration().getString("tmcpermissions.settings.chat.splitter").replaceAll("&", "§") + " " + ChatColor.RESET + message);
	}
}
