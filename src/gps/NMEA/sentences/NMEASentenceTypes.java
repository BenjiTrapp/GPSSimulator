package gps.NMEA.sentences;

public enum NMEASentenceTypes {
    GPGGA, GPRMC;

    public String getSentenceType() {
        return "$" + this.name();
    }

    public String toString() {
        return this.name();
    }

    public static boolean equals(String type) {
        for (NMEASentenceTypes enumType : NMEASentenceTypes.values()) {
            if (type.equals(enumType.toString()))
                return true;
        }
        return false;
    }

    public static NMEASentenceTypes getType(String type) throws TypeNotPresentException {
        assert type != null;

        for (NMEASentenceTypes enumType : NMEASentenceTypes.values()) {
            if (type.equals(enumType.toString()))
                return enumType;
        }

        throw  new TypeNotPresentException(type, new Throwable("Passed type not recognized"));
    }
}

