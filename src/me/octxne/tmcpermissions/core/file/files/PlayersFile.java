package me.octxne.tmcpermissions.core.file.files;

import me.octxne.tmcpermissions.core.file.Filer;

public class PlayersFile
{
	private static Filer file = new Filer("players.yml");
	
	public static Filer getFile()
	{
		return file;
	}
}
