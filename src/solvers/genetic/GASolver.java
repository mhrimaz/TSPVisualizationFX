package solvers.genetic;

import solvers.TSPSolver;
import solvers.model.CityManager;
import solvers.model.Tour;

import java.util.function.Consumer;

public class GASolver implements TSPSolver {
    Tour bestTour;

    @Override
    public Tour solve(CityManager cityManager, Consumer<Tour> tourConsumer) {
        // Initialize population

        Population pop = new Population(50, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());

        // Evolve population for 100 generations
        pop = GeneticAlgorithmEngine.evolvePopulation(pop);
        for (int i = 0; i < 100; i++) {
            pop = GeneticAlgorithmEngine.evolvePopulation(pop);
        }
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        return pop.getFittest();
    }
}
