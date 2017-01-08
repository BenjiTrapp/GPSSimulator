package faultInjection.perturbation_functions.perturbation_strategies;

import communication.StringReader;
import communication.StringWriter;
import faultInjection.perturbation_functions.modes.PerturbationModes;

import java.util.Random;

public final class RandomASCIIStrategy extends AbstractPerturbationStrategy {

    public static final int MAX_ASCII_TOKENS = 255;

    public RandomASCIIStrategy() {super(PerturbationModes.RANDOM_ASCII);}

    @Override
    public synchronized void perturb() {
        String line2perturb = StringReader.getInstance().receive();
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(line2perturb);

        int stringLength = line2perturb.length();
        int startValue = 0;
        int maxStep = calculateMaxStepBasedOnStringLen(stringLength);

        if (stringLength > 3)
            startValue = rnd.nextInt(2);


        // Damaging the GPS-Sentence randomly with randomized ASCII-Strings
        for (int i = startValue; i < stringLength; i += rnd.nextInt(maxStep)) {
            char tmp = (char) rnd.nextInt(MAX_ASCII_TOKENS);
            sb.setCharAt(i, tmp);

            //Assert that this loop wont stuck or make silly things
            if (i > stringLength || maxStep <= 1)
                break;
        }

        StringWriter.getInstance().send(sb.toString());
    }

    private int calculateMaxStepBasedOnStringLen(int stringLength) {
        return (stringLength >= 3) ? stringLength >> 1 : 1;
    }
}