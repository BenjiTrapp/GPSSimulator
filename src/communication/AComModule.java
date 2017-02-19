package communication;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.management.InvalidApplicationException;

/**
 * An Abstract Class that contains all necessary information for 
 * creating an in- and output stream/writer and also includes some
 * useful methods to simplify the usage
 *    
 * @author Benjamin Trapp
 */
public abstract class AComModule
{
    /**
     * Input Stream
     */
	BufferedReader inStream = null;
    
    /**
     * Output Stream
     */
	OutputStream outStream = null;
    
    /**
     * Constructor of the Communication class
     * @param socket Socket to handle the communication
     */
	AComModule(Socket socket)
	{
		try 
		{
			outStream = socket.getOutputStream();
		} catch (IOException e) 
		  {
			System.err.println("ERROR @ getOutputStream due to an IOException");
			closeAllCom();
		  }
	}
	
    /**
     * Constructor of the Communication class
     * @param socket Socket to handle the communication
     */
	AComModule(ServerSocket socket)
	{
		try 
		{
			Socket s = socket.accept();
			inStream = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) 
		  {
			System.err.println("ERROR @ in- /output Stream, IOException");
			closeAllCom();
		  }
	}
    
    /**
     * Closes the BufferedReader and the OutputStream. Call this
     * Method if you want close the connection
     * @throws IOException
     */
    public void closeAllCom()
    {
        try
		{
			this.inStream.close();
			this.outStream.close();
			outStream.close();
		} catch (Exception e)
		{
			System.exit(0);
		}
    } 
    
    /**
     * Sends a message
     * @param msg Message that shall be send
     * @throws InvalidApplicationException If the passed argument is null
     * @throws IOException In case of an error during writing the output stream
     */
    @Contract("null -> fail")
    public abstract void send(@NotNull String msg) throws InvalidApplicationException;
    
    /**
     * Receives a message 
     * @return returns the passed String which was read from the input Stream
     * @throws IOException In case of an error during reading the input stream
     */
    public abstract String receive();
}
