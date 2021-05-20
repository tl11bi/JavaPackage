package AI.GeneticAlgorithm;

import AI.GeneticAlgorithm.ChromosomePair;

public interface Crossover {

    static double CROSSOVER_RATE;

    ChromosomePair crossover(ChromosomePair in);
}
