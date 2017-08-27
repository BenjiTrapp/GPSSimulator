package annotations;

import faultInjection.fault_library.modes.PerturbationModes;
import java.lang.annotation.*;



@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PerturbationFunction {
    PerturbationModes value();
}

