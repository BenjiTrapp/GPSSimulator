package gps.generator;

import gps.generator.datagen_tasks.DataGenTaskObjectHolder;
import gps.generator.datagen_tasks.DataGenTaskObjectHolderBuilder;
import org.junit.jupiter.api.Test;

import static gps.generator.GPSGenEnumHolder.*;
import static org.junit.jupiter.api.Assertions.*;

class DataGenTaskBuilderTest {

    @Test
    public void shouldCreateValidTask(){
        // given
        DataGenTaskObjectHolderBuilder dataGenTaskBuilder = new DataGenTaskObjectHolderBuilder();
        DataGenTaskObjectHolder result;

        // when
        result = dataGenTaskBuilder
                .addMode(Patterns.LATITUDE, Modes.ASCENDING)
                .addMode(Patterns.LONGITUDE, Modes.ASCENDING)
                .addMode(Patterns.ALTITUDE, Modes.DESCENDING)
                .addAngleUnit(AngleUnits.RADIAL)
                .build();

        // then
        assertEquals(Modes.ASCENDING, result.getMode(Patterns.LATITUDE));
        assertEquals(Modes.ASCENDING, result.getMode(Patterns.LONGITUDE));
        assertEquals(Modes.DESCENDING, result.getMode(Patterns.ALTITUDE));
        assertEquals(Modes.RANDOM, result.getMode(Patterns.VELOCITY));
        assertEquals(Modes.RANDOM, result.getMode(Patterns.DOP));
        assertEquals(Modes.RANDOM, result.getMode(Patterns.DOP));
    }

    @Test
    public void shouldUseDefaultRANDOMValuesForNewCreatedTask(){
        // given
        DataGenTaskObjectHolderBuilder dataGenTaskBuilder = new DataGenTaskObjectHolderBuilder();
        DataGenTaskObjectHolder result;

        // when
        result = dataGenTaskBuilder.build();

        // then
        assertEquals(Modes.RANDOM, result.getMode(Patterns.LATITUDE));
        assertEquals(Modes.RANDOM, result.getMode(Patterns.LONGITUDE));
        assertEquals(Modes.RANDOM, result.getMode(Patterns.ALTITUDE));
        assertEquals(Modes.RANDOM, result.getMode(Patterns.VELOCITY));
        assertEquals(Modes.RANDOM, result.getMode(Patterns.DOP));
    }

    @Test
    public void shouldUseDefaultRANDOMInsteadOFNULLValuesForNewCreatedTask(){
        // given
        DataGenTaskObjectHolderBuilder dataGenTaskBuilder = new DataGenTaskObjectHolderBuilder();
        DataGenTaskObjectHolder result;

        // when
        result = dataGenTaskBuilder
                .addMode(Patterns.LATITUDE, null)
                .addMode(Patterns.LONGITUDE, null)
                .addMode(Patterns.ALTITUDE, null)
                .build();

        // then
        assertEquals(Modes.RANDOM, result.getMode(Patterns.LATITUDE));
        assertEquals(Modes.RANDOM, result.getMode(Patterns.LONGITUDE));
        assertEquals(Modes.RANDOM, result.getMode(Patterns.ALTITUDE));
        assertEquals(Modes.RANDOM, result.getMode(Patterns.VELOCITY));
        assertEquals(Modes.RANDOM, result.getMode(Patterns.DOP));
    }

    @Test
    public void shouldUseLastPassedValueAsModeForNewCreatedTask(){
        // given
        DataGenTaskObjectHolderBuilder dataGenTaskBuilder = new DataGenTaskObjectHolderBuilder();
        DataGenTaskObjectHolder result;

        // when
        result = dataGenTaskBuilder
                .addMode(Patterns.LATITUDE, Modes.ASCENDING)
                .addMode(Patterns.LATITUDE, Modes.DESCENDING)
                .build();

        // then
        assertEquals(Modes.DESCENDING, result.getMode(Patterns.LATITUDE));
    }
}