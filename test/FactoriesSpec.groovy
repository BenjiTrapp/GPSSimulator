import spock.lang.Specification

class FactoriesSpec extends Specification {

//    static
//    {
//        PropertyConfigurator.configure("test.properties")
//    }


//    @Test
//    void testParserFactory()
//    {
//        NMEAParserFactory pf = new NMEAParserFactory()
//        assertNotNull(pf)
//
//        NMEAParserFactory pf_spy = new NMEAParserFactory(spyTeleDummy)
//        assertNotNull(pf_spy)
//        assertNotNull(pf_spy.createParserThread())
//        isSuccessful = true
//    }
//
//    @Test
//    void testGeneratorFactory()
//    {
//        GPSGeneratorFactory gf = new GPSGeneratorFactory()
//        assertNotNull(gf)
//        isSuccessful = true
//    }
//
//    @Test
//    void testGeneratorFacException()
//    {
//        GPSGeneratorFactory gf = new GPSGeneratorFactory()
//
//        try
//        {
//            gf.build(null, 0)
//            fail("Exception wasn't triggered")
//        } catch (NullPointerException e)
//        {
//            assertNotNull(e)
//            assertSame(NullPointerException.class, e.getClass())
//            LOG.info("Catched the expected Exception of type:  " + e.getClass())
//        }catch(Exception e)
//        {
//            fail("Catched the wrong exception")
//            LOG.error("Catched the wrong exception of the type: "
//                    + e.getClass())
//        }
//
//        try
//        {
//            gf.build(null, 0, null)
//            fail("Exception wasn't triggered")
//        } catch (NullPointerException e)
//        {
//            assertNotNull(e)
//            assertSame(NullPointerException.class, e.getClass())
//            LOG.info("Catched the expected Exception of type:  " + e.getClass())
//        }catch(Exception e)
//        {
//            fail("Catched the wrong exception")
//            LOG.error("Catched the wrong exception of the type: "
//                    + e.getClass())
//        }
//
//        isSuccessful = true
//    }


//	//FIXME
//	@Test
//	public void testPerturbationFacException()
//	{
//		PerturbationFactory pf = new PerturbationFactory();
//
//		try
//		{
//			pf.build(null);
//			fail("Exception wasn't triggered");
//		} catch (NullPointerException e)
//		{
//			assertNotNull(e);
//			assertSame(NullPointerException.class, e.getClass());
//			LOG.info("Catched the expected Exception of type:  " + e.getClass());
//		}catch(Exception e)
//		{
//			fail("Catched the wrong exception");
//			LOG.error("Catched the wrong exception of the type: "
//					+ e.getClass());
//		}
//
//		try
//		{
//			pf.build(null, 0, null);
//			fail("Exception wasn't triggered");
//		} catch (NullPointerException e)
//		{
//			assertNotNull(e);
//			assertSame(NullPointerException.class, e.getClass());
//			LOG.info("Catched the expected Exception of type:  " + e.getClass());
//		}catch(Exception e)
//		{
//			fail("Catched the wrong exception");
//			LOG.error("Catched the wrong exception of the type: "
//					+ e.getClass());
//		}
//
//		try
//		{
//			pf.build(null, 0, null, null);
//			fail("Exception wasn't triggered");
//		} catch (NullPointerException e)
//		{
//			assertNotNull(e);
//			assertSame(NullPointerException.class, e.getClass());
//			LOG.info("Catched the expected Exception of type:  " + e.getClass());
//		}catch(Exception e)
//		{
//			fail("Catched the wrong exception");
//			LOG.error("Catched the wrong exception of the type: "
//					+ e.getClass());
//		}
//
//		isSuccessful = true;
//	}
}
