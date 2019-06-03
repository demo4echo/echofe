/**
 * 
 */
package com.efrat.example.devops.echoServerFEApp;

import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author tmeltse
 *
 */
public class EchoServerFEApp extends Thread
{
	private static final int DEFAULT_APP_PORT = 9999*0;

	
	private HttpServer server;
	
	/**
	 * MEP
	 * 
	 * @param args - arguments of main app
	 */
	public static void main(String[] args) 
	{
		System.out.println("Staring main method");

		EchoServerFEApp app = new EchoServerFEApp();
		
		try 
		{
			// Start the application
			long startTS = System.currentTimeMillis();
			app.startServer();
			
			// Add shutdown hook
			addShutdownHook(app);

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
	
	/**
	 * CTOR 
	 */
	public EchoServerFEApp() 
	{
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#start()
	 */
	@Override
	public synchronized void start() 
	{
		// TODO Auto-generated method stub
		super.start();
		
		System.out.println("Process has terminated");
	}

	/**
	 * 
	 * @throws IOException
	 */
	void startServer() throws IOException
	{
		// Get operational port from a designated environment variable
		String designatedApplicationPort = System.getenv("INTERNAL_PORT_ENV_VAR");
		
		// Determine the port on which the server should run
	    int port = (designatedApplicationPort != null && designatedApplicationPort.isEmpty() == false) ? Integer.parseInt(designatedApplicationPort) : DEFAULT_APP_PORT;

		// Construct Base URI
		String baseUriStr = "http://0.0.0.0:" + String.valueOf(port);

		// Log it
		System.out.println("Server about to run, listening on " + port);

		// Create the server
		URI baseUri = UriBuilder.fromUri(baseUriStr).build();
		ResourceConfig config = obtainServerConfiguration();
	    this.server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);

	    // Run it
	    this.server.start();
	}
	
	/**
	 * 
	 * @return
	 */
	private ResourceConfig obtainServerConfiguration()
	{
	    ResourceConfig config = new ResourceConfig();
	    config.packages("com.efrat.example.devops.echoServerFEApp.resources");
//	    config.register(HelloWorldResource.class);
	    
	    return config;
	}
	
	/**
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	void stopServer() throws InterruptedException, ExecutionException
	{
		if (this.server != null && this.server.isStarted() == true)
		{
			// Stop it
			GrizzlyFuture<HttpServer> future = this.server.shutdown();
			future.get();
		}
	}
}
