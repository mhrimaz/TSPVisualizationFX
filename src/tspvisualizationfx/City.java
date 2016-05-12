/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tspvisualizationfx;

import javafx.geometry.Point2D;

/**
 *
 * @author mhrimaz
 */
public class City implements Comparable<City> {

    private final Point2D location;

    public City(double x, double y) {
        this.location = new Point2D(x, y);
    }

    @Override
    public int compareTo(City o) {
        return (int) this.getLocation().distance(o.getLocation());
    }

    public int distanceTo(City o) {
        return (int) this.getLocation().distance(o.getLocation());
    }

    /**
     * @return the location
     */
    public Point2D getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return location.toString();
    }

}
