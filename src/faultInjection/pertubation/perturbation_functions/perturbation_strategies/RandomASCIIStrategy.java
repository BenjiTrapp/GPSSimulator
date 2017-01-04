package faultInjection.pertubation.perturbation_functions.perturbation_strategies;

import faultInjection.communication_jammer.ComJammer;
import faultInjection.pertubation.perturbation_functions.modes.PerturbationModes;
import gps.data.GPSData;

import java.net.Socket;
import java.util.Random;

public class RandomASCIIStrategy extends AbstractPerturbationStrategy {

    private static final double DASH_COORDINATES_FACTOR = 42.0;
    private ComJammer comJammer;

    public RandomASCIIStrategy() {
        super(PerturbationModes.DASH);
        this.comJammer = ComJammer.getInstance();
    }

    public RandomASCIIStrategy(Socket socket) {
        super(PerturbationModes.DASH);
        ComJammer.initComJammer(socket);
        this.comJammer = ComJammer.getInstance();
    }

    //FIXME Einbau ist so noch nicht richtig ...

    @Override
    public void perturb(String line2perturb) {
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

        comJammer.send(sb.toString());
    }
}