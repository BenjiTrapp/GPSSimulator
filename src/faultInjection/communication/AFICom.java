package faultInjection.communication;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.management.InvalidApplicationException;

/**
 * An Abstract Class that contains all necessary information for 
 * creating an in- and output stream/writer and also includes some
 * useful methods to simplify the usage. This Class is based on the
 * Abstract Class of the Communication package and used to create
 * the FIStringWriter for creating some Fault-Injection Experiments
 *    
 * @author Benjamin Trapp
 */
public abstract class AFICom
{
    /**
     * Output Stream
     */
    protected OutputStream outStream = null;
    
    /**
     * Constructor of the Communication class
     * @param socket Socket to handle the communication
     */
	public AFICom(Socket socket) 
	{
		try 
		{
			outStream = socket.getOutputStream();
		} catch (IOException e) 
		  {
			System.err.println("ERROR @ in- /output Stream, IOException");
			closeOutStream();
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
}
