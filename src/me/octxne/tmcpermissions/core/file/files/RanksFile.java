package me.octxne.tmcpermissions.core.file.files;

import me.octxne.tmcpermissions.core.file.Filer;

public class RanksFile
{
	private static Filer file = new Filer("ranks.yml");
	
	public static Filer getFile()
	{
		return file;
	}
}
