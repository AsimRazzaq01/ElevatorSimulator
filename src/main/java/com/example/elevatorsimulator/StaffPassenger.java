package com.example.elevatorsimulator;

public class StaffPassenger extends Passanger {
    private String StaffName;
    private int StaffAge;
    private String StaffID;
    StaffPassenger(int startingFloor, int destinationFloor) {
        super(startingFloor, destinationFloor);
    }
    //Method to confirm Staff member
    boolean isStaff() {
        return true;
    }

    //Method to give Staff member express Elevator privlages
    @Override
    boolean prefersExpressElevator() {
        return true;
    }



}