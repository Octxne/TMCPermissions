package me.octxne.tmcpermissions.core.file.files;

import me.octxne.tmcpermissions.core.file.Filer;
import me.octxne.tmcpermissions.core.managers.RankManager;

public class RanksFile
{
	private static Filer file = new Filer("ranks.yml");
	
	public static Filer getFile()
	{
		return file;
	}
	
	public static void loadDefaults()
	{
		getFile().addDefault("tmcpermissions.ranks", RankManager.getDefaultRank());
		getFile().addDefault("tmcpermissions.ranks.Default.color", RankManager.getDefaultRankColor());
	}
}
