package annotations;

import org.junit.experimental.categories.Category;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Disabled("Only run on demand ind separate pipeline")
public @interface End2EndTest {
}

