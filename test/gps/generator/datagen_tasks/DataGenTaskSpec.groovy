package gps.generator.datagen_tasks

import spock.lang.Specification

class DataGenTaskSpec extends Specification{
    private def mockHolder = Mock(DataGenTaskObjectHolder.class)


    def shouldReturnAProperInstance(){
        when:
        def instance = DataGenTask.getInstance(mockHolder)

        then:
        instance != null
        instance instanceof DataGenTask
        TimerTask.class.isAssignableFrom(DataGenTask)
        0 * _
    }

  /*  def bla2(){
        given:
        DataGenTask instance = DataGenTask.getInstance(mockHolder)
        when(mockHolder.getMode(any())).thenReturn(GPSGenEnumHolder.Modes.MOCK)
        when(mockHolder.getMode(GPSGenEnumHolder.Patterns.LATITUDE)).thenReturn(GPSGenEnumHolder.Modes.MOCK)
        when(mockHolder.getMode(GPSGenEnumHolder.Patterns.LONGITUDE)).thenReturn(GPSGenEnumHolder.Modes.MOCK)
        when(mockHolder.getMode(GPSGenEnumHolder.Patterns.ALTITUDE)).thenReturn(GPSGenEnumHolder.Modes.MOCK)
        when(mockHolder.getMode(GPSGenEnumHolder.Patterns.VELOCITY)).thenReturn(GPSGenEnumHolder.Modes.MOCK)
        when(mockHolder.getAngleUnit()).thenReturn(GPSGenEnumHolder.AngleUnits.GON)

        when:
        instance.run();

        then:
        verify(mockHolder, atLeastOnce()).getMode(any());
        verify(mockHolder, atLeastOnce()).getAngleUnit();
    }
*/
}
