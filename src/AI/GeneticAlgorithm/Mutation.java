package AI.GeneticAlgorithm;

import AI.GeneticAlgorithm.Chromosome;

public interface Mutation {
    static double MUTATION_RATE;
    public Chromosome mutate(Chromosome in);
}
