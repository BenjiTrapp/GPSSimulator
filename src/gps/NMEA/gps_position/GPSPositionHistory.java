package gps.NMEA.gps_position;

import gps.NMEA.sentences.NMEASentenceTypes;

public class GPSPositionHistory {

    private GPSPosition currentPosition;
    private GPSPosition lastPosition;
    private GPSPosition previousLasttPosition;
    private NMEASentenceTypes type;

    public GPSPositionHistory(NMEASentenceTypes type){
        this.type = type;
    }

    public void addNMEASentenceType(NMEASentenceTypes type){
        this.type = type;
    }

    public NMEASentenceTypes getSentenceType(){
        return this.type;
    }

    public void addCurrentPosition(GPSPosition currentPosition){
        this.currentPosition = currentPosition;
    }

    public GPSPosition getCurrentPosition(){
        return this.currentPosition;
    }

    public void addlastPosition(GPSPosition lastPosition){
        this.lastPosition = lastPosition;
    }

    public GPSPosition getLastPosition(){
        return this.lastPosition;
    }
    public void addpreviousLasttPosition(GPSPosition previousLasttPosition){
        this.previousLasttPosition = previousLasttPosition;
    }

    public GPSPosition getPreviousLasttPosition(){
        return this.previousLasttPosition;
    }
    public void addPositions(GPSPosition currentPosition, GPSPosition lastPosition,GPSPosition previousLasttPosition){
        this.currentPosition = currentPosition;
        this.lastPosition = lastPosition;
        this.previousLasttPosition = previousLasttPosition;
    }
}
