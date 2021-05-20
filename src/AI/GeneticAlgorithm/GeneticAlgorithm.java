package AI.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {

    protected static int NUM_ELITE;
     static char[][]shreddedDocument = null;
    private static final String[] ShreddedDocURLS = {"Docs/SillyExample-shredded.txt","Docs/document1-shredded.txt","Docs/document2-shredded.txt","Docs/document3-shredded.txt"};
    private static String ShreddedDocURL = null;
    protected static int DIMENSION;

    public static int NUM_GENERATIONS;
    static Random random;
    private Mutation mutation;
    private Selection selection;
    private Crossover crossover;

    public GeneticAlgorithm(Crossover crossover, Mutation mutation, Selection selection,
                            double crossoverRate, double mutationRate, double uniformOrderRatio,
                            int populationSize, int initPopSize, int numGenerations, int tournamentSelectionSize, int numElite,
                            int seed, int docSelection){
        this.mutation = mutation;
        this.crossover = crossover;
        this.selection = selection;
        this.POPULATION_SIZE = populationSize;
        this.NUM_GENERATIONS = numGenerations;
        this.CROSSOVER_RATE = crossoverRate;
        this.MUTATION_RATE = mutationRate;
        this.INITPOPSIZE = initPopSize;
        this.TOURNAMENTSELECTIONSIZE = tournamentSelectionSize;
        this.NUM_ELITE = numElite;
        random = new Random(seed);
        UniformOrderCrossover.UNIFORMORDERRATIO = uniformOrderRatio;
        GAShared.setShreddedDocURL(docSelection);
    }

    public Population init(Population pop, Chromosome ch){

        for (int i = 0; i < INITPOPSIZE; i++) {
            Chromosome nextCh = ch.getNextChromosome();
            pop.addChromosome(nextCh);
        }
        return pop;
    }

    public Population genNextPopulation(Population current){
        Population nextGen = current.nextGeneration();
        //keep some elite member of the previous generation
        ArrayList<Chromosome> all = new ArrayList<Chromosome>();
        all.addAll(current.getPopulation());
        all.sort(Chromosome::compareTo);
        nextGen.getPopulation().addAll(all.subList(0,NUM_ELITE));
        while (nextGen.getPopulationSize() < POPULATION_SIZE){
            ChromosomePair pair = selection.select(current);

            if(random.nextDouble()<CROSSOVER_RATE){
                pair = crossover.crossover(pair);
            }

            if(random.nextDouble()<MUTATION_RATE){
                pair = new ChromosomePair(mutation.mutate(pair.getFirst()),mutation.mutate(pair.getSecond()));
            }
            nextGen.addChromosome(pair.getFirst());
            if(nextGen.getPopulationSize() < POPULATION_SIZE) nextGen.addChromosome(pair.getSecond());
        }
        return nextGen;
    }



}
