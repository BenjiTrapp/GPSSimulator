package communication;

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
			System.err.println("ERROR @ in- /output Stream, IOException");
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
			inStream.close();
			outStream.close();
		} catch (Exception e)
		{
			System.exit(0);
		}
    } 
    
    /**
     * Function to close (input stream) the StringReader
     */
    public void closeInStream()
    {
    	try
		{
			inStream.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
    }
    
    /**
     * Function to close (output stream) the StringWriter
     */
    public void closeOutStream()
    {
    	try
		{
			outStream.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
    }
    
    /**
     * Sets the input stream for the StringReader
     * @param inStream Reference to the BufferedReader that shall be used
     * as input stream
     */
    public void setInStream(InputStream inputStream)
    {
    	this.inStream = new BufferedReader(new InputStreamReader(inputStream));
    	System.out.println("InputStream injected");
    }
    
    /**
     * Sets the output stream for the StringWriter
     * @param outStream Reference to the outStream that shall be used
     * as output stream
     */
    public void setOutStream(OutputStream outStream)
    {
    	this.outStream = outStream;
    	System.out.println("OutputStream injected");
    }
    
    /**
     * Sends a message
     * @param msg Message that shall be send
     * @throws InvalidApplicationException If the passed argument is null
     * @throws IOException In case of an error during writing the output stream
     */
    public abstract void send(String msg) throws InvalidApplicationException;
    
    /**
     * Receives a message 
     * @return returns the passed String which was read from the input Stream
     * @throws IOException In case of an error during reading the input stream
     */
    public abstract String receive();
}
