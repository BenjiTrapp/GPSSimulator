package faultInjection.pertubation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import gps.data.GPSData;

/**
 * This Class is used to define some Perturbation Functions. Use
 * this functions in combination with the Fault-Injector (FIStringWriter)
 * 
 * @author Benjamin Trapp
 *
 */
public final class PertubationFunctions
{
	private static Map<String,String> asciiMap = new HashMap<>();
	
	/**
	 * Replaces a random character of the given String by a random chosen ASCII
	 * Character to create to compromise this String.
	 * @param s Given String that shall be compromised
	 * @return A String that contains compromised characters (based on ASCII)
	 */
	public static String randomASCIIChar(String s)
	{
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(s);
		
		int stringLength = s.length();
		int startValue = 0;
		int maxStep = (stringLength >= 3)?  stringLength >> 1 : 1;
		
		if(stringLength > 3)
			startValue = rnd.nextInt(2);
	
		for(int i = startValue; i < stringLength; i += rnd.nextInt(maxStep))
		{
			char tmp = (char) rnd.nextInt(255);
			sb.setCharAt(i, tmp);
			
			//Assert that this loop wont stuck 
			if(i > stringLength || maxStep <= 1)
				break;
		}
		
		asciiMap.put(s, sb.toString());
		return sb.toString();
	}
	
	/**
	 * Function to get a Map back that contains the original String and the 
	 * compromised String. This map is assumed for an automatic validation of
	 * the processing of the injected fault.
	 * @return Map that contains the original and the compromised String that was
	 * created by the use of the randomASCIIChar(String) Method
	 */
	public static Map<String,String> getASCIIMap()
	{
		return asciiMap;
	}
	
	/**
	 * This Function performs a random dash in the GPS coordinates by adding 10.0 on 
	 * the basic values
	 */
	public static void randomDashCoordinates()
	{
		double lat = Double.parseDouble(GPSData.getLatitude()) + 10.0;
		double lng = Double.parseDouble(GPSData.getLongitude()) + 10.0;
		double alt = Double.parseDouble(GPSData.getAltitude()) + 10.0;
		
		GPSData.setLongitude(Double.toString(lng));
		GPSData.setLatitude(Double.toString(lat));
		GPSData.setAltitude(Double.toString(alt));
	}
	
	/**
	 * This Function performs a random dash in the GPS coordinates by adding the passed
	 * double value on the basic values
	 * @param dashValue that shall be added to the basic values of the GPS coordinates to perform a dash
	 */
	public static void randomDashCoordinates(double dashValue)
	{
		double lat = Double.parseDouble(GPSData.getLatitude()) + dashValue;
		double lng = Double.parseDouble(GPSData.getLongitude()) + dashValue;
		double alt = Double.parseDouble(GPSData.getAltitude()) + dashValue;
		
		GPSData.setLongitude(Double.toString(lng));
		GPSData.setLatitude(Double.toString(lat));
		GPSData.setAltitude(Double.toString(alt));
	}
	
	/*
	 * ATTENTION: The methods below are assumed to be used in combination with the
	 * Fault-Injection Tool ByteMan but aren't used currently. 
	 */
	public static int flipBit(int var, int bits)
	{
		return var ^(1 << bits);
	}
	
	public static String flipBit(String var, int bits)
	{
		double varD = Double.parseDouble(var);
		double tmp = Double.doubleToRawLongBits(varD) ^ (1 << bits);
		
		return Double.toString(tmp);
	}
	
	public static int allBitsLow()
	{
		return ((int)0);
	}
	
	public static int allBitsHigh()
	{
		return ((int)~0);
	}
	
	public static int randomPattern(int var)
	{
		Random r = new Random();
		
		return r.nextInt(var >> 1);
	}
	
	public static int offByOne(int var)
	{
		return (var % 2 == 0)? ++var: --var;
	}
	
	public static double offByOne(double var)
	{
		return (var % 2 == 0)? ++var: --var;
	}

	public static int toggleBits(int var)
	{
		return ~var;
	}
	
	public static double toggleBits(double var)
	{
		return ~Double.doubleToRawLongBits(var);
	}
}
