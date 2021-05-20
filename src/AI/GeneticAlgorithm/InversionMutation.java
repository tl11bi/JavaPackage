package AI.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;

//reverse the perm
public class InversionMutation implements Mutation {
    @Override
    public Chromosome mutate(Chromosome in) {
        ArrayList<Integer> parent = in.getPerm();
        ArrayList<Integer> child = new ArrayList<>();

        child.addAll(parent);
        Collections.reverse(child);
        Chromosome c1 = in.getNextChromosome();
        c1.setPerm(child);
        return c1;    }


    @Override
    public String toString() {
        return "Inversion Mutation";
    }
}
