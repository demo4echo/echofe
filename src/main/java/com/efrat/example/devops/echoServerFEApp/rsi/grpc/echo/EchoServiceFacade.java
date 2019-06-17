package com.efrat.example.devops.echoServerFEApp.rsi.grpc.echo;

public interface EchoServiceFacade
{
	/** Close communication channel */
	void shutdown() throws InterruptedException;

	/** Send some echo to the server and return the response */
	String echo(String what);
}