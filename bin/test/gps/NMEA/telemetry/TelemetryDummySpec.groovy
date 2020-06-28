package gps.NMEA.telemetry

import gps.NMEA.gps_position.GPSPosition
import gps.NMEA.parser.NMEAParser
import spock.lang.Specification

class TelemetryDummySpec extends Specification {
    private def fileWriterMock = Mock(FileWriter)
    private def nmeaParserMock = Mock(NMEAParser)

    def dummy() {
        given:
        def pos = new GPSPosition()
        pos.setLatitude(4711.0 as Double)

        def dummy = new TelemetryDummy(fileWriterMock, nmeaParserMock)

        when:
        nmeaParserMock.parse(_) >> pos
        fileWriterMock._ >> null // do nothing on any interaction with the file writer
        dummy.write2File("test")

        then:
        1 * fileWriterMock.write(_)
        1 * fileWriterMock.flush()
        1 * fileWriterMock.close()
        1 * nmeaParserMock.parse(_) >> pos
        0 * _
    }
}
