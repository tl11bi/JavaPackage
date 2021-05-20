package AI.GeneticAlgorithm;

import java.util.ArrayList;
import static AI.GeneticAlgorithm.GeneticAlgorithm.random;
import static AI.GeneticAlgorithm.GeneticAlgorithm.DIMENSION;

//basically this method will select a city and then insert into a random place
public class InsertionMutation implements Mutation {
    @Override
    public Chromosome mutate(Chromosome in) {
        ArrayList<Integer> parent = in.getPerm();
        ArrayList<Integer> child = new ArrayList<>();
        int a = random.nextInt(DIMENSION);
        int b;
        while(true) {
            b = GAShared.getRandom().nextInt(GAShared.DIMENSION);
            if(a!=b) break;
        }
        child.addAll(parent);
        Integer item = child.remove(a);
        child.add(b,item);
        Chromosome c1 = in.getNextChromosome();
        c1.setPerm(child);
       return c1;
    }

    @Override
    public String toString() {
        return "Insertion Mutation";
    }
}
