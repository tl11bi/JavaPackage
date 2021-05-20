package AI.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;

public class OrderCrossover implements Crossover {
    @Override
    public ChromosomePair crossover(ChromosomePair in) {
        ArrayList<Integer> parent1 = in.getFirst().getPerm();
        ArrayList<Integer> parent2 = in.getSecond().getPerm();
        ArrayList<Integer> child1 = new ArrayList<>();
        ArrayList<Integer> child2 = new ArrayList<>();
        ArrayList<Integer> child1s1 = new ArrayList<>();
        ArrayList<Integer> child2s2 = new ArrayList<>();
        int a = GAShared.getRandom().nextInt(GAShared.DIMENSION);
        int b;
        while(true) {
            b = GAShared.getRandom().nextInt(GAShared.DIMENSION);
            if(a!=b) break;
        }

        int lb = Math.min(a, b);
        int ub = Math.max(a, b);

        child1.addAll(parent1.subList(lb, ub + 1));
        child1s1.addAll(child1);
        child2.addAll(parent2.subList(lb, ub + 1));
        child2s2.addAll(child2);


        for (int i = 1; i <= GAShared.DIMENSION; i++) {
            int idx = (ub + i) % GAShared.DIMENSION;

            Integer item1 = parent1.get(idx);
            Integer item2 = parent2.get(idx);

            if (!child1s1.contains(item2)) {
                child1.add(item2);
                child1s1.add(item2);
            }

            if (!child2s2.contains(item1)) {
                child2.add(item1);
                child2s2.add(item1);
            }
        }
        Collections.rotate(child1, lb);
        Collections.rotate(child2, lb);
        Chromosome c1 = in.getFirst().getNextChromosome();
        c1.setPerm(child1);
        Chromosome c2 = in.getSecond().getNextChromosome();
        c2.setPerm(child2);
        return new ChromosomePair(c1,c2);
    }



    @Override
    public String toString() {
        return "Order Crossover";
    }
}
