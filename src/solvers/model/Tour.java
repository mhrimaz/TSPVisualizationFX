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
    private int distance = 0;

    // Constructs a blank tour
    public Tour() {
        tour = new City[CityManager.getInstance().numberOfCities()];
    }

    public void generateIndividual() {
        for (int cityIndex = 0; cityIndex < CityManager.getInstance().numberOfCities(); cityIndex++) {
            setCity(cityIndex, CityManager.getInstance().getCity(cityIndex));
        }

        ArrayUtil.shuffle(tour);
    }

    public City getCity(int tourPosition) {
        return tour[tourPosition];
    }

    public void setCity(int tourPosition, City city) {
        tour[tourPosition]= city;
        fitness = 0;
        distance = 0;
    }

    // Gets the tours fitness
    public double getFitness() {
        if (fitness == 0) {
            fitness = 1 / (double) getDistance();
        }
        return fitness;
    }

    // Gets the total distance of the tour
    public int getDistance() {
        if (distance == 0) {
            int tourDistance = 0;
            for (int cityIndex = 0; cityIndex < tourSize(); cityIndex++) {
                City fromCity = getCity(cityIndex);
                City destinationCity;
                if (cityIndex + 1 < tourSize()) {
                    destinationCity = getCity(cityIndex + 1);
                } else {
                    destinationCity = getCity(0);
                }
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
        String geneString = "|";
        for (int i = 0; i < tourSize(); i++) {
            geneString += getCity(i) + "|";
        }
        return geneString;
    }

    @Override
    public int compareTo(Tour o) {
        if (o == null) {
            return -1;
        }
        return (int) (this.distance - o.distance);
    }
}
