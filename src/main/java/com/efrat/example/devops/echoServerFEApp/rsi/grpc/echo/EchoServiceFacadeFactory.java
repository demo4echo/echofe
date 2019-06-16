/**
 * 
 */
package com.efrat.example.devops.echoServerFEApp.rsi.grpc.echo;

/**
 * @author tmeltse
 *
 */
public class EchoServiceFacadeFactory
{
	/**
	 * 
	 */
	private EchoServiceFacadeFactory() 
	{
		// TODO Auto-generated constructor stub
	}

	public static EchoServiceFacade create()
	{
		// Get mockup engagement mode from a designated environment variable
		String engageMockupRemoteInterfacesAsStr = System.getenv("ENGAGE_MOCKUP_REMOTE_INTERFACES");
		
		System.out.println("Observed ENV VAR with name: [ENGAGE_MOCKUP_REMOTE_INTERFACES] and value: [" + engageMockupRemoteInterfacesAsStr + "]");
		
		// Determine the working mode on which the server should run
	    boolean engageMockupRemoteInterfaces = (engageMockupRemoteInterfacesAsStr != null && 
	    		engageMockupRemoteInterfacesAsStr.isEmpty() == false &&
	    		Boolean.parseBoolean(engageMockupRemoteInterfacesAsStr) == true) ? 
	    		true : false;
	    
	    // Apply factory
	    if (engageMockupRemoteInterfaces == true)
	    {
	    	return new PseudoEchoService();
	    }
	    else
	    {
	    	return new RealEchoService();
	    }
	}
}