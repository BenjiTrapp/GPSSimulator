package gps.NMEA.sentences;

import gps.NMEA.utils.ChecksumUtilities;

public class NMEASentenceBuilder {

    private StringBuilder builder;
    private static final String DELIMITER = ",";

    NMEASentenceBuilder(NMEASentenceTypes type){
        this.builder = new StringBuilder(type.getSentenceType() + DELIMITER);
    }

    public String build(){
        return this.builder.toString();
    }

    public NMEASentenceBuilder append(String line){
        this.builder.append(line)
                    .append(DELIMITER);
        return this;
    }

    public NMEASentenceBuilder appendNotDelimited(String line){
        this.builder.append(line);
        return this;
    }

    public NMEASentenceBuilder appendChecksum(){

        this.builder.deleteCharAt(builder.length() - 1)
                    .append("*");

        final String tmp = this.build();

        this.builder.append(ChecksumUtilities.getCRC(tmp));
        return this;
    }

    public NMEASentenceBuilder appendChecksum(String mode){

        this.builder.deleteCharAt(builder.length() - 1)
                    .append(mode)
                    .append("*");

        String tmp = this.build();

        this.builder.append(ChecksumUtilities.getCRC(tmp));
        return this;
    }
}
