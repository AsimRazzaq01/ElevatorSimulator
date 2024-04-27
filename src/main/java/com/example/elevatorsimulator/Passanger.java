package com.example.elevatorsimulator;

public abstract class Passanger {
    int startingFloor;
    int destinationFloor;
    Passanger(int startingFloor, int destinationFloor) {
        this.startingFloor = startingFloor;
        this.destinationFloor = destinationFloor;

    }
    //Method to confirm not a Staff member = Passanger
    abstract boolean isStaff();
    //Method to give Staff member express Elevator privlages
    abstract boolean prefersExpressElevator();
}

