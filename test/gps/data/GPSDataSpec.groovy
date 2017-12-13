package gps.data

import spock.lang.Specification
import spock.lang.Stepwise

import static gps.data.GPSDataEnumHolder.CardinalDirections.EAST
import static gps.data.GPSDataEnumHolder.CardinalDirections.NORTH
import static gps.data.GPSDataEnumHolder.CardinalDirections.SOUTH
import static gps.data.GPSDataEnumHolder.CardinalDirections.WEST
import static gps.data.GPSDataEnumHolder.GPSFixTypes.GPS_FIX_3D
import static gps.data.GPSDataEnumHolder.Modes.DIFFERENTIAL
import static gps.data.GPSDataEnumHolder.Modes.SIMULATION
import static gps.data.GPSDataEnumHolder.Status.A
import static gps.data.GPSDataEnumHolder.Status.V

@Stepwise
class GPSDataSpec extends Specification {

    def "Should reinitialize the GPS Data correctly"() {
        given:
        GPSData.reinitialize()

        expect:
        A == GPSData.getStatus()
        SOUTH == GPSData.getNS()
        EAST == GPSData.getEW()
        SIMULATION == GPSData.getMode()
        GPS_FIX_3D == GPSData.getFixType()
    }

    def "Should ignore the setStatus method when the GPS Data is stuck"() {
        given:
        GPSData.reinitialize()
        GPSData.stuckAtState(true)

        when:
        assert GPSData.isStuck()
        GPSData.setStatus(V) // Should be ignored

        then:
        V == GPSData.getStatus()
        assert GPSData.isStuck()
    }

    def "Should update the GPSData when the GPS-Data is not stuck"() {
        given:
        GPSData.reinitialize()
        GPSData.stuckAtState(true)
        assert GPSData.isStuck()

        when:
        GPSData.stuckAtState(false)
        assert !GPSData.isStuck()
        GPSData.setLatitude("123456789")
        GPSData.setLongitude("987654321")
        GPSData.setVelocity("-1")

        then:
        "123456789" == GPSData.getLatitude()
        "987654321" == GPSData.getLongitude()
        "-1" == GPSData.getVelocity()
    }

    def "Should not update GPS-Data when data is equal to the old Data and GPS is not stuck"() {
        given:
        GPSData.stuckAtState(false)
        !GPSData.isStuck()
        GPSData.setLatitude("123456789")
        GPSData.setLongitude("987654321")
        GPSData.setVelocity("-1")
        "123456789" == GPSData.getLatitude()
        "987654321" == GPSData.getLongitude()
        "-1" == GPSData.getVelocity()

        when:
        GPSData.setLatitude("123456789")
        GPSData.setLongitude("987654321")
        GPSData.setVelocity("-1")

        then:
        "123456789" == GPSData.getLatitude()
        "987654321" == GPSData.getLongitude()
        "-1" == GPSData.getVelocity()
    }

    def "Should set and get course"() {
        given:
        int expected = 314
        GPSData.setCourse(expected)

        when:
        int result = GPSData.getCourse()

        then:
        expected == result
    }

    def "Should Set and Get Longitude"() {
        given:
        String expected = "123"
        GPSData.setLongitude(expected)

        when:
        String result = GPSData.getLongitude()

        then:
        expected == result
    }

    def "Should Set and Get Cardinals"() {
        given:
        GPSData.setNS(NORTH)
        GPSData.setEW(WEST)

        expect:
        NORTH == GPSData.getNS()
        WEST == GPSData.getEW()
    }

    def "Should Set and Get Modes"() {
        given:
        GPSData.setMode(DIFFERENTIAL)

        expect:
        DIFFERENTIAL == GPSData.getMode()
    }

    def "Should Set and Get FixType"() {
        given:
        GPSData.setFixType(GPS_FIX_3D)

        expect:
        GPS_FIX_3D ==  GPSData.getFixType()
    }

    def "Should Set and Get Status"() {
        given:
        GPSData.setStatus(V)

        expect:
        V ==  GPSData.getStatus()
    }

    def "Should Set and Get Latitude"() {
        given:
        String expected = "9475.557085"
        GPSData.setLatitude(expected)

        when:
        String result = GPSData.getLatitude()

        then:
        expected ==  result
    }

    def "Should Set and Get StuckAtState TRUE"() {
        given:
        boolean expected = true
        GPSData.stuckAtState(expected)

        when:
        boolean result = GPSData.isStuck()

        then:
        expected ==  result
    }

    def "shouldSetAndGetStuckAtStateFALSE"() {
        given:
        boolean expected = false
        GPSData.stuckAtState(expected)

        when:
        boolean result = GPSData.isStuck()

        then:
        expected ==  result
    }
}

