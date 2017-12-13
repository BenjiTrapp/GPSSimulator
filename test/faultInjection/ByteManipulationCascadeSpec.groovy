package faultInjection

import communication.communication_jammer.ComJammer
import spock.lang.Specification

import static faultInjection.fault_library.modes.BitShiftByteManipulationModes.*
import static faultInjection.fault_library.modes.ByteManipulationModes.*

class ByteManipulationCascadeSpec extends Specification {
    private def jammer = Mock(ComJammer.class)

    def setup(){ jammer.send(_) >> null }

    
    def "Should build correct ByteManipulation functions"() {
        given:
        def bmb = new ByteManipulationCascade().addByteManipulation(OFF_BY_ONE, "4711")
                                               .addByteManipulation(RANDOM_BITS, "1337")

        when:
        bmb.sendByteManipulations(jammer)

        then:
        2 * jammer.send(_)
        0 * _
    }

    
    def "should build correct BitShiftManipulation functions"() {
        given:
        def bmb = new ByteManipulationCascade().addBitShiftManipulation(FLIP_BITS, "1337", 21)
                                               .addBitShiftManipulation(FLIP_BITS, "4711", 42)

        when:
        bmb.sendBitShiftManipulations(jammer)

        then:
        2 * jammer.send(_)
        0 * _
    }


    def "Should never run send step when nothing is inserted to the ByteManipulationList"() {
        given:
        ByteManipulationCascade bmb = new ByteManipulationCascade()
        bmb.addBitShiftManipulation(FLIP_BITS, "1337", 21)

        when:
        bmb.sendByteManipulations(jammer)

        then:
        thrown(AssertionError)
        0 * jammer.send(_)
    }

    
    def "Should never run send step when nothing is inserted to the BitShiftManipulationList"() {
        given:
        def bmb = new ByteManipulationCascade().addByteManipulation(RANDOM_BITS, "1337")

        when:
        bmb.sendBitShiftManipulations(jammer)

        then:
        thrown(AssertionError)
        0 * jammer.send(_)
    }

    
    def "Should build correct BitShiftAndByteManipulation functions"() {
        given:
        def bmb = new ByteManipulationCascade().addBitShiftManipulation(FLIP_BITS, "1337", 21)
                                                .addBitShiftManipulation(FLIP_BITS, "4711", 42)
                                                .addByteManipulation(OFF_BY_ONE, "4711")
                                                .addByteManipulation(RANDOM_BITS, "1337")

        when:
        bmb.sendBitShiftManipulations(jammer)
        bmb.sendByteManipulations(jammer)

        then:
        4 * jammer.send(_)
        0 * _
    }

    
    def "Should fail due to invalid arguments for BitShifts"(){
        given:
        def bmb = new ByteManipulationCascade()

        when:
        bmb.addBitShiftManipulation(mode, var, shift)

        then:
        thrown(expectation)

        where:
        mode        | var      | shift              || expectation
        null        | "1337"   | 21                 || AssertionError
        FLIP_BITS   | null     | 21                 || AssertionError
        FLIP_BITS   | "1337"   | null as Integer    || AssertionError
    }

    
    def "Should fail due to invalid arguments for ByteManipulations"(){
        given:
        def bmb = new ByteManipulationCascade()

        when:
        bmb.addByteManipulation(null, "4711")

        then:
        thrown(expectation)

        where:
        mode        | var      || expectation
        null        | "1337"   || AssertionError
        OFF_BY_ONE  | null     || AssertionError
    }
}
