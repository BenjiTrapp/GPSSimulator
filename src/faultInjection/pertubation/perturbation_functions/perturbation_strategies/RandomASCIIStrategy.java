package faultInjection.pertubation.perturbation_functions.perturbation_strategies;

import communication.StringReader;
import communication.StringWriter;
import faultInjection.pertubation.perturbation_functions.modes.PerturbationModes;

import java.util.Random;

public class RandomASCIIStrategy extends AbstractPerturbationStrategy {

    public RandomASCIIStrategy() {
        super(PerturbationModes.RANDOM_ASCII);
    }

    @Override
    public void perturb() {
        String line2perturb = StringReader.getInstance().receive();
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(line2perturb);

        int stringLength = line2perturb.length();
        int startValue = 0;
        int maxStep = (stringLength >= 3) ? stringLength >> 1 : 1;

        if (stringLength > 3)
            startValue = rnd.nextInt(2);

        for (int i = startValue; i < stringLength; i += rnd.nextInt(maxStep)) {
            char tmp = (char) rnd.nextInt(255);
            sb.setCharAt(i, tmp);

            //Assert that this loop wont stuck
            if (i > stringLength || maxStep <= 1)
                break;
        }

        StringWriter.getInstance().send(sb.toString());
    }
}