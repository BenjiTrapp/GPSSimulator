package gps.generator

import gps.generator.datagen_tasks.DataGenTaskObjectHolder
import gps.generator.datagen_tasks.DataGenTaskObjectHolderBuilder
import spock.lang.Specification

import static gps.generator.GPSGenEnumHolder.Modes.ASCENDING
import static gps.generator.GPSGenEnumHolder.Modes.DESCENDING
import static gps.generator.GPSGenEnumHolder.Modes.RANDOM
import static gps.generator.GPSGenEnumHolder.Patterns.ALTITUDE
import static gps.generator.GPSGenEnumHolder.Patterns.DOP
import static gps.generator.GPSGenEnumHolder.Patterns.LATITUDE
import static gps.generator.GPSGenEnumHolder.Patterns.LONGITUDE
import static gps.generator.GPSGenEnumHolder.Patterns.VELOCITY

class DataGenTaskBuilderSpec extends Specification {

    def "shouldCreateValidTask"(){
        given:
        def dataGenTaskBuilder = new DataGenTaskObjectHolderBuilder()

        when:
        def result = dataGenTaskBuilder.addMode(LATITUDE, ASCENDING)
                                       .addMode(LONGITUDE, ASCENDING)
                                       .addMode(ALTITUDE, DESCENDING)
                                       .addAngleUnit(GPSGenEnumHolder.AngleUnits.RADIAL)
                                       .build()

        then:
        result instanceof DataGenTaskObjectHolder
        ASCENDING == result.getMode(LATITUDE)
        ASCENDING == result.getMode(LONGITUDE)
        DESCENDING == result.getMode(ALTITUDE)
        RANDOM == result.getMode(VELOCITY)
        RANDOM == result.getMode(DOP)
        RANDOM == result.getMode(DOP)
    }


    def "Should use default RANDOM values for a new created task"(){
        given:
        def dataGenTaskBuilder = new DataGenTaskObjectHolderBuilder()

        when:
        def result = dataGenTaskBuilder.build()

        then:
        RANDOM == result.getMode(LATITUDE)
        RANDOM == result.getMode(LONGITUDE)
        RANDOM == result.getMode(ALTITUDE)
        RANDOM == result.getMode(VELOCITY)
        RANDOM == result.getMode(DOP)
    }


    def "Should use default RANDOM instead of NULL values for a new created task"(){
        given:
        def dataGenTaskBuilder = new DataGenTaskObjectHolderBuilder()

        when:
        def result = dataGenTaskBuilder.addMode(LATITUDE, null)
                                       .addMode(LONGITUDE, null)
                                       .addMode(ALTITUDE, null)
                                       .build()

        then:
        RANDOM == result.getMode(LATITUDE)
        RANDOM == result.getMode(LONGITUDE)
        RANDOM == result.getMode(ALTITUDE)
        RANDOM == result.getMode(VELOCITY)
        RANDOM == result.getMode(DOP)
    }


    def "Should use last passed value as mode for a new created task"(){
        given:
        def dataGenTaskBuilder = new DataGenTaskObjectHolderBuilder()

        when:
        def result = dataGenTaskBuilder.addMode(LATITUDE, ASCENDING)
                                       .addMode(LATITUDE, DESCENDING)
                                       .build()

        then:
        DESCENDING == result.getMode(LATITUDE)
    }
}
