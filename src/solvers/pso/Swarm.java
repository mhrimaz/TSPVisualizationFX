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
    private int steadyState;
    private List<Particle> particles;
    private Tour gBest;

    public Swarm(int swarmSize, int maxIteration,int steadyState) {
        this.swarmSize = swarmSize;
        this.maxIteration = maxIteration;
        this.steadyState = steadyState;
        particles = new ArrayList<>();
        for(int i=0;i<this.swarmSize;i++){
            particles.add(new Particle());
        }
        gBest = particles.get(0).getCurrentSolution();
    }

    @Override
    public Tour solve(CityManager cityManager, Consumer<Tour> tourConsumer) {
        double minDist = Double.MAX_VALUE;
        int steadyStateCount = 0;
        for(int i=0;i<maxIteration&&steadyStateCount<steadyState;i++){
            Tour tempGBest = gBest;
            for(Particle particle:particles){
                particle.setGBest(gBest);
                particle.updatePosition();
                if(particle.getPBest().getDistance()<tempGBest.getDistance()){
                    tempGBest = new Tour(particle.getPBest());
                }
            }
            gBest = new Tour(tempGBest);
            if(minDist>gBest.getDistance()){
                minDist = gBest.getDistance();
                steadyStateCount = 0;
            }else{
                steadyStateCount++;
            }
            if(i%10==0){
                System.out.println("gBest.getDistance() = "+i+" :" + gBest.getDistance());
            }

        }
        return gBest;
    }
}
