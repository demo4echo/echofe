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
	public static final String BASE_URI = "http://192.168.99.100";

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
		String paramForTest = "Golan";
		
		WebTarget target = ClientBuilder
				.newClient()
        		.target(BASE_URI)
        		.path(EchoResource.SELF_PATH)
        		.queryParam("what",paramForTest);
        		
		String responseAsString = target.request(MediaType.TEXT_PLAIN).get(String.class);
        
		assertEquals(paramForTest,responseAsString);
	}
}
