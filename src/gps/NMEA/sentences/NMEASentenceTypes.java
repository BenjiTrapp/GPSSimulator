package gps.NMEA.sentences;

public enum NMEASentenceTypes {
    GPGGA,
    GPVTG,
    GPGSA,
    GPRMC;

    public String getSentenceType() {
        return "$" + this.name();
    }

    public String toString() {
        return this.name();
    }

    public static boolean isValidType(String type) {
        for (NMEASentenceTypes enumType : NMEASentenceTypes.values()) {
            if (type.equals(enumType.toString()))
                return true;
        }
        return false;
    }

    public static boolean containsValidType(String stringWithPossibleType) {
        for (NMEASentenceTypes enumType : NMEASentenceTypes.values()) {
            if (stringWithPossibleType.contains(enumType.toString()))
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

        throw  new TypeNotPresentException(type, new Throwable("Passed type was not recognized"));
    }
}

