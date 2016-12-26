/**
 * 
 */
package test;


import org.apache.log4j.PropertyConfigurator;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.communication.ComModuleTest;

/**
 * @author Benjamin Trapp
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	TestGPSData.class,
	TestCheckSum.class,
	//ComModuleTest.class, //Auskommentiert => Test braucht viel Zeit!
	TestFactories.class,
	TestGPSPosition.class,
	TestPertubationFuncs.class,
	TestNMEAParser.class
})
public class RunTestCampaign
{
private final static Logger LOG = LoggerFactory.getLogger(TestCheckSum.class);
	
	static
	{
		PropertyConfigurator.configure("test.properties");
	}
	
	public static Test suite()
	{
		LOG.info("Starting Test Campaign\n ===========================================\n" );
		//$JUnit-BEGIN$
		TestSuite suite = new TestSuite(RunTestCampaign.class.getName());
		suite.addTestSuite(TestCheckSum.class);
		suite.addTestSuite(ComModuleTest.class);
		suite.addTestSuite(TestGPSData.class);
		suite.addTestSuite(TestGPSPosition.class);
		suite.addTestSuite(TestNMEAParser.class);
		suite.addTestSuite(TestPertubationFuncs.class);
		suite.addTestSuite(TestFactories.class);
		//$JUnit-END$
		
		return suite;
	}
}
