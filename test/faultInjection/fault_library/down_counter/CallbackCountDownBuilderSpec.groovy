package faultInjection.fault_library.down_counter

import annotations.IntegrationTest
import annotations.MockClass
import spock.lang.Specification
import spock.lang.Subject

class CallbackCountDownBuilderSpec extends Specification {
    private static boolean isNotified = false

    @IntegrationTest
    def shouldCallBackAfter100MilliSeconds() throws InterruptedException {
        given:
        def builder = new CallbackCountDownBuilder()
        @Subject def event = new  MockCallbackEventImplClass()

        when:
        builder.registerEvent(event).presetCountInSeconds(10)
                .setTimerDelay(0)
                .setTimerPeriod(10)
                .startCountDown()

        /*
         Spend some calculation time to simulate the asynchronous behavior of the callback down counter.
         The timer runtime is assumed to 100 ms (10 * 10 ms). To make the test less flaky the time was doubled
         */
        Thread.sleep(200)

        then:
        isNotified
    }

    @MockClass
    class MockCallbackEventImplClass implements CallbackEvent {
        @Override
        void notifyToPerturb() {isNotified = true}
    }
}
