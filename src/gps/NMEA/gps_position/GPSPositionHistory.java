package gps.NMEA.gps_position;

import gps.NMEA.sentences.NMEASentenceTypes;

public class GPSPositionHistory {

    private static final String NEW_LINE = "\n";
    private GPSPosition currentPosition;
    private GPSPosition lastPosition;
    private GPSPosition secondLastPosition;
    private GPSPosition thirdLastPosition;
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
        assert currentPosition!= null;
        this.currentPosition = currentPosition;
    }

    public GPSPosition getCurrentPosition(){
        return this.currentPosition;
    }

    public void addLastPosition(GPSPosition lastPosition){
        assert lastPosition != null;
        this.lastPosition = lastPosition;
    }

    public GPSPosition getLastPosition(){
        return this.lastPosition;
    }

    public void addSecondLastPosition(GPSPosition previousLasttPosition){
        assert previousLasttPosition != null;
        this.secondLastPosition = previousLasttPosition;
    }

    public void addThirdLastPosition(GPSPosition thirdLastPosition){
        assert thirdLastPosition != null;
        this.thirdLastPosition = thirdLastPosition;
    }

    public GPSPosition getThirdLastPosition(){
        return this.thirdLastPosition;
    }

    public GPSPosition getSecondLastPosition(){
        return this.secondLastPosition;
    }

    public void addPositions(GPSPosition currentPosition, GPSPosition lastPosition,GPSPosition previousLasttPosition, GPSPosition thirdLastPosition){
        this.addCurrentPosition(currentPosition);
        this.addLastPosition(lastPosition);
        this.addSecondLastPosition(previousLasttPosition);
        this.addThirdLastPosition(thirdLastPosition);
    }

    public String toString(){
        return   "Current Position: " + this.getCurrentPosition() + NEW_LINE
                + "Last Position: " + this.getLastPosition() + NEW_LINE
                + "Previous Last Position: " + this.getSecondLastPosition() + NEW_LINE
                + "Third Last Position: " + this.getThirdLastPosition() + NEW_LINE;
    }
}
