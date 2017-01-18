package faultInjection.perturbation_functions.down_counter;

import annotations.MockClass;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CallbackCountDownBuilderTest {
    private static boolean isNotified;

    @Test
    @Tag("Integration Test")
    public void shouldCallBackAfter100MilliSeconds() throws InterruptedException {

        // given
        CallbackCountDownBuilder builder = new CallbackCountDownBuilder();
        MockCallbackEventImplClass event = new MockCallbackEventImplClass();

        // when
        builder.registerEvent(event).presetCountInSeconds(10)
                                    .setTimerDelay(0)
                                    .setTimerPeriod(10)
                                    .startCountDown();

        /*
         Spend some calculation time to simulate the asynchronous behavior of the callback down counter.
         The timer runtime is assumed to 100 ms (10 * 10 ms). To make the test less flaky the time was doubled
         plus 50 ms
         */
        Thread.sleep(250);

        // then
        assertTrue(isNotified);
    }

    @MockClass
    private class MockCallbackEventImplClass implements CallbackEvent {
        @Override
        public void notifyToPerturb() {isNotified = true;}
    }
}