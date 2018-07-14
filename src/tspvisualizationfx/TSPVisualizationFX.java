/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tspvisualizationfx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import solvers.TSPSolver;
import solvers.genetic.GASolver;
import solvers.genetic.GeneticAlgorithmEngine;
import solvers.genetic.Population;
import solvers.model.City;
import solvers.model.CityManager;
import solvers.model.Tour;
import solvers.pso.Swarm;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mhrimaz
 */
public class TSPVisualizationFX extends Application {

    Tour bestTour;

    @Override
    public void start(Stage primaryStage) {
        BorderPane mainRoot = new BorderPane();
        mainRoot.setPadding(new Insets(14));
        Pane root = new Pane();
        root.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        VBox buttonBox = new VBox(20);
        buttonBox.setPadding(new Insets(20));
        Button fileButton = new Button("Load From File");
        buttonBox.getChildren().add(fileButton);
        Label label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(0, 100, 15, 100));
        mainRoot.setCenter(root);
        mainRoot.setBottom(label);
        mainRoot.setRight(buttonBox);

        Scene scene = new Scene(mainRoot, 800, 600);



        fileButton.setOnAction(e->{
            root.requestFocus();
            CityManager.getInstance().clear();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(Paths.get("").toAbsolutePath().toString()));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );
            File file = fileChooser.showOpenDialog(primaryStage);

            if(file!=null) {
                root.getChildren().clear();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))){
                    List<City> collect = reader.lines()
                            .map(line -> {
                                String[] split = line.split(" ");
                                return new City(Double.parseDouble(split[0])*5, Double.parseDouble(split[1])*5);
                            })
                            .collect(Collectors.toList());
                    collect.forEach(city -> {
                        CityManager.getInstance().addCity(city);
                        Circle circle = new Circle(city.getLocation().getX(), city.getLocation().getY(), 10);
                        root.getChildren().add(circle);
                        bestTour = null;
                        label.setText("Total City: " + CityManager.getInstance().numberOfCities()+" | Ctrl+Space: GA, Space: PSO");
                    });
                }catch (FileNotFoundException ex){

                }catch (IOException ex){

                }
            }
        });

        root.setOnMouseClicked(event -> {
            root.requestFocus();
            Circle circle = new Circle(event.getX(), event.getY(), 10);
            CityManager.getInstance().addCity(new City(event.getX(), event.getY()));
            root.getChildren().add(circle);
            bestTour = null;
            label.setText("Total City: " + CityManager.getInstance().numberOfCities()+" | Ctrl+Space: GA, Space: PSO");
        });
        scene.setOnKeyTyped(event -> {
            TSPSolver solver = null;
            if(event.isControlDown()){
                if(solver!=null && !solver.getClass().equals(GASolver.class)){
                    bestTour=null;
                }
                solver = new GASolver();
            }else{
                if(solver!=null && !solver.getClass().equals(Swarm.class)){
                    bestTour=null;
                }
                solver = new Swarm(10,10000,CityManager.getInstance().numberOfCities()*100);
            }
            Tour tour = solver.solve(CityManager.getInstance(), (solution) -> {

            });
            System.out.println("tour = " + tour);
            if(bestTour ==null){
                bestTour = tour;
            }else if(bestTour.getDistance()>tour.getDistance()){
                bestTour = tour;
            }
            label.setText("Total City: " + CityManager.getInstance().numberOfCities()+ " | Current Best Distance: " + bestTour.getDistance());
            root.getChildren().removeIf((Node t) -> t.getClass().getSimpleName().equals("Line"));
            for (int i = 0; i < bestTour.tourSize(); i++) {
                Line line = new Line(bestTour.getCity(i).getLocation().getX(), bestTour.getCity(i).getLocation().getY(),
                        bestTour.getCity((i + 1)%bestTour.tourSize()).getLocation().getX(),
                        bestTour.getCity((i + 1)%bestTour.tourSize()).getLocation().getY());
                line.setStroke(Color.GREEN);
                line.setStrokeWidth(5);

                root.getChildren().add(line);
                line.toBack();
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
