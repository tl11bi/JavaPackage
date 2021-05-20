package AI.GeneticAlgorithm;

import java.util.ArrayList;

import static AI.GeneticAlgorithm.GeneticAlgorithm.random;
import static AI.GeneticAlgorithm.GeneticAlgorithm.DIMENSION;

public class UniformOrderCrossover implements Crossover {
    static double UNIFORMORDERRATIO;

    @Override
    public ChromosomePair crossover(ChromosomePair in) {
        ArrayList<Integer> parent1 = in.getFirst().getPerm();
        ArrayList<Integer> parent2 = in.getSecond().getPerm();
        ArrayList<Integer> child1 = new ArrayList<>(DIMENSION);
        ArrayList<Integer> child2 = new ArrayList<>(DIMENSION);


        for (int index = 0; index < DIMENSION; index++) {

            if (random.nextDouble() < UNIFORMORDERRATIO) {
                // swap the bits -> take other parent
                child1.add(parent2.get(index));
                child2.add(parent1.get(index));
            } else {
                child1.add(parent1.get(index));
                child2.add(parent2.get(index));
            }
        }

        Chromosome c1 = in.getFirst().getNextChromosome();
        c1.setPerm(child1);
        Chromosome c2 = in.getSecond().getNextChromosome();
        c2.setPerm(child2);
        return new ChromosomePair(c1,c2);
    }



    @Override
    public String toString() {
        return "Uniform Order Crossover";
    }
}
