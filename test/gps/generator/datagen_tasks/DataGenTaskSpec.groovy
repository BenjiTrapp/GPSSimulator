package gps.generator.datagen_tasks

import spock.lang.Unroll

import static gps.generator.GPSGenEnumHolder.Patterns.*
import spock.lang.Specification

class DataGenTaskSpec extends Specification{
    private def mockHolder = Mock(DataGenTaskObjectHolder.class)

    def "Should return a proper instance"(){
        when:
        def instance = DataGenTask.getInstance(mockHolder)

        then:
        instance != null
        instance instanceof DataGenTask
        TimerTask.class.isAssignableFrom(DataGenTask)
        0 * _
    }

    @Unroll
    def "Should dispatch the pattern as assumed"(){
        given:
        def task = DataGenTask.getInstance(mockHolder)

        when:
        def result = task.dispatchPattern(pattern)

        then:
        result >= 0
        result < 1

        where:
        pattern     || _
        LATITUDE    || _
        LONGITUDE   || _
        VELOCITY    || _
        ALTITUDE    || _
        DOP         || _
    }

    def "Should avoid negative numbers as Result"(){
        given:
        def task = DataGenTask.getInstance(mockHolder)

        when:
        def result = task.avoidNegativeValues(digit as double)

        then:
        result >= 0

        where:
        digit || _
        1     || _
        0.5   || _
        0     || _
        -1    || _
        -42   || _
    }

}
