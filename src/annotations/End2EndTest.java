package annotations;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.commons.meta.API;

import java.lang.annotation.*;

import static org.junit.platform.commons.meta.API.Usage.Maintained;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag("E2ETest")
//@Disabled("Only run on demand ind separate pipeline")
@Testable
@API(Maintained)
public @interface End2EndTest {
}

