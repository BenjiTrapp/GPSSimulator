/**
 * 
 */
package test.communication;

import junit.framework.TestCase;

import org.jboss.byteman.contrib.bmunit.BMUnitRunner;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

/**
 * http://blog.james-carr.org/2010/04/22/mockito-spy-annotation/
 * http://www.javahotchocolate.com/tutorials/mockito.html
 * http://gojko.net/2009/10/23/mockito-in-six-easy-examples/
 * http://stackoverflow.com/questions/6371379/mocking-java-inputstream
 * @author Benjamin Trapp
 * 
 *http://www.vogella.com/articles/Mockito/article.html
 *http://stackoverflow.com/questions/6371379/mocking-java-inputstream
 *
 *@MockSocket
 *https://github.com/lukas-krecan/mock-socket/wiki/Core-API
 */
@RunWith(MockitoJUnitRunner.class)
@IncludeCategory(BMUnitRunner.class)
public class ComModuleTest extends TestCase
{
/*	 private static final String ADDRESS = "localhost";
	 private static final int PORT = 4711;
	 
	 @Mock StringReader strReader = spy(StringReader.getInstance());
	 @Mock StringWriter strWriter = spy(StringWriter.getInstance());
	 
	 @Mock BufferedReader bufferedReader = null;
	 @Mock InputStream inputStream = null;
	 
	 *//*
	 * http://stackoverflow.com/questions/6371379/mocking-java-inputstream
	 * http://stackoverflow.com/questions/2276271/how-to-make-mock-to-void-methods-with-mockito
	 * https://github.com/lukas-krecan/mock-socket/wiki/Core-API
	 *//*
	
	*//**
	 * @throws java.lang.Exception
	 *//*
	@Before
	public void setUp() throws Exception
	{
		bufferedReader = mock(BufferedReader.class);
		inputStream = mock(InputStream.class);
		
		assertNotNull(bufferedReader);
		assertNotNull(inputStream);
		
		strReader.setInStream(inputStream);
	}

	*//**
	 * @throws java.lang.Exception
	 *//*
	@After
	public void tearDown() throws Exception
	{
		reset(); //reset needed for MockSocket
		
		bufferedReader = null;
		inputStream = null;
		strReader = null;
		strWriter = null;

		
		assertNull(bufferedReader);
		assertNull(inputStream);
		assertNull(strReader);
		assertNull(strWriter);
	}

	@Test
	public void testResponse() throws Exception
	{
		  byte[] mockData = new byte[]{1,2,3,4};
          expectCall().andReturn(data(mockData));
          
          Socket socket = SocketFactory.getDefault().createSocket(ADDRESS,PORT);
          byte[] data = IOUtils.toByteArray(socket.getInputStream());
          socket.close();
          assertThat(data, is(mockData));
	}
	
	  @Test
      public void testMultiple() throws Exception
      {
              byte[] mockData1 = new byte[]{1,2,3,4};
              byte[] mockData2 = new byte[]{1,2,3,4};
              expectCall().andReturn(data(mockData1)).andReturn(data(mockData2));
              
              Socket socket1 = SocketFactory.getDefault().createSocket(ADDRESS,PORT);
              byte[] data1 = IOUtils.toByteArray(socket1.getInputStream());
              socket1.close();
              assertThat(data1, is(mockData1));

              Socket socket2 = SocketFactory.getDefault().createSocket(ADDRESS,PORT);
              byte[] data2 = IOUtils.toByteArray(socket2.getInputStream());
              socket2.close();
              assertThat(data2, is(mockData2));
      }
      
      @Test
      public void testConditionalAddress() throws Exception
      {
              byte[] mockData = new byte[]{1,2,3,4};
              expectCall().andWhenRequest(address(is(ADDRESS + ":" + PORT))).thenReturn(data(mockData));
              
              Socket socket = SocketFactory.getDefault().createSocket(ADDRESS,PORT);
              byte[] data = IOUtils.toByteArray(socket.getInputStream());
              socket.close();
              assertThat(data, is(mockData));
      }
      
      @Test
      public void testConditionalData() throws Exception
      {
              byte[] dataToWrite = new byte[]{5,4,3,2};
              byte[] mockData = new byte[]{1,2,3,4};
              expectCall().andWhenRequest(data(is(dataToWrite))).thenReturn(data(mockData));
              
              Socket socket = SocketFactory.getDefault().createSocket(ADDRESS,PORT);
              IOUtils.write(dataToWrite, socket.getOutputStream());
              byte[] data = IOUtils.toByteArray(socket.getInputStream());
              socket.close();
              assertThat(data, is(mockData));
      }*/

}
