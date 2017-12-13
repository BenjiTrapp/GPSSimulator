package faultInjection.pertubation

import faultInjection.fault_library.PerturbationBuilder
import faultInjection.fault_library.perturbation_strategies.RandomASCIIStrategy
import faultInjection.fault_library.perturbation_strategies.StuckAtErrorStrategy
import spock.lang.Specification
import spock.lang.Unroll

import static faultInjection.fault_library.PerturbationBuilder.PerturbationModes.*
import static org.hamcrest.Matchers.*
import static spock.util.matcher.HamcrestSupport.*

class PerturbationBuilderSpec extends Specification {

    def "Should create valid perturbation Builder"() {
        when:
        def perturbationBuilder = new PerturbationBuilder().addStrategy(new StuckAtErrorStrategy())
                                                           .withTimerPeriod(10)
                                                           .withTimerDelay(0)
                                                           .withPresetCountInSeconds(1)
                                                           .useRandomnessForConfiguration()
                                                           .addStrategy(new RandomASCIIStrategy())

        then:
        perturbationBuilder instanceof PerturbationBuilder
        perturbationBuilder != null
        notThrown()
    }



    def "Should build correct PerturbationStrategies"() {
        given:
        def perturbationBuilder = new PerturbationBuilder().addStrategy(new StuckAtErrorStrategy())
                                                           .addStrategy(new RandomASCIIStrategy())

        when:
        perturbationBuilder.build()

        then:
        perturbationBuilder instanceof PerturbationBuilder
        perturbationBuilder != null
        notThrown()
    }


    @Unroll
    def "Should throw AssertionErrorException due to a negative argument"() {
        when:
        new PerturbationBuilder().withTimerPeriod(period)
                                 .withTimerDelay(delay)
                                 .withPresetCountInSeconds(presetCount)
                                 .addStrategy(strategy)
        then:
        thrown(expectation)

        where:
        period | delay | presetCount | strategy                  || expectation
        -1     | 1     | 1           | new StuckAtErrorStrategy()|| AssertionError
        1      | -1    | 1           | new StuckAtErrorStrategy()|| AssertionError
        1      | 1     | -1          | new StuckAtErrorStrategy()|| AssertionError
        1      | 1     | 1           | null                      || AssertionError
    }

    def "Should NOT trow any AssertErrorException when a passed argument is zero"() {
        given:
        def perturbationBuilder

        when:
        perturbationBuilder = new PerturbationBuilder().withPresetCountInSeconds(0)
                                                       .withTimerDelay(0)
                                                       .withTimerPeriod(0)

        then:
        notThrown()
        perturbationBuilder != null
    }

    def "Should randomize the config if randomness is used"(){
        given:
        def perturbationBuilder = new PerturbationBuilder().withPresetCountInSeconds(1)
                                                           .withTimerDelay(1)
                                                           .withTimerPeriod(1)


        when:
        perturbationBuilder.useRandomnessForConfiguration().build()
        def configMap = perturbationBuilder.getConfiguration()

        then:
        configMap.size() == 3
        that configMap, hasKey(COUNT)
        that configMap, hasKey(PERIOD)
        that configMap, hasKey(DELAY)
        that configMap.get(COUNT), greaterThanOrEqualTo(1) // One is explicit allowed as lowest boundary
        that configMap.get(PERIOD), greaterThan(1)
        that configMap.get(DELAY), greaterThan(1)
    }
}
