package solvers.pso;

import solvers.ArrayUtil;
import solvers.model.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SwapOperator {
    private final int firstIndex;
    private final int secondIndex;

    public SwapOperator(int firstIndex, int secondIndex) {
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    public void apply(Tour tour){
        tour.swapCity(firstIndex,secondIndex);
    }

    public static void apply(Tour tour,List<SwapOperator> operators){
        for(SwapOperator operator : operators){
            operator.apply(tour);
        }
    }

    public static void apply(Tour tour,List<SwapOperator> operators,double probability){

        for(SwapOperator operator : operators){
            if(Math.random()<probability) {
                operator.apply(tour);
            }
        }
    }
    public static List<SwapOperator> generateRandomSwapSequence(Tour tour){
        Random rand = new Random();
        List<SwapOperator> swaps = new ArrayList<>();
        swaps.add(new SwapOperator(rand.nextInt(tour.tourSize()),rand.nextInt(tour.tourSize())));
        return swaps;
    }

    public static List<SwapOperator> mergeSequences(List<SwapOperator> v1,List<SwapOperator> v2
            ,double alpha,List<SwapOperator> v3, double beta){
        List<SwapOperator> swaps = new ArrayList<>();
        swaps.addAll(v1);
        for(int i=0;i<v2.size();i++){
            if(Math.random()<alpha){
                swaps.add(v2.get(i));
            }
        }
        for(int i=0;i<v3.size();i++){
            if(Math.random()<beta){
                swaps.add(v3.get(i));
            }
        }
        return swaps;
    }
    public static List<SwapOperator> generateBasicSwapSequence(Tour source, Tour target){

        if(source.tourSize()!=target.tourSize()){
            throw new IllegalArgumentException("Two Tour must be in equal length");
        }
        List<SwapOperator> swaps = new ArrayList<>();
        Tour sourceCopy = new Tour(source);
        Tour targetCopy = new Tour(target);
        int index=0;
        while(index<sourceCopy.tourSize()){
            if(!sourceCopy.getCity(index).equals(targetCopy.getCity(index))){
                int findIndex = sourceCopy.findCityIndex(targetCopy.getCity(index));
                sourceCopy.swapCity(index,findIndex);
                swaps.add(new SwapOperator(index,findIndex));
            }
            index++;
        }
        if(!sourceCopy.equals(targetCopy)){
            System.out.println("sourceCopy = " + sourceCopy);
            System.out.println("targetCopy = " + targetCopy);
            throw new IllegalStateException("Broken Swap Sequence Generator");
        }
        return swaps;
    }

    @Override
    public String toString() {
        return "SO("+firstIndex+","+secondIndex+")";
    }
}
