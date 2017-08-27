package annotations;

import gps.NMEA.sentences.NMEASentenceTypes;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NMEASentence {
    NMEASentenceTypes value();
}
