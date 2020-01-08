/**
 * 
 */
package test.com.efrat.example.devops.echoServerFEApp.resources.echoHealthCheck;

import static org.junit.Assert.*;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.efrat.example.devops.echoServerFEApp.resources.echoHealthCheck.EchoHealthCheckResource;

/**
 * @author tmeltse
 *
 */
public class EchoHealthCheckTest
{
	// Need to take this externally to a file
//	public static final String BASE_URI = "http://192.168.99.100:9999";
//	public static final String BASE_URI = "http://192.168.99.100:30999";

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
	 * Test method for {@link com.efrat.example.devops.echoServerFEApp.resources.echoHealthCheck.EchoHealthCheckResource#getROOT()}.
	 */
	@Test
	public final void testGetEchoHealthCheck() 
	{
		System.out.println("com.efrat.echofe.serviceEndPoint system property value is: [" + System.getProperty("com.efrat.echofe.serviceEndPoint") + "]");

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
		}
		
		assertNotNull(baseURI);
		
		WebTarget target = ClientBuilder
				.newClient()
        		.target(baseURI)
        		.path(EchoHealthCheckResource.SELF_PATH);
        		
		String responseAsString = target.request(MediaType.TEXT_PLAIN).get(String.class);
        
		assertEquals(EchoHealthCheckResource.GET_RESPONSE,responseAsString);
	}
}
