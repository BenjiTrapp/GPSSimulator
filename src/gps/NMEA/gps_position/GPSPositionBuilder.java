package gps.NMEA.gps_position;

public class GPSPositionBuilder {

    private GPSPosition position= new GPSPosition();

    public GPSPosition build(){
        return this.position;
    }

    public GPSPositionBuilder addTime(Double time){
        assert time != null;
        this.position.setTime(time);
        return this;
    }

    public GPSPositionBuilder addLatitude(Double latitude){
        assert latitude != null;
        this.position.setLatitude(latitude);
        return this;
    }

    public GPSPositionBuilder addLongitude(Double longitude){
        assert longitude!= null;
        this.position.setLatitude(longitude);
        return this;
    }

    public GPSPositionBuilder addQuality(Double quality){
        assert quality != null;
        this.position.setQuality(quality);
        return this;
    }

    GPSPositionBuilder addDirection(Double direction){
        assert direction != null;
        this.position.setDirection(direction);
        return this;
    }

    public GPSPositionBuilder addAltitude(Double altitude){
        assert altitude != null;
        this.position.setAltitude(altitude);
        return this;
    }

    GPSPositionBuilder addVelocity(Double velocity){
        assert velocity != null;
        this.position.setAltitude(velocity);
        return this;
    }

    GPSPositionBuilder addFixed(boolean isFixed){
        this.position.setFixed(isFixed);
        return this;
    }
}
