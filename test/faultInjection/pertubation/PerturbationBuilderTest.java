package faultInjection.pertubation;

import faultInjection.fault_library.PerturbationBuilder;
import faultInjection.fault_library.perturbation_strategies.RandomASCIIStrategy;
import faultInjection.fault_library.perturbation_strategies.StuckAtErrorStrategy;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerturbationBuilderTest {

    @Test
    @Tag("HappyPath")
    public void shouldCreatePerturbationBuilder() {
        // given
        PerturbationBuilder perturbationBuilder;

        // when
        perturbationBuilder = new PerturbationBuilder().addStrategy(new StuckAtErrorStrategy())
                .setTimerPeriod(10)
                .setTimerDelay(0)
                .presetCountInSeconds(1)
                .useRandomnessForConfiguration()
                .addStrategy(new RandomASCIIStrategy());

        // then
        assertNotNull(perturbationBuilder);
    }

    @Test
    @Tag("HappyPath")
    public void shouldBuildCorrectPerturbationStrategies() {
        // given
        PerturbationBuilder perturbationBuilder = new PerturbationBuilder().addStrategy(new StuckAtErrorStrategy())
                .addStrategy(new RandomASCIIStrategy());

        // when
        try {
            perturbationBuilder.build();

            // then
        } catch (Exception e) {
            fail("Building should not cause an exception");
            e.printStackTrace();
        }

        assertNotNull(perturbationBuilder);
    }

    @Test
    @Tag("SadPath")
    public void shouldBThrowAssertErrorExceptionDueToNegativeTimerPeriod() {
        // given
        PerturbationBuilder perturbationBuilder;

        try {
            // when
            perturbationBuilder = new PerturbationBuilder().setTimerPeriod(-100);
            assertNotNull(perturbationBuilder);
            // then
        } catch (AssertionError ok) {
            // all fine!
        } catch (Exception failed) {
            fail("The correct Exception wasn't triggered as assumed");
        }
    }

    @Test
    @Tag("SadPath")
    public void shouldBThrowAssertErrorExceptionDueToNegativeTimerDelay() {
        // given
        PerturbationBuilder perturbationBuilder;

        try {
            // when
            perturbationBuilder = new PerturbationBuilder().setTimerDelay(-1);
            assertNotNull(perturbationBuilder);
            // then
        } catch (AssertionError ok) {
            // all fine!
        } catch (Exception failed) {
            fail("The correct Exception wasn't triggered as assumed");
        }
    }

    @Test
    @Tag("ExceptionalPath")
    public void shouldBThrowAssertErrorExceptionDueToNegativePresetCount() {
        // given
        PerturbationBuilder perturbationBuilder;

        try {
            // when
            perturbationBuilder = new PerturbationBuilder().presetCountInSeconds(-1);
            assertNotNull(perturbationBuilder);
            // then
        } catch (AssertionError ok) {
            // all fine!
        } catch (Exception failed) {
            fail("The correct Exception wasn't triggered as assumed");
        }
    }

    @Test
    @Tag("ExceptionalPath")
    public void shouldNOTThrowAssertErrorExceptionWhenPresetCountIsZero() {
        // given
        PerturbationBuilder perturbationBuilder;

        try {
            // when
            perturbationBuilder = new PerturbationBuilder().presetCountInSeconds(0);
            assertNotNull(perturbationBuilder);
            // then
        } catch (Exception failed) {
            fail("The correct Exception wasn't triggered as assumed");
        }
    }

    @Test
    @Tag("ExceptionalPath")
    public void shouldNOTThrowAssertErrorExceptionWhenTimerDelayIsZero() {
        // given
        PerturbationBuilder perturbationBuilder;

        try {
            // when
            perturbationBuilder = new PerturbationBuilder().setTimerDelay(0);
            assertNotNull(perturbationBuilder);
            // then
        } catch (Exception failed) {
            fail("The correct Exception wasn't triggered as assumed");
        }
    }

    @Test
    @Tag("ExceptionalPath")
    public void shouldNOTThrowAssertErrorExceptionWhenTimerPeriodIsZero() {
        // given
        PerturbationBuilder perturbationBuilder;

        try {
            // when
            perturbationBuilder = new PerturbationBuilder().setTimerPeriod(0);
            assertNotNull(perturbationBuilder);
            // then
        } catch (Exception failed) {
            fail("The correct Exception wasn't triggered as assumed");
        }
    }
}