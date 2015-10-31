package me.octxne.tmcpermissions.utilities;

import me.octxne.tmcpermissions.core.TMCPermissions;
import me.octxne.tmcpermissions.utilities.type.PrinterType;

public class Printer
{
	public static void printToConsole(String text, PrinterType type)
	{
		switch (type)
		{
		case NORMAL:
			System.out.println(TMCPermissions.getInstance().getPrefix() + " " + text);

			break;

		case WARNING:
			System.out.println(TMCPermissions.getInstance().getPrefix() + " (WARNING) " + text);

			break;

		case ERROR:
			System.out.println(TMCPermissions.getInstance().getPrefix() + " (ERROR) " + text);
		}
	}
}
