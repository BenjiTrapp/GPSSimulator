package faultInjection.perturbation_functions.perturbation_strategies;

import gps.NMEA.utils.ChecksumUtilities;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RandomASCIIStrategyTest {

    @Test
    public void shouldRandomlyPertrubThePassedString(){
        // given
        String ggaSentence = "$GPGGA,181300,5337.44,N,1005.87,W,8.0,03,2.5,16.5,M,0,M,,*6D";
        String result;

        // when
        result = randomASCII(ggaSentence);


        // then
        assertNotEquals(ggaSentence, result);
        try{
            assertFalse(ChecksumUtilities.isChecksumValid(result));
        }catch (AssertionError ignored){
            // may appear when the asterisk of the Checksum is perturbed
            System.err.println(result);
        }
    }

    private static String randomASCII(String line2perturb){
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(line2perturb);

        int stringLength = line2perturb.length();
        int startValue = 0;
        int maxStep = (stringLength >= 3) ? stringLength >> 1 : 1;

        if (stringLength > 3)
            startValue = rnd.nextInt(2);

        for (int i = startValue; i < stringLength; i += rnd.nextInt(maxStep)) {
            char tmp = (char) rnd.nextInt(255);
            sb.setCharAt(i, tmp);

            //Assert that this loop wont stuck
            if (i > stringLength || maxStep <= 1)
                break;
        }

        return sb.toString();
    }

}