package AI.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*Tournament selection, a k members at random will be picked, and then best of two will be selected*/
public class TournamentSelection implements Selection {
    static int TOURNAMENTSELECTIONSIZE;


    @Override
    public ChromosomePair select(Population pop) {
        Chromosome p1 = getNext(pop);
        Chromosome p2;
        //to avoid having exact same parent
        do{
            p2 = getNext(pop);
        }while (p1.equals(p2));
        return new ChromosomePair(p1,p2);
    }

    private Chromosome getNext(Population pop){
        List<Chromosome> tmp= pop.getPopulation();
        List<Chromosome> newList = new ArrayList<>();
        //get n unique individuals
        for (int i = 0; i < TOURNAMENTSELECTIONSIZE; i++) {
            Collections.shuffle(tmp);
            newList.add(tmp.get(i));
        }
        return Collections.min(newList);
    }

    @Override
    public String toString() {
        return "Tournament Selection";
    }
}
