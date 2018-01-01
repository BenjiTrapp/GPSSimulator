package gps.NMEA.gps_position;

import gps.NMEA.sentences.NMEASentenceTypes;

public class GPSPositionHistoryBuilder {
    private GPSPosition currentPosition;
    private GPSPosition lastPosition;
    private GPSPosition secondLastPosition;
    private GPSPosition thirdLastPosition;
    private NMEASentenceTypes type;

    public GPSPositionHistoryBuilder withCurrentPosition(GPSPosition position){
        assert position != null;
        this.currentPosition = position;
        return this;
    }

    public GPSPositionHistoryBuilder withLastPosition(GPSPosition position){
        assert position != null;
        this.lastPosition = position;
        return this;
    }

    public GPSPositionHistoryBuilder withSecondLastPosition(GPSPosition position){
        assert position != null;
        this.secondLastPosition = position;
        return this;
    }

    public GPSPositionHistoryBuilder withThirdLastPosition(GPSPosition position){
        assert position != null;
        this.thirdLastPosition = position;
        return this;
    }

    public GPSPositionHistoryBuilder withType(NMEASentenceTypes type){
        this.type = type;
        return this;
    }

    public GPSPositionHistory build(){
        assert currentPosition != null;
        assert lastPosition != null;
        assert secondLastPosition != null;
        assert thirdLastPosition != null;
        assert type != null;

        return new GPSPositionHistory(type){{ addCurrentPosition(currentPosition);
                                              addSecondLastPosition(secondLastPosition);
                                              addThirdLastPosition(thirdLastPosition);
        }};
    }
}
