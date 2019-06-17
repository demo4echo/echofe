/**
 * 
 */
package com.efrat.example.devops.echoServerFEApp.rsi.grpc.echo;

//import dagger.Module;
//import dagger.Provides;

/**
 * @author tmeltse
 *
 */
//@Module
public class EchoServiceModule 
{
//	@Provides
	EchoServiceFacade provideEchoServiceFacade(PseudoEchoService pseudo,RealEchoService real)
	{
		System.out.println("Inside EchoServiceModule and returning real");
		
		return new PseudoEchoService();
	}
}
