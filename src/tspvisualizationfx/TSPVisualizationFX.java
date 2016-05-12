/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tspvisualizationfx;

import java.util.function.Predicate;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author mhrimaz
 */
public class TSPVisualizationFX extends Application {
    
    Tour bestTour;
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane mainRoot = new BorderPane();
        Pane root = new Pane();
        Label label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(0, 100, 15, 100));
        mainRoot.setCenter(root);
        mainRoot.setBottom(label);
        
        Scene scene = new Scene(mainRoot, 600, 600);
        scene.setOnMouseClicked(event -> {
            Circle circle = new Circle(event.getSceneX(), event.getSceneY(), 10);
            CityManager.getInstance().addCity(new City(event.getSceneX(), event.getSceneY()));
            root.getChildren().add(circle);
            bestTour = null;
            label.setText("Total City: " + CityManager.getInstance().numberOfCities());
        });
        scene.setOnKeyTyped(event -> {
            // Initialize population

            Population pop = new Population(50, true);
            System.out.println("Initial distance: " + pop.getFittest().getDistance());

            // Evolve population for 100 generations
            pop = GA.evolvePopulation(pop);
            for (int i = 0; i < 100; i++) {
                pop = GA.evolvePopulation(pop);
            }
            
            Tour tour = pop.getFittest();
            if (tour.compareTo(bestTour) < 0) {
                bestTour = tour;
                label.setText("Total City: " + CityManager.getInstance().numberOfCities()+ " | Current Best Distance: " + pop.getFittest().getDistance());
                root.getChildren().removeIf((Node t) -> {
                    return t.getClass().getSimpleName().equals("Line");
                });
                for (int i = 0; i < tour.tourSize() - 1; i++) {
                    Line line = new Line(tour.getCity(i).getLocation().getX(), tour.getCity(i).getLocation().getY(),
                            tour.getCity(i + 1).getLocation().getX(), tour.getCity(i + 1).getLocation().getY());
                    line.setStroke(Color.GREEN);
                    line.setStrokeWidth(5);
                    
                    root.getChildren().add(line);
                    line.toBack();
                }
            }
        });
        primaryStage.setTitle("TSP Genetic");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
