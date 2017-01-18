package annotations;

import gps.NMEA.sentences.NMEASentenceTypes;
import org.junit.jupiter.api.Tag;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.commons.meta.API;

import java.lang.annotation.*;

import static org.junit.platform.commons.meta.API.Usage.Maintained;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag("NMEASentenceParser")
@Testable
@API(Maintained)
public @interface NMEASentenceParser {
    NMEASentenceTypes value();
}

