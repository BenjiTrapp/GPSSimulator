package gps.NMEA.gps_position
import spock.lang.Specification

import static gps.NMEA.sentences.NMEASentenceTypes.*
import static org.hamcrest.Matchers.*
import static spock.util.matcher.HamcrestSupport.*

class GPSPositionHistorySpec extends Specification{
    private def gpsPositionHistory
    private def mockPos1, mockPos2, mockPos3, mockPos4

    def setup(){
        mockPos1 = Mock(GPSPosition.class)
        mockPos2 = Mock(GPSPosition.class)
        mockPos3 = Mock(GPSPosition.class)
        mockPos4 = Mock(GPSPosition.class)

        mockPos1.getAltitude() >> 1.0
        mockPos2.getAltitude() >> 2.0
        mockPos3.getAltitude() >> 3.0
        mockPos4.getAltitude() >> 4.0
    }


    def "Should add and get all types of historic positions"(){
        given:
        gpsPositionHistory = new GPSPositionHistory(GPGGA)

        when:
        gpsPositionHistory.addCurrentPosition(mockPos1)
        gpsPositionHistory.addLastPosition(mockPos2)
        gpsPositionHistory.addSecondLastPosition(mockPos3)
        gpsPositionHistory.addThirdLastPosition(mockPos4)

        then:
        1.0 == gpsPositionHistory.getCurrentPosition().getAltitude()
        2.0 == gpsPositionHistory.getLastPosition().getAltitude()
        3.0 == gpsPositionHistory.getSecondLastPosition().getAltitude()
        4.0 == gpsPositionHistory.getThirdLastPosition().getAltitude()
    }


    def "Should add all positions and get all types of historic positions"(){
        given:
        gpsPositionHistory = new GPSPositionHistory(GPGGA)

        when:
        gpsPositionHistory.addPositions(mockPos1, mockPos2, mockPos3, mockPos4)

        then:
        1.0 == gpsPositionHistory.getCurrentPosition().getAltitude()
        2.0 == gpsPositionHistory.getLastPosition().getAltitude()
        3.0 == gpsPositionHistory.getSecondLastPosition().getAltitude()
        4.0 == gpsPositionHistory.getThirdLastPosition().getAltitude()
    }


    def "Should add and get SentenceTypes"(){
        given:
        gpsPositionHistory = new GPSPositionHistory(GPGGA)

        when:
        gpsPositionHistory.addNMEASentenceType(GPRMC)

        then:
        GPRMC == gpsPositionHistory.getSentenceType()
    }


    def shouldPrintToString(){
        given:
        gpsPositionHistory = new GPSPositionHistory(GPGGA)

        when:
        String result = gpsPositionHistory.toString()

        then:
        that result, containsString("Current Position: ")
        that result, containsString("Current Position: ")
        that result, containsString("Last Position: ")
        that result, containsString("Previous Last Position: ")
        that result, containsString("Third Last Position: ")
        that result, containsString("\n")
    }
}
