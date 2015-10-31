package me.octxne.tmcpermissions.core;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import me.octxne.tmcpermissions.core.file.files.ConfigurationFile;
import me.octxne.tmcpermissions.utilities.Printer;
import me.octxne.tmcpermissions.utilities.type.PrinterType;

public class TMCPermissions extends JavaPlugin
{
	private static TMCPermissions instance;
	
	private String name, author;
	
	private double version;
	
	@Override
	public void onEnable()
	{
		this.setPlugin(this);
		this.setPluginName("TMCPermissions");
		this.setPluginAuthor("Octxne");
		this.setPluginVersion(0.1D);
		this.registerCommands();
		this.setupFiles();
		
		Printer.printToConsole("v" + this.getPluginVersion() + " developed by " + this.getPluginAuthor() + " has been enabled!", PrinterType.NORMAL);
	}
	
	@Override
	public void onDisable()
	{
		Printer.printToConsole("v" + this.getPluginVersion() + " developed by \"" + this.getPluginAuthor() + "\" has been disabled!", PrinterType.NORMAL);
		
		this.setPlugin(null);
	}
	
	public static TMCPermissions getInstance()
	{
		return instance;
	}
	
	public String getPluginName()
	{
		return this.name;
	}
	
	public String getPluginAuthor()
	{
		return this.author;
	}
	
	public double getPluginVersion()
	{
		return this.version;
	}
	
	public String getPrefix()
	{
		return ConfigurationFile.getFile().getConfiguration().getString("tmcpermissions.settings.prefix");
	}
	
	public void setPlugin(TMCPermissions plugin)
	{
		instance = plugin;
	}
	
	public void setPluginName(String name)
	{
		this.name = name;
	}
	
	public void setPluginAuthor(String author)
	{
		this.author = author;
	}
	
	public void setPluginVersion(Double version)
	{
		this.version = version;
	}
	
	public void setPrefix(String prefix)
	{
		ConfigurationFile.getFile().getConfiguration().set("tmcpermissions.settings.prefix", prefix);
		ConfigurationFile.getFile().saveConfiguration();
	}
	
	public void registerCommand(String name, CommandExecutor executor)
	{
		this.getCommand(name).setExecutor(executor);
	}
	
	private void registerCommands()
	{
		
	}
	
	private void setupFiles()
	{
		ConfigurationFile.loadDefaults();
	}
}
