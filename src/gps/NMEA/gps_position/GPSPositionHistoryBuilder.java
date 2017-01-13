package gps.NMEA.gps_position;

import gps.NMEA.sentences.NMEASentenceTypes;

public class GPSPositionHistoryBuilder {
    private GPSPosition currentPosition;
    private GPSPosition lastPosition;
    private GPSPosition secondLastPosition;
    private GPSPosition thirdLastPosition;
    private NMEASentenceTypes type;

    public GPSPositionHistoryBuilder setCurrentPosition(GPSPosition position){
        this.currentPosition = position;
        return this;
    }

    public GPSPositionHistoryBuilder setLastPosition(GPSPosition position){
        this.lastPosition = position;
        return this;
    }

    public GPSPositionHistoryBuilder setSecondLastPosition(GPSPosition position){
        this.secondLastPosition = position;
        return this;
    }

    public GPSPositionHistoryBuilder setThirdLastPosition(GPSPosition position){
        this.thirdLastPosition = position;
        return this;
    }

    public GPSPositionHistoryBuilder setType(NMEASentenceTypes type){
        this.type = type;
        return this;
    }

    public GPSPositionHistory build(){
        assert currentPosition != null;
        assert lastPosition != null;
        assert secondLastPosition != null;
        assert thirdLastPosition != null;
        assert type != null;

        GPSPositionHistory tmp = new GPSPositionHistory(type);
        tmp.addCurrentPosition(currentPosition);
        tmp.addSecondLastPosition(secondLastPosition);
        tmp.addThirdLastPosition(thirdLastPosition);

        return tmp;
    }
}
