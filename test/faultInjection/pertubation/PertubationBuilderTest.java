package faultInjection.pertubation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PertubationBuilderTest {

    @Test
    public void shouldCreateValidPerturbationModes(){
        // given
        PertubationBuilder builder = new PertubationBuilder();

        // when
        builder.addPerturbationMode(EPertubationModes.RANDOM_ASCII).addPerturbationMode(EPertubationModes.DASH).build();

        // then
    }

}