package com.example.elevatorsimulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Elevator {
    protected int currentFloor;
    protected List<Passanger> passengers;
    protected int maxCapacity;

    protected int getCurrentFloor() {
        return currentFloor;
    }

    Elevator(int maxCapacity) {
        this.currentFloor = 1;
        this.passengers = new ArrayList<>();
        this.maxCapacity = maxCapacity;
    }
    public abstract void canMove();
    // Accomadation for Elevator capacity
     boolean canAccommodatePassenger() {
        return passengers.size() < maxCapacity;
     }

    // Method to move the Standard elevator
    public int move() {
        Random rand = new Random();
        // Randomly decide whether to move up or down
        boolean moveUp = rand.nextBoolean();
        // If moveUp is true, move up; otherwise, move down
        if (moveUp) {
            currentFloor = Math.min(currentFloor + 1, getMaxFloor());
        } else {
            currentFloor = Math.max(currentFloor - 1,1);
        }
        return 0;
    }

    // Method to move the Express elevator
    public int moveEXP() {
        Random rand = new Random();
        // Randomly decide whether to move up or down
        boolean moveUp = rand.nextBoolean();
        // If moveUp is true, move up; otherwise, move down
        if (moveUp) {
            currentFloor = Math.min(currentFloor + 3, getMaxFloor());
        } else {
            currentFloor= Math.max(currentFloor - 3, 1);
        }
        return 0;
    }

    // Abstract method to get the maximum floor the elevator can go to (to be implemented in subclasses)
    protected abstract int getMaxFloor();


    // method to add passangers staff/patient
    int addPassenger(Passanger passenger) {
        passengers.add(passenger);
        System.out.println("A" + (passenger.isStaff() ? "Staff" : "Patient") + " boarded the elevator at floor " + currentFloor);
        return currentFloor = currentFloor + 1 ;
    }


    // method to remove passangers staff/patient
    void removePassenger(Passanger passenger) {
        passengers.remove(passenger);
        System.out.println("A " + (passenger.isStaff() ? "Staff" : "Patient") + " left the elevator at floor " + currentFloor);
    }
    // method to count Patients
    int countPatients() {
        int count = 0;
        for (Passanger passenger : passengers) {
            if (!passenger.isStaff()) {
                count++;
            }
        }
        return count;
    }
    // method to count staff
    int countStaff() {
        int count = 0;
        for (Passanger passenger : passengers) {
            if (passenger.isStaff()) {
                count++;
            }
        }
        return count;
    }

}


// Run simulation with 2 elevators, 10 passengers, and 5 iterations
//   ElevatorSimulation simulation = new ElevatorSimulation(1, 100, 10);
//    simulation.runSimulation();