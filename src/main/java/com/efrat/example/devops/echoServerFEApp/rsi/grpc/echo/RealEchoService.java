/**
 * 
 */
package com.efrat.example.devops.echoServerFEApp.rsi.grpc.echo;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.efrat.example.devops.echoServerBEApp.generated.services.echo.EchoGrpc;
import com.efrat.example.devops.echoServerBEApp.generated.services.echo.EchoQuery;
import com.efrat.example.devops.echoServerBEApp.generated.services.echo.EchoReply;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

/**
 * @author tmeltse
 * 
 * A simple client that requests an echo from the echo server.
 */
public class RealEchoService implements EchoServiceFacade 
{
	private static final Logger logger = Logger.getLogger(RealEchoService.class.getName());

	private final ManagedChannel channel;
	private final EchoGrpc.EchoBlockingStub blockingStub;

	/** Construct client connecting to Echo server at {@code host:port}. */
	public RealEchoService() 
	{
		// Get remote approach host and port from designated environment variables
		String echobeServiceHostAsStr = System.getenv("EXTERNAL_ECHOBE_HOST_ENV_VAR");
		String echobeServicePortAsStr = System.getenv("EXTERNAL_ECHOBE_PORT_ENV_VAR");

		// Log something
		logger.info("Got from environment the following echobe HOST: [" + echobeServiceHostAsStr + "]");
		logger.info("Got from environment the following echobe PORT: [" + echobeServicePortAsStr + "]");
		
		this.channel = ManagedChannelBuilder.forAddress(echobeServiceHostAsStr,Integer.parseInt(echobeServicePortAsStr))
				// Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid needing certificates.
				.usePlaintext()
				.build();

		this.blockingStub = EchoGrpc.newBlockingStub(channel);
	}

	/** Close communication channel */
	@Override
	public void shutdown() throws InterruptedException 
	{
		channel.shutdown().awaitTermination(5,TimeUnit.SECONDS);
		logger.info("Channel has been terminated");
	}

	/** Send some echo to the server and return the response. */
	@Override
	public String echo(String what) 
	{
		logger.info("Will try to echo [" + what + "] ...");
	    
		EchoQuery request = EchoQuery.newBuilder().setQueryMessage(what).build();
		EchoReply response;
		try 
		{
			response = blockingStub.echo(request);
		} 
		catch (StatusRuntimeException e) 
		{
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
			return "";
		}
	    
		logger.info("Echo response is: [" + response.getReplyMessage() + "]");
		
		// Return the echo response
		return response.getReplyMessage();
	}
}
