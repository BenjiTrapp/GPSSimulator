package gps.NMEA.gps_position;

import gps.NMEA.sentences.NMEASentenceTypes;

public class GPSPositionHistory {

    private GPSPosition currentPosition;
    private GPSPosition lastPosition;
    private GPSPosition previousLastPosition;
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

    public void addpreviousLasttPosition(GPSPosition previousLasttPosition){
        assert previousLasttPosition != null;
        this.previousLastPosition = previousLasttPosition;
    }

    public void addThirdLasttPosition(GPSPosition previousLasttPosition){
        assert previousLasttPosition != null;
        this.previousLastPosition = previousLasttPosition;
    }

    public GPSPosition getThirdLastPosition(){
        return this.lastPosition;
    }

    public GPSPosition getPreviousLastPosition(){
        return this.previousLastPosition;
    }

    public void addPositions(GPSPosition currentPosition, GPSPosition lastPosition,GPSPosition previousLasttPosition, GPSPosition thirdLastPosition){
        this.addCurrentPosition(currentPosition);
        this.addLastPosition(lastPosition);
        this.addpreviousLasttPosition(previousLasttPosition);
        this.addThirdLasttPosition(thirdLastPosition);
    }

    public String toString(){
        return   "Current Position: " + this.getCurrentPosition() + "\n"
                + "Last Position: " + this.getLastPosition() + "\n"
                + "Previous Last Position: " + this.getPreviousLastPosition() + "\n"
                + "Third Last Position: " + this.getThirdLastPosition() + "\n";
    }
}
