package AI.GeneticAlgorithm;//population collection

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population{
    static int INITPOPSIZE;
    public static int POPULATION_SIZE;

    private ArrayList<Chromosome> population;
    //return population size
    int getPopulationSize(){
        return getPopulation().size();
    }

    //return the next generation
    Population nextGeneration(){
        return new Population();
    }
    //add chromosome to the list
    void addChromosome(Chromosome chromosome){
        getPopulation().add(chromosome);
    }
    //return the fittest chromosome from list
    public List<Chromosome> getPopulation(){
        if(population == null) population = new ArrayList<Chromosome>();
        return population;
    }




    public Chromosome getBestChromosome(){
        return Collections.min(getPopulation());
    }
}
