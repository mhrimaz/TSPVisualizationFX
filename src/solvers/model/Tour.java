/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solvers.model;

import solvers.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author mhrimaz
 */
public class Tour implements Comparable<Tour> {

    private City[] tour;
    private double fitness = 0;
    private double distance = 0;

    // Constructs a blank tour
    public Tour() {
        tour = new City[CityManager.getInstance().numberOfCities()];

        for (int cityIndex = 0; cityIndex < CityManager.getInstance().numberOfCities(); cityIndex++) {
            setCity(cityIndex, CityManager.getInstance().getCity(cityIndex));
        }
    }


    public Tour(Tour toCopy) {
        tour = toCopy.tour.clone();
        fitness = toCopy.fitness;
        distance = toCopy.distance;
    }

    public void shuffleTour() {
        ArrayUtil.shuffle(tour);
        distance = 0;
        fitness=0;
    }


    public City getCity(int tourPosition) {
        return tour[tourPosition];
    }

    public void setCity(int tourPosition, City city) {
        tour[tourPosition]= city;
        fitness = 0;
        distance = 0;
    }

    public int findCityIndex(City city){
        return ArrayUtil.findIndex(tour,city);
    }

    // Gets the tours fitness
    public double getFitness() {
        if (fitness == 0) {
            fitness = 1 /getDistance();
        }
        return fitness;
    }

    // Gets the total distance of the tour
    public double getDistance() {
        if (distance == 0) {
            double tourDistance = 0;
            for (int cityIndex = 0; cityIndex < tourSize(); cityIndex++) {
                City fromCity = getCity(cityIndex);
                City destinationCity = getCity((cityIndex + 1)%tourSize());
                tourDistance += fromCity.distanceTo(destinationCity);
            }
            distance = tourDistance;
        }
        return distance;
    }

    // Get number of cities on our tour
    public int tourSize() {
        return tour.length;
    }

    // Check if the tour contains a city
    public boolean containsCity(City city) {
        return ArrayUtil.contains(tour,city);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour1 = (Tour) o;
        return Arrays.equals(tour, tour1.tour);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tour);
    }

    @Override
    public String toString() {
        StringBuilder geneString = new StringBuilder( "|");
        for (int i = 0; i < tourSize(); i++) {
            geneString.append(getCity(i).toString()).append( "|");
        }
        return geneString.toString();
    }

    @Override
    public int compareTo(Tour o) {
        if (o == null) {
            return -1;
        }
        return (int) (this.distance - o.distance);
    }

    public void swapCity(int firstIndex, int secondIndex) {
        ArrayUtil.swap(tour,firstIndex,secondIndex);
        distance = 0;
        fitness=0;
    }
}
