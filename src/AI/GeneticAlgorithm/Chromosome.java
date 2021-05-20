package AI.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;

/*this method is a abstract class for chromosome, which we allows all kinds of function*/
public abstract class Chromosome implements Comparable<Chromosome>, Fitness {
    private double fitness = Double.NEGATIVE_INFINITY;
    private ArrayList<Integer> perm = null;//permutation mask
    private static ArrayList<Integer> sequence = null;

    public void setPerm(ArrayList<Integer> perm) {
        this.perm = perm;
    }

    public ArrayList<Integer> getPerm() {
        if(perm == null){
            perm = new ArrayList<>(GAShared.DIMENSION);
            perm.addAll(getSequence());
            Collections.shuffle(perm);
        }
        return perm;
    }

    //generate numbers from 0 to N-1
    public static ArrayList<Integer> getSequence() {
        if(sequence==null){
            sequence = new ArrayList<>(GAShared.DIMENSION);
            for (int i = 0; i < GAShared.DIMENSION; i++) {
                sequence.add(i);
            }
        }
        return sequence;
    }

    /*get fitness*/
    public double getFitness(){
        if(this.fitness == Double.NEGATIVE_INFINITY) this.fitness = fitness();
        return this.fitness;
    }

    @Override
    public boolean equals(Object obj) {
        ArrayList<Integer> other = ((Chromosome) obj).getPerm();
        ArrayList<Integer> perm = this.getPerm();
        return other.equals(perm);
    }

    //comparsion
    @Override
    public int compareTo(Chromosome o) {
        return Double.compare(this.getFitness(), o.getFitness());
    }

    public abstract Chromosome getNextChromosome();
}
