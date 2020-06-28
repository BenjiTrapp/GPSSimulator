package gps.NMEA.parser.sentences

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class GPGGAParserSpec extends Specification {

    @Shared private String[] tokens = ["GPGGA", "215336", "5333.46", "N", "1001.53", "E",
                                       "8.0", "02", "2.0", "149.8", "M", "O", "M", "*4D"]

    def "Should parse tokens array and return correct GPSPosition information object"() {
        given:
        @Subject def parser = new GPGGAParser()

        when:
        def result = parser.parse(tokens)

        then:
        tokens[1] as Double == result.getTime()
        tokens[2] as Double == result.getLatitude()
        tokens[6] as Double == result.getQuality()
        tokens[9] as Double == result.getAltitude()
        result.getVelocity() == null
        result.getDirection() == null
    }

    def "Should throw AssertionErrorException when try to parse the wrong amount of tokens"() {
        given:
        @Subject def parser = new GPGGAParser()
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
