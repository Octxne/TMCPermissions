package me.octxne.tmcpermissions.core.file.files;

import me.octxne.tmcpermissions.core.TMCPermissions;
import me.octxne.tmcpermissions.core.file.Filer;

public class ConfigurationFile
{
	private static Filer file = new Filer("config.yml");
	
	public static Filer getFile()
	{
		return file;
	}
	
	public static void loadDefaults()
	{
		getFile().addDefault("tmcpermissions.settings.prefix", "[" + TMCPermissions.getInstance().getPluginName() + "]");
	}
}
