package solvers.pso;

import solvers.genetic.GeneticAlgorithmEngine;
import solvers.genetic.Population;
import solvers.model.City;
import solvers.model.CityManager;
import solvers.model.Tour;

public class Test {

    public static void main(String[] args) {
        CityManager.getInstance().addCity(new City(1,1));
        CityManager.getInstance().addCity(new City(2,2));
        CityManager.getInstance().addCity(new City(3,3));
        CityManager.getInstance().addCity(new City(4,4));
        CityManager.getInstance().addCity(new City(5,5));

        Particle particle = new Particle();
        for(int i=0;i<10;i++) {
            System.out.println("particle.getCurrentSolution() = " + particle.getCurrentSolution());
            System.out.println("particle.getVelocity = " + particle.getVelocity());
            particle.updatePosition();
        }
    }
}
