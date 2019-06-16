/**
 * 
 */
package com.efrat.example.devops.echoServerFEApp;

import java.io.IOException;
import java.text.MessageFormat;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

/**
 * @author tmeltse
 *
 */
public class EchoServerFEApp
{
	/**
	 * MEP
	 * 
	 * @param args - arguments of main app
	 */
	public static void main(String[] args) 
	{
		System.out.println("Staring main method");

		EchoServerFEMep serverApp = new EchoServerFEMep();
	      
		try 
		{
			// Start the application
			long startTS = System.currentTimeMillis();
			serverApp.startServer();
			
			// Add shutdown hook
			addShutdownHook(serverApp);

			// Measure it
			long endTS = System.currentTimeMillis();
			float elapsedTime = (((float)(endTS - startTS))/(float)1000);
			
			System.out.println(MessageFormat.format("Server started in [{0}] seconds",elapsedTime));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Ending main method");
	}
	
	private static void addShutdownHook(Thread shutdownHook)
	{
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	}
}
