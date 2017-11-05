package gps.data

import spock.lang.Specification
import spock.lang.Stepwise

import static gps.data.GPSDataEnumHolder.CardinalDirections.EAST
import static gps.data.GPSDataEnumHolder.CardinalDirections.SOUTH
import static gps.data.GPSDataEnumHolder.GPSFixTypes.GPS_FIX_3D
import static gps.data.GPSDataEnumHolder.Modes.SIMULATION
import static gps.data.GPSDataEnumHolder.Status.A

@Stepwise
class GPSDataSpec extends Specification {

    def "should reinitialize the GPS Data correctly"() {
        when:
        GPSData.reinitialize()

        then:
        A == GPSData.getStatus()
        SOUTH ==GPSData.getNS()
        EAST ==  GPSData.getEW()
        SIMULATION == GPSData.getMode()
        GPS_FIX_3D == GPSData.getFixType()
    }
}
