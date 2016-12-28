package gps.NMEA.sentences;

public enum  NMEASentenceTypes {
    GPGGA, GPRMC;

    public String getSentenceType(){
        return "$" + this.name();
    }

    public String toString(){
        return this.name();
    }

}
