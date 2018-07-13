package solvers.pso;

import solvers.TSPSolver;
import solvers.model.CityManager;
import solvers.model.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Swarm implements TSPSolver {
    private int swarmSize;
    private int maxIteration;
    private List<Particle> particles;
    private Tour gBest;

    public Swarm(int swarmSize, int maxIteration) {
        this.swarmSize = swarmSize;
        this.maxIteration = maxIteration;
        particles = new ArrayList<>();
        for(int i=0;i<this.swarmSize;i++){
            particles.add(new Particle());
        }
        gBest = particles.get(0).getCurrentSolution();
    }

    @Override
    public Tour solve(CityManager cityManager, Consumer<Tour> tourConsumer) {
        for(int i=0;i<maxIteration;i++){
            Tour tempGBest = gBest;
            for(Particle particle:particles){

                particle.setGBest(gBest);
                particle.updatePosition();

                if(particle.getPBest().getDistance()<tempGBest.getDistance()){
                    tempGBest = particle.getPBest();
                }
            }
            gBest = tempGBest;

        }
        return gBest;
    }
}
