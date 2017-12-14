package gps.NMEA.gps_position

import spock.lang.Specification
import spock.lang.Unroll

class GPSPositionBuilderSpec extends Specification{

    def "Should create valid GPSPosition"(){
        given:
        def positionBuilder = new GPSPositionBuilder()

        when:
        def result = positionBuilder.addLatitude(0.1 as Double)
                                    .addDirection(1.0 as Double)
                                    .addVelocity(42.0 as Double)
                                    .addFixed(true)
                                    .build()

        then:
        result != null
        0.1 as double ==  result.getLatitude()
    }


    def "Should add and get all values back"(){
        given:
        def positionBuilder = new GPSPositionBuilder()

        when:
        def result = positionBuilder.addLatitude(0.1 as Double)
                                    .addTime(1337.2 as Double)
                                    .addQuality(4.0 as Double)
                                    .addLongitude(21.0 as Double)
                                    .addDirection(1.0 as Double)
                                    .addVelocity(42.0 as Double)
                                    .addFixed(true)
                                    .build()

        then:
        assert result.isFixed()
        1337.2  == result.getTime()
        0.1     == result.getLatitude()
        21.0    == result.getLongitude()
        1.0     == result.getDirection()
        4.0     ==  result.getQuality()
        42.0    ==  result.getVelocity()
    }

    @Unroll
    def "Should trigger AssertionErrorException due nilled argument"(){
        given:
        def positionBuilder = new GPSPositionBuilder()

        when:
        positionBuilder.addAltitude(altitude as Double)
                        .addTime(time as Double)
                        .addQuality(quality as Double)
                        .addLongitude(longitude as Double)
                        .addLatitude(latitude as Double)
                        .addDirection(direction as Double)
                        .addVelocity(velocity as Double)
                        .build()


        then:
        thrown(expectation)

        where:
        altitude | time | quality | longitude | latitude | direction | velocity || expectation
        null     | 1.0  | 1.0     | 1.0       | 1.0      | 1.0       | 1.0      || AssertionError
        1.0      | null | 1.0     | 1.0       | 1.0      | 1.0       | 1.0      || AssertionError
        1.0      | 1.0  | null    | 1.0       | 1.0      | 1.0       | 1.0      || AssertionError
        1.0      | 1.0  | 1.0     | null      | 1.0      | 1.0       | 1.0      || AssertionError
        1.0      | 1.0  | 1.0     | 1.0       | null     | 1.0       | 1.0      || AssertionError
        1.0      | 1.0  | 1.0     | 1.0       | 1.0      | null      | 1.0      || AssertionError
        1.0      | 1.0  | 1.0     | 1.0       | 1.0      | 1.0       | null     || AssertionError
    }
}
