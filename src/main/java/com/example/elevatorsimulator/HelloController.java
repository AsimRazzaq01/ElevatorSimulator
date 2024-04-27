package com.example.elevatorsimulator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    private Arc STDelevator;
    @FXML
    private Circle EXPelevator;
    @FXML
    private GridPane grid;

    // Reference to ElevatorSimulation
    private ElevatorSimulation elevatorSimulation;


    private List<Elevator> elevators;
    private List<Elevator> elevators123;
    private int currentRow = 1; // Track the current row of the elevators


    @FXML
    public void initialize() {
        // Initialize any necessary setup here
        // Initialize elevators
        elevators = new ArrayList<>();
        elevators123 = new ArrayList<>();
        elevators.add(new StandardElevator(10)); // Standard Elevator
        elevators123.add(new ExpressElevator(10));   // Express Elevator
    }


    @FXML
    public void StartSimulation(ActionEvent event) {
        // Implement logic to start the simulation when the button is clicked
        ElevatorSimulation simulation = new ElevatorSimulation(elevators,elevators123, 50, 10,this::updateElevatorPosition,this::updateEElevatorPosition, grid); // 10 passengers, 5 iterations
        simulation.runSimulation();
    }


    public void moveSTDelevator() {
        for (Elevator elevator : elevators) {
            elevator.canMove();
            elevator.move(); // Move elevator
            if (elevator.getCurrentFloor() == currentRow) {
                updateElevatorPosition(currentRow);
            }
        }
    }

    public void moveEXPelevator() {
        for (Elevator Eelevator : elevators123) {
            Eelevator.canMove();
            Eelevator.moveEXP(); // Move elevator
            if (Eelevator.getCurrentFloor() == currentRow) {
                updateEElevatorPosition(currentRow);
            }
        }
    }

    public void moveARCup(ActionEvent event) {
        currentRow++;
        moveSTDelevator();
    }

    public void moveARCDown(ActionEvent event) {
        currentRow--;
        moveSTDelevator();

    }

    public void moveCircleup(ActionEvent event) {
        currentRow++;
        moveEXPelevator();
    }

    public void moveCircleDown(ActionEvent event) {
        currentRow--;
        moveEXPelevator();
    }

    // Event handler for moving the standard elevator up
    @FXML
    public void moveSTDelevatorUp(ActionEvent event) {
        elevatorSimulation.moveARCup(event);
    }

    // Event handler for moving the standard elevator down
    @FXML
    public void moveSTDelevatorDown(ActionEvent event) {
        elevatorSimulation.moveARCDown(event);
    }

    // Event handler for moving the express elevator up
    @FXML
    public void moveEXPelevatorUp(ActionEvent event) {
        elevatorSimulation.moveCircleup(event);
    }

    // Event handler for moving the express elevator down
    @FXML
    public void moveEXPelevatorDown(ActionEvent event) {
        elevatorSimulation.moveCircleDown(event);
    }

    // Set the ElevatorSimulation reference
    public void setElevatorSimulation(ElevatorSimulation elevatorSimulation) {
        this.elevatorSimulation = elevatorSimulation;
    }

    public void updateElevatorPosition(int currentRow) {
        Platform.runLater(() -> {
            grid.getChildren().remove(STDelevator); // Remove STDelevator from its current position
            GridPane.setColumnIndex(STDelevator, 1); // Set STDelevator to the first column
            GridPane.setRowIndex(STDelevator, currentRow); // Set STDelevator to the updated row
            grid.getChildren().add(STDelevator); // Add STDelevator to the updated position in the GridPane
        });
    }

    public void updateEElevatorPosition(int currentRow) {
        Platform.runLater(() -> {
            grid.getChildren().remove(EXPelevator); // Remove EXPelevator from its current position
            GridPane.setColumnIndex(EXPelevator, 3); // Set EXPelevator to the first column
            GridPane.setRowIndex(EXPelevator, currentRow); // Set EXPelevator to the updated row
            grid.getChildren().add(EXPelevator); // Add EXPelevator to the updated position in the GridPane
        });
    }




}















