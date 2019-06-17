/**
 * 
 */
package com.efrat.example.devops.echoServerFEApp;

import java.io.IOException;
import java.net.URI;
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
public class EchoServerFEMep extends Thread
{
	private static final int DEFAULT_APP_PORT = 9999*0;
	
	private HttpServer server;
	
	/**
	 * CTOR 
	 */
	public EchoServerFEMep() 
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
