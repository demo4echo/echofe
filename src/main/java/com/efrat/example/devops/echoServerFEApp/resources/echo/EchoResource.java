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
import com.efrat.example.devops.echoServerFEApp.rsi.grpc.echo.EchoServiceFacadeFactory;

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
		EchoServiceFacade echoServiceFacade = EchoServiceFacadeFactory.create();
		
		try 
		{
			// Acquire end point access
			String reply = echoServiceFacade.echo(what);
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
				echoServiceFacade.shutdown();
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
}
