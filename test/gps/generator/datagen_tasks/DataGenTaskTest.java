package gps.generator.datagen_tasks;

import gps.generator.GPSGenEnumHolder;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class DataGenTaskTest {

    @Mock
    private DataGenTaskObjectHolder mockHolder = mock(DataGenTaskObjectHolder.class);

    @Test
    void shouldReturnAProperInstance(){
        // given - when
        DataGenTask instance = DataGenTask.getInstance(mockHolder);

        // then
        assertTrue(instance != null);
        assertTrue(instance instanceof DataGenTask);
    }

    @Test
    void bla2(){
        // given
        DataGenTask instance = DataGenTask.getInstance(mockHolder);
        when(mockHolder.getMode(any())).thenReturn(GPSGenEnumHolder.Modes.MOCK);
        when(mockHolder.getMode(GPSGenEnumHolder.Patterns.LATITUDE)).thenReturn(GPSGenEnumHolder.Modes.MOCK);
        when(mockHolder.getMode(GPSGenEnumHolder.Patterns.LONGITUDE)).thenReturn(GPSGenEnumHolder.Modes.MOCK);
        when(mockHolder.getMode(GPSGenEnumHolder.Patterns.ALTITUDE)).thenReturn(GPSGenEnumHolder.Modes.MOCK);
        when(mockHolder.getMode(GPSGenEnumHolder.Patterns.VELOCITY)).thenReturn(GPSGenEnumHolder.Modes.MOCK);
        when(mockHolder.getAngleUnit()).thenReturn(GPSGenEnumHolder.AngleUnits.GON);

        // when
//        instance.run();

        // then
//        verify(mockHolder, atLeastOnce()).getMode(any());
//        verify(mockHolder, atLeastOnce()).getAngleUnit();
    }

}