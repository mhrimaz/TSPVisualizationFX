package solvers.pso;

import solvers.model.Tour;

import java.util.List;
import java.util.Random;

public class Particle {
    private Tour currentSolution;
    private List<SwapOperator> velocity;

    private Tour pBest;
    private Tour gBest;

    private static final double ALPHA = 0.85;
    private static final double BETA = 0.85;

    public Particle(){
        this(new Tour());
    }
    public Particle(Tour solution){
        solution.shuffleTour();
        setCurrentSolution(solution);
        setVelocity(SwapOperator.generateRandomSwapSequence(currentSolution));
        pBest = currentSolution;
        gBest = currentSolution;
    }

    public void updatePosition(){
        //Step 3-1
        List<SwapOperator> swapsToPBest = SwapOperator.generateBasicSwapSequence(pBest, currentSolution);
        //Step 3-2
        List<SwapOperator> swapsToGBest = SwapOperator.generateBasicSwapSequence(gBest, currentSolution);
        //Step 3-3
        setVelocity(SwapOperator.mergeSequences(velocity,swapsToPBest,ALPHA,swapsToGBest,BETA));
        //Step 3-4
        SwapOperator.apply(currentSolution,velocity);
        //Step 3-5
        if(currentSolution.getDistance()<pBest.getDistance()){
            pBest = new Tour(currentSolution);
        }
    }

    public double getDistance() {
        return currentSolution.getDistance();
    }


    private void setCurrentSolution(Tour currentSolution) {
        this.currentSolution = currentSolution;
    }

    private void setVelocity(List<SwapOperator> velocity) {
        this.velocity = velocity;
    }

    public Tour getPBest() {
        return pBest;
    }

    public void setGBest(Tour gBest) {
        this.gBest = gBest;
    }

    public Tour getCurrentSolution(){
        return currentSolution;
    }
}
