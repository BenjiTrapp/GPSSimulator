package gps.NMEA.gps_position

import spock.lang.Specification
import static org.hamcrest.Matchers.*
import static spock.util.matcher.HamcrestSupport.*

class GPSPositionSpec extends Specification {

    def shouldGetCorrectValuesAfterSettingThem() {
        given:
        def gp1 = new GPSPosition()

        when:
        gp1.setFixed(false)
        gp1.setAltitude(123.321 as Double)
        gp1.setDirection(123.321 as Double)
        gp1.setLatitude(123.321 as Double)
        gp1.setLongitude(123.321 as Double)
        gp1.setQuality(123.321 as Double)
        gp1.setTime(123.321 as Double)
        gp1.setVelocity(123.321 as Double)

        then:
        123.321 == gp1.getAltitude()
        123.321 == gp1.getDirection()
        123.321 == gp1.getLatitude()
        123.321 == gp1.getLongitude()
        123.321 == gp1.getQuality()
        123.321 == gp1.getTime()
        123.321 == gp1.getVelocity()
        assert !gp1.isFixed()
    }


    def shouldBeEqualWhenBaseValuesAreEqual() {
        given:
        GPSPosition gp1 = new GPSPosition()
        GPSPosition gp2
        GPSPosition gp3 = new GPSPosition()

        when: //positions 1 and 3 are different
        gp1.setAltitude(10.0 as Double)
        gp1.setLatitude(100.0 as Double)
        gp1.setLongitude(12.0 as Double)
        gp1.setDirection(1.0 as Double)

        gp3.setAltitude(11.0 as Double)
        gp3.setLatitude(111.0 as Double)
        gp3.setLongitude(22.0 as Double)
        gp3.setVelocity(100.0 as Double)

        gp2 = gp1 // and position 1 equals position 2

        then:
        assert gp1.isBasicPositionEqual(gp2)
        assert gp1.isEqual(gp2)
        assert !gp1.isBasicPositionEqual(gp3)
        assert !gp1.isEqual(gp3)
        assert gp2.isBasicPositionEqual(gp1)
        assert gp1.isEqual(gp1)
        assert gp2 != gp3
        assert !gp2.isEqual(gp3)
    }


    def shouldBeOnlyEqualWhenCompleteObjectsAreEqual() {
        given:
        GPSPosition gp1 = new GPSPosition()
        GPSPosition gp2
        GPSPosition gp3 = new GPSPosition()

        when: //positions 1 and 3 are different
        gp1.setAltitude(10.0 as Double)
        gp1.setLatitude(100.0 as Double)
        gp1.setLongitude(12.0 as Double)
        gp1.setQuality(32.0 as Double)
        gp1.setDirection(42.0 as Double)
        gp1.setVelocity(4711.0 as Double)
        gp1.setTime(1337.0 as Double)
        gp1.setFixed(false)

        gp3.setAltitude(11.0 as Double)
        gp3.setLatitude(111.0 as Double)
        gp3.setLongitude(22.0 as Double)
        gp3.setLongitude(32.0 as Double)
        gp3.setDirection(4711.0 as Double)
        gp3.setTime(1337.1 as Double)
        gp3.setFixed(true)
        gp2 = gp1 // and position 1 equals position 2

        then:
        assert gp1 == gp2
        assert gp1.isEqual(gp2)
        assert gp1 != gp3
        assert !gp1.isEqual(gp3)
        assert gp2 == gp1
        assert gp1.isEqual(gp1)
        assert gp2 != gp3
        assert !gp2.isEqual(gp3)
    }
}
