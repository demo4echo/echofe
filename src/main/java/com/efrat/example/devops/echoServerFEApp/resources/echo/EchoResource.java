/**
 * 
 */
package com.efrat.example.devops.echoServerFEApp.resources.echo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.efrat.example.devops.echoServerFEApp.rsi.grpc.echo.EchoServiceFacade;

/**
 * @author tmeltse
 *
 */
@Path("echo")
public class EchoResource 
{
	public static final String SELF_PATH = "echo";
	
	/**
	 * 
	 */
	public EchoResource() 
	{
		// TODO Auto-generated constructor stub
	}

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @param what - the item to echo
     * @return String that will be returned as a text/plain response.
     */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getEcho(@QueryParam("what") String what) 
	{
		// Naive approach
		return what;
/*
		EchoServiceFacade client = null;
		
		try 
		{
			// Get remote approach host and port from designated environment variables
			String echobeServiceHostAsStr = System.getenv("EXTERNAL_ECHOBE_HOST_ENV_VAR");
			String echobeServicePortAsStr = System.getenv("EXTERNAL_ECHOBE_PORT_ENV_VAR");

			// Log something
			System.out.println("Got from environment the following echobe HOST: [" + echobeServiceHostAsStr + "]");
			System.out.println("Got from environment the following echobe PORT: [" + echobeServicePortAsStr + "]");
			
			// Acquire end point access
			client = new EchoServiceFacade(echobeServiceHostAsStr,Integer.parseInt(echobeServicePortAsStr));

			String reply = client.echo(what);
			return reply;
		} 
		catch (Throwable t)
		{
			t.printStackTrace();
			return "NOK";
		}
		finally 
		{
			try 
			{
				client.shutdown();
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}	
}
