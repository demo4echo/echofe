/**
 * 
 */
package test.com.efrat.example.devops.echoServerFEApp.resources.echo;

import static org.junit.Assert.*;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.efrat.example.devops.echoServerFEApp.resources.echo.EchoResource;

/**
 * @author tmeltse
 *
 */
public class EchoTest
{
	// Need to take this externally to a file
//	public static final String BASE_URI = "http://192.168.99.100:9999";
//	public static final String BASE_URI = "http://192.168.99.100:30999";
//	public static final String BASE_URI = "http://192.168.99.100"; // in case an Ingress is in place 

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
	}

	/**
	 * Test method for {@link com.efrat.example.devops.echoServerFEApp.resources.echo.EchoResource#getEcho(java.lang.String)}.
	 */
	@Test
	public final void testGetEcho() 
	{
		System.out.println("com.efrat.echofe.serviceEndPoint.internal system property value is: [" + System.getProperty("com.efrat.echofe.serviceEndPoint.internal") + "]");
		System.out.println("com.efrat.echofe.serviceEndPoint.external system property value is: [" + System.getProperty("com.efrat.echofe.serviceEndPoint.external") + "]");

		String paramForTest = "Golan";
//		String baseURI = System.getProperty("com.efrat.echofe.serviceEndPoint",BASE_URI);
//		String baseURI = System.getProperty("com.efrat.echofe.serviceEndPoint");
		String internalBaseURI = System.getProperty("com.efrat.echofe.serviceEndPoint.internal");
		String externalBaseURI = System.getProperty("com.efrat.echofe.serviceEndPoint.external");
		String baseURI = internalBaseURI;
		
		// Resolve correct service end point and update if running outside the cluster
		boolean isRunningOutsideTheCluster = System.getenv().containsKey("MARK_OFF_CLUSTER_INVOCATION_ENV_VAR");
		if (isRunningOutsideTheCluster == true)
		{
			baseURI = externalBaseURI;
			System.out.println("Found MARK_OFF_CLUSTER_INVOCATION_ENV_VAR environment variable - invoking the test in off cluster mode!");
		}

		System.out.println("baseURI is set as: [" + baseURI + "]");
		
		assertNotNull(baseURI);
		
		WebTarget target = ClientBuilder
				.newClient()
        		.target(baseURI)
        		.path(EchoResource.SELF_PATH)
        		.queryParam("what",paramForTest);
        		
		String responseAsString = target.request(MediaType.TEXT_PLAIN).get(String.class);
        
		assertEquals(paramForTest,responseAsString);
	}
}
