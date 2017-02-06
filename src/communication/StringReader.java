package communication;

import java.io.IOException;
import java.net.ServerSocket;
/**
 * This class is used to receive a String via ByteStream. To avoid complications
 * this class is implemented as Singleton. To use this class simply get an
 * instance
 * 
 * @author Benjamin Trapp
 */
public class StringReader extends AComModule
{

	private static final int PORT = 6711;
	private static StringReader instance;
	private static ServerSocket socket;
	
	/**
	 * Creates a new StringReader Object
	 * 
	 * @param socket Socket for the communication
	 */
	private StringReader(ServerSocket socket)
	{
		super(socket);
	}

	/**
	 * Gets the Instance of the StringReader back. If the instance 
	 * was initialized properly the passed socket will be used otherwise
	 * a default instance is created with the ServerSocket 4711.
	 * 
	 * Note: After the StringReader was set up once the instance can't
	 * be changed or modified! So if you want to use a specific ServerSocket
	 * you have to initialize the instance first via initInstance(ServerSocket)
	 * 
	 * @return The one and only Instance of the StringReader
	 */
	public static StringReader getInstance()
	{
		try
		{
			if (instance == null && socket == null)
				instance = new StringReader(new ServerSocket(PORT));
			else if (instance == null)
				instance = new StringReader(socket);

		}catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return instance;
	}

	/**
	 * Initializes the StringReader Instance if necessary
	 * Caution: This can only be done once and before calling
	 * the getInstance() Method!
	 * @param socket Socket that the StringReader shall use
	 * @return true if the initialization was done else false
	 */
	public static boolean initInstance(ServerSocket socket)
	{
		if(StringReader.socket != null)
		{
			StringReader.socket = socket;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Caution: This operation is unsupported for the StringReader!
	 * 			Only "receive" is supported...
	 */
	@Override
	public void send(String msg)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized String receive()
	{
		StringBuilder result = new StringBuilder();
		String line;

		try
		{
			while ((line = inStream.readLine()) != null)
			{
				result.append(line);

				if (!super.inStream.ready())
					break;
			}
		} catch (Exception e)
		{
			System.err.println("ERROR @ read input stream (receive)");
			instance.closeAllCom();
		}

		return result.toString();
	}
}