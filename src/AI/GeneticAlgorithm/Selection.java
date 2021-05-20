package AI.GeneticAlgorithm;

import AI.GeneticAlgorithm.ChromosomePair;
import AI.GeneticAlgorithm.Population;

public interface Selection {

    ChromosomePair select(Population pop);
}
