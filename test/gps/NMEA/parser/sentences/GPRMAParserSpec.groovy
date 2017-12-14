package gps.NMEA.parser.sentences

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class GPRMAParserSpec extends Specification {

    @Shared private String[] tokens = ["GPRMC", "222637", "A", "5333.82", "N", "1001.96",
                                       "E", "010.1", "84.0", "150117", "", "S*42"]


    def "Should parse tokens array and return correct GPSPosition information object"() {
        given:
        @Subject def parser = new GPRMCParser()

        when:
        def result = parser.parse(tokens)

        then:
        tokens[1] as Double == result.getTime()
        tokens[3] as Double == result.getLatitude()
        tokens[7] as Double == result.getQuality()
        tokens[8] as Double == result.getAltitude()
        result.getVelocity() == null
        result.getDirection() == null
    }

    def "Should throw AssertionErrorException when try to parse the wrong amount of tokens"() {
        given:
        @Subject def parser = new GPRMCParser()
        def reducedTokens = reducedTokenArray

        when:
        def result = parser.parse(reducedTokens)

        then:
        thrown(AssertionError)
        result == null

        where:
        reducedTokenArray                                 | _
        tokens.toList().remove(3)                   | _
        tokens.toList().add(tokens.size(), "42")  | _
    }
}
