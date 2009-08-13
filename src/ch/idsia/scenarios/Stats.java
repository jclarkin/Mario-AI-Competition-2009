package ch.idsia.scenarios;

import ch.idsia.ai.agents.RegisterableAgent;
import ch.idsia.ai.agents.Agent;
import ch.idsia.ai.agents.ai.TimingAgent;
import ch.idsia.tools.EvaluationOptions;
import ch.idsia.tools.CmdLineOptions;
import ch.idsia.tools.Evaluator;
import ch.idsia.tools.EvaluationInfo;
import ch.idsia.utils.StatisticalSummary;

public class Stats {

    final static int numberOfTrials = 100;

    public static void main(String[] args) {

        Agent controller = RegisterableAgent.load ("com.reddit.programming.mario.BestFirstAgent");
        final int startingSeed = Integer.parseInt ("0");
        doStats (new TimingAgent(controller), startingSeed);
    }

    public static void doStats (Agent agent, int startingSeed) {
        TimingAgent controller = new TimingAgent (agent);
        RegisterableAgent.registerAgent (controller);
        EvaluationOptions options = new CmdLineOptions(new String[0]);

        options.setMaxAttempts(1);
        options.setVisualization(false);
        options.setMaxFPS(true);
        System.out.println("Testing controller " + controller + " with starting seed " + startingSeed);

        double competitionScore = 0;

        competitionScore += testConfig (controller, options, startingSeed, 0, true);
        competitionScore += testConfig (controller, options, startingSeed, 0, false);
        competitionScore += testConfig (controller, options, startingSeed, 3, true);
        competitionScore += testConfig (controller, options, startingSeed, 3, false);
        competitionScore += testConfig (controller, options, startingSeed, 5, true);
        competitionScore += testConfig (controller, options, startingSeed, 5, false);
        //testConfig (controller, options, startingSeed, 8, true);
        //testConfig (controller, options, startingSeed, 8, false);
        competitionScore += testConfig (controller, options, startingSeed, 10, true);
        competitionScore += testConfig (controller, options, startingSeed, 10, false);
        //testConfig (controller, options, startingSeed, 15, true);
        //testConfig (controller, options, startingSeed, 15, false);
        //testConfig (controller, options, startingSeed, 20, true);
        //testConfig (controller, options, startingSeed, 20, false);
        System.out.println("Competition score: " + competitionScore);
    }

    public static double testConfig (TimingAgent controller, EvaluationOptions options, int seed, int level, boolean paused) {
        options.setLevelDifficulty(level);
        options.setPauseWorld(paused);
        StatisticalSummary ss = test (controller, options, seed, level);
        System.out.printf("Level %d %s %.4f (%.4f) (min %.4f max %.4f) (avg time %.4f)\n",
                level, paused ? "paused" : "unpaused",
                ss.mean(), ss.sd(), ss.min(), ss.max(), controller.averageTimeTaken());
        return ss.mean();
    }


    public static StatisticalSummary test (Agent controller, EvaluationOptions options, int seed, int level) {
        StatisticalSummary ss = new StatisticalSummary ();
        for (int i = 0; i < numberOfTrials; i++) {
            options.setLevelRandSeed(seed + i);
            controller.reset();
            options.setAgent(controller);
            Evaluator evaluator = new Evaluator (options);
            EvaluationInfo result = evaluator.evaluate().get(0);
			System.out.printf("  map seed %d diff %2d -> %f\n", seed+i, level, result.computeDistancePassed());
            ss.add (result.computeDistancePassed());
        }
        return ss;
    }


}
