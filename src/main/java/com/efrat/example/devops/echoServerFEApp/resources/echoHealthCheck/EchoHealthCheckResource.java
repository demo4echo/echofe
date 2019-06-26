/**
 * 
 */
package com.efrat.example.devops.echoServerFEApp.resources.echoHealthCheck;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author tmeltse
 *
 */
@Path("/echo/health")
public class EchoHealthCheckResource 
{
	public static final String SELF_PATH = "/echo/health";
	public static final String GET_RESPONSE = "I'm Alive!";
	
	/**
	 * 
	 */
	public EchoHealthCheckResource() 
	{
		// TODO Auto-generated constructor stub
	}

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getROOT() 
    {
        return GET_RESPONSE;
    }	
}
