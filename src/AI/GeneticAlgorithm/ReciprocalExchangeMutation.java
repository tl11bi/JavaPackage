package AI.GeneticAlgorithm;
import java.util.ArrayList;

//swap two cities
public class ReciprocalExchangeMutation implements Mutation {
    @Override
    public Chromosome mutate(Chromosome in) {
        ArrayList<Integer> parent = in.getPerm();
        ArrayList<Integer> child = new ArrayList<>();
        int a = random.nextInt(GAShared.DIMENSION);
        int b;
        while(true) {
            b = random.nextInt(GAShared.DIMENSION);
            if(a!=b) break;
        }
        child.addAll(parent);

        Integer item = child.get(a);
        child.set(a, child.get(b));
        child.set(b, item);
        Chromosome c1 = in.getNextChromosome();
        c1.setPerm(child);
        return c1;
    }

    @Override
    public String toString() {
        return "Reciprocal Exchange Mutation";
    }
}
