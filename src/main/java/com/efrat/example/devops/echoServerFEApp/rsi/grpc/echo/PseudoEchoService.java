/**
 * 
 */
package com.efrat.example.devops.echoServerFEApp.rsi.grpc.echo;

import java.util.logging.Logger;

/**
 * @author tmeltse
 * 
 * A fake client that sends back what it got (NO backup server).
 */
public class PseudoEchoService implements EchoServiceFacade 
{
	private static final Logger logger = Logger.getLogger(PseudoEchoService.class.getName());

	/** Do nothing */
	@Override
	public void shutdown() throws InterruptedException 
	{
	}

	/** Send back what we got */
	@Override
	public String echo(String what) 
	{
		logger.info("This is a pseudo implementation, getting [" + what + "] and returning it");
		
		// Return the echo response
		return what;
	}
}
