package com.example.elevatorsimulator;

public class PatientPassanger extends Passanger {

    private int age;
    private String PatientID;

    public PatientPassanger(int startingFloor, int destinationFloor) {
        super(startingFloor, destinationFloor);
    }

    @Override
    boolean prefersExpressElevator() {
        return false;
    }
    boolean isStaff() {
        return false;
    }
}
