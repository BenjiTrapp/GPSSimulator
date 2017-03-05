package gps.NMEA.sentences;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NMEASentenceTypesTest {
    @Test
    public void shouldReturnRMCEnumName(){
        //given
        String RPMC_name;

        //when
        RPMC_name = NMEASentenceTypes.GPRMC.toString();

        //then
        assertEquals("GPRMC", RPMC_name);
    }

    @Test
    public void shouldReturnRMCEnumType(){
        //given
        String RPMC_type;

        //when
        RPMC_type = NMEASentenceTypes.GPRMC.getSentenceType();

        //then
        assertEquals("$GPRMC", RPMC_type);
    }
    @Test
    public void shouldReturnGGAEnumName(){
        //given
        String GGA_name;

        //when
        GGA_name = NMEASentenceTypes.GPGGA.toString();

        //then
        assertEquals("GPGGA", GGA_name);
    }

    @Test
    public void shouldReturnGGAEnumType(){
        //given
        String GGA_type;

        //when
        GGA_type = NMEASentenceTypes.GPGGA.getSentenceType();

        //then
        assertEquals("$GPGGA", GGA_type);
    }

}