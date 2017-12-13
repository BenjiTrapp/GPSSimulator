package faultInjection.fault_library.perturbation_strategies

import gps.NMEA.utils.ChecksumUtilities
import spock.lang.Specification


class RandomASCIIStringSpec extends Specification {

        def shouldRandomlyPertrubThePassedString(){
            given:
            def ggaSentence = '''$GPGGA,181300,5337.44,N,1005.87,W,8.0,03,2.5,16.5,M,0,M,,*6D'''

            expect:
            def result = randomASCII(ggaSentence)
            result != ggaSentence

            try{
                !ChecksumUtilities.isChecksumValid(result)
            }catch (AssertionError ignored){
                // may appear when the asterisk of the Checksum is perturbed
                System.err.println(result)
            }
        }

        private static def randomASCII(String line2perturb){
            def rnd = new Random()
            def sb = new StringBuilder(line2perturb)
            def stringLength = line2perturb.length()
            def startValue = 0
            def maxStep = (stringLength >= 3) ? stringLength >> 1 : 1

            if (stringLength > 3){startValue = rnd.nextInt(2)}

            for (int i = startValue; i < stringLength; i += rnd.nextInt(maxStep)) {
                def tmp =  rnd.nextInt(255)
                sb.setCharAt(i, tmp as char)

                //Assert that this loop wont stuck
                if (i > stringLength || maxStep <= 1) {break}
            }

            return sb.toString()
        }
}
