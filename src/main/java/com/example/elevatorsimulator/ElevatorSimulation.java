package com.example.elevatorsimulator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleMapProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


// ElevatorSimulation class
public class ElevatorSimulation {

    @FXML
    private GridPane grid;
    @FXML
    private Arc STDelevator;
    @FXML
    private Circle EXPelevator;

    private final ElevatorUpdateListener updateListener;
    private final ElevatorUpdateListener123 updateListener123;
    // Constructor
    public ElevatorSimulation(List<Elevator> numElevators, int numPassengers, int numIterations, ElevatorUpdateListener listener, ElevatorUpdateListener123 listener123) {
        // Existing initialization code...
        this.updateListener = listener;
        this.updateListener123 = listener123;
    }

    private List<Elevator> elevators;   //Standard Elevator
    private List<Elevator> elevators123;  // Express Elevator
    private List<Passanger> passengers;
    protected int numIterations;

    public ElevatorSimulation(List<Elevator> numElevators,List<Elevator> numElevatorsE, int numPassengers, int numIterations, ElevatorUpdateListener listener,ElevatorUpdateListener123 listener123, GridPane grid) {
        this.elevators = new ArrayList<>();
        this.elevators123 = new ArrayList<>();
        this.updateListener = listener;
        this.updateListener123 = listener123;
        if (grid == null ) {
            throw new IllegalArgumentException("GridPane cannot be null");
        }
        this.grid = grid;
        for (int i = 0; i < 1; i++) {
            elevators.add(new StandardElevator(10)); // Standard Elevator max capacity 10
            elevators123.add(new ExpressElevator(10));  // Express Elevator max capacity 8
        }




        // Initialize the Staff && passanger ratio
        this.passengers = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numPassengers; i++) {
            int startingFloor = rand.nextInt(10) + 1; // Assuming 10 floors
            int destinationFloor = rand.nextInt(10) + 1;
            boolean isStaff = rand.nextDouble() < 0.5; // 50% chance of being a staff member
            if (isStaff) {
                passengers.add(new StaffPassenger(startingFloor, destinationFloor));
            } else {
                passengers.add(new PatientPassanger(startingFloor, destinationFloor));
            }
        }
        this.numIterations = numIterations;
    }

    public void runSimulation() {
        // Create a Timeline to schedule GUI updates
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
      //  for (int i = 0; i < numIterations; i++) {

            // Move Standard elevators
            for (Elevator elevator : elevators) {
                elevator.move();
                elevator.canMove();
            }
            // Notify GUI to update elevator position
            updateListener.updateElevatorPosition(elevators.get(0).getCurrentFloor()); // Assuming there's only one Standerd elevator
            // Move the STDelevator GUI representation
            moveSTDelevatorGUI();
            // Load and unload passengers Standerd Elevator
            for (Elevator elevator : elevators) {
                List<Passanger> passengersToRemove = new ArrayList<>();
                for (Passanger passenger : elevator.passengers) {
                    // Logic - remove & add passangers at stop
                    if (passenger.destinationFloor == elevator.currentFloor) {
                        passengersToRemove.add(passenger);
                    }
                }     // Remove Passangers
                for (Passanger passenger : passengersToRemove) {
                    elevator.removePassenger(passenger);
                }
                // ADD Passangers && pass < MaxCapacity
                for (Passanger passenger : passengers) {
                    if (passenger.startingFloor == elevator.currentFloor && elevator.canAccommodatePassenger()) {
                        elevator.addPassenger(passenger);
                    }
                }
            }
            // Express Elevator
            for (int j = 0; j < 1; j++) {
                // Move elevators
                for (Elevator Eelevator : elevators123) {
                    Eelevator.canMove();
                    Eelevator.moveEXP();
                }
                // Notify GUI to update elevator position
                updateListener123.updateEElevatorPosition(elevators.get(0).getCurrentFloor()); // Assuming there's only one EXPRESS elevator
                moveEXPelevatorGUI();
                // Load and unload passengers
                for (Elevator Eelevator : elevators123) {
                    List<Passanger> passengersToRemove = new ArrayList<>();
                    for (Passanger passenger : Eelevator.passengers) {
                        // Logic - remove & add passangers at stop
                        if (passenger.destinationFloor == Eelevator.currentFloor) {
                            passengersToRemove.add(passenger);
                        }
                    }     // Remove Passangers
                    for (Passanger passenger : passengersToRemove) {
                        Eelevator.removePassenger(passenger);
                    }
                    // ADD Passangers && pass < MaxCapacity
                    for (Passanger passenger : passengers) {
                        if (passenger.startingFloor == Eelevator.currentFloor && Eelevator.canAccommodatePassenger()) {
                            Eelevator.addPassenger(passenger);
                        }
                    }
                }

                // Display elevator status
                System.out.println();
                System.out.println("New Iteration \n")    ;
                for (Elevator elevator : elevators) {
                    System.out.println("Standard Elevator at floor " + elevator.currentFloor + " with " + elevator.countPatients() + " patients and " + elevator.countStaff() + " staff members");
                }
                for (Elevator Eelevator : elevators123) {
                    System.out.println("Express Elevator at floor " + Eelevator.currentFloor + " with " + Eelevator.countPatients() + " patients and " + Eelevator.countStaff() + " staff members");
                }
                System.out.println();

            }
            }));

    // Set the cycle count to the number of iterations
    timeline.setCycleCount(numIterations);
    // Start the timeline
    timeline.play();
        }


    protected void moveSTDelevatorGUI() {
        //int currentRow = currentFloor - 1; // Adjust for zero-based indexing
        // Check the current floor of the elevator and move the STDelevator GUI representation accordingly
        Platform.runLater(() -> {
            if (grid != null && STDelevator != null) { // Check if grid and STDelevator are not null
                grid.getChildren().remove(STDelevator); // Remove STDelevator from its current position
                int currentRow = elevators.get(0).getCurrentFloor(); // Assuming there's only one elevator
                GridPane.setColumnIndex(STDelevator, 1); // Set STDelevator to the first column
                GridPane.setRowIndex(STDelevator, currentRow); // Set STDelevator to the updated row
                grid.getChildren().add(STDelevator); // Add STDelevator to the updated position in the GridPane
            }

        });

    }

    protected void moveEXPelevatorGUI() {
        // Check the current floor of the elevator and move the EXPelevator GUI representation accordingly
        Platform.runLater(() -> {
            if (grid != null && EXPelevator != null) { // Check if grid and EXPelevator are not null
                grid.getChildren().remove(EXPelevator); // Remove EXPelevator from its current position
                //int currentFloor = elevators123.get(0).getCurrentFloor(); // Assuming there's only one elevator
                int currentRow = elevators123.get(0).getCurrentFloor(); // Assuming there's only one elevator
                //int currentRow = currentFloor - 1; // Adjust for zero-based indexing
                GridPane.setColumnIndex(EXPelevator, 3); // Set EXPelevator to the first column
                GridPane.setRowIndex(EXPelevator, currentRow); // Set EXPelevator to the updated row
                grid.getChildren().add(EXPelevator); // Add EXPelevator to the updated position in the GridPane
            }

        });

    }


    public void moveARCDown(ActionEvent event) {
    }

    public void moveARCup(ActionEvent event) {
    }

    public void moveCircleup(ActionEvent event) {
    }

    public void moveCircleDown(ActionEvent event) {
    }
}



