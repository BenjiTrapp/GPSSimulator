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

    public NMEASentenceBuilder append(double line){
        this.builder.append(Double.toString(line))
                .append(DELIMITER);
        return this;
    }

    NMEASentenceBuilder appendNotDelimited(String line){
        this.builder.append(line);
        return this;
    }

    NMEASentenceBuilder appendChecksum(){

        this.builder.deleteCharAt(builder.length() - 1);

        final String tmp = this.build();

        this.builder.append("*").append(ChecksumUtilities.getCRC(tmp));
        return this;
    }

    NMEASentenceBuilder appendChecksum(String mode){

        this.builder.deleteCharAt(builder.length() - 1)
                    .append(mode);

        String tmp = this.build();

        this.builder.append("*").append(ChecksumUtilities.getCRC(tmp));
        return this;
    }
}
