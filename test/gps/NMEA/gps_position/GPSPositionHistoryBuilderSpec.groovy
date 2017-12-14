package gps.NMEA.gps_position

import gps.NMEA.sentences.NMEASentenceTypes
import spock.lang.Specification
import spock.lang.Unroll

import static gps.NMEA.sentences.NMEASentenceTypes.*

class GPSPositionHistoryBuilderSpec extends Specification {

    def "Should build correct GPSPositionHistory"() {
        given:
        def secondLastPost = new GPSPosition()
        secondLastPost.setLatitude(1234.0 as Double)

        when:
        GPSPositionHistory result = new GPSPositionHistoryBuilder().setType(GPGGA)
                                                                   .setCurrentPosition(new GPSPosition())
                                                                   .setLastPosition(new GPSPosition())
                                                                   .setSecondLastPosition(secondLastPost)
                                                                   .setThirdLastPosition(new GPSPosition())
                                                                   .build()

        then:
        result != null
        result.getSecondLastPosition().getLatitude() == 1234.0
        GPGGA == result.getSentenceType()
    }

    @Unroll
    def shouldThrowAssertionErrorExeption() {
        given:
        def positionBuilder = new GPSPositionHistoryBuilder()

        when:
        positionBuilder.setType(type as NMEASentenceTypes)
                .setCurrentPosition(currentPosition)
                .setLastPosition(lastPostion)
                .setSecondLastPosition(secondLastPostion)
                .setThirdLastPosition(thirdLasstPosition)
                .build()
        then:
        thrown(expectation)

        where:
        type  | currentPosition   | lastPostion       | secondLastPostion | thirdLasstPosition || expectation
        null  | new GPSPosition() | new GPSPosition() | new GPSPosition() | new GPSPosition()  || AssertionError
        GPGGA | null              | new GPSPosition() | new GPSPosition() | new GPSPosition()  || AssertionError
        GPGSA | new GPSPosition() | null              | new GPSPosition() | new GPSPosition()  || AssertionError
        GPRMC | new GPSPosition() | new GPSPosition() | null              | new GPSPosition()  || AssertionError
        GPVTG | new GPSPosition() | new GPSPosition() | new GPSPosition() | null               || AssertionError
    }
}
