package org.code.parkinglot.core;

import java.util.ArrayList;

/**
 * An interface for parking lot, where a vehicle can park and leave.
 *
 * Created by hmistry on 04/09/17.
 */
public interface ParkingLot {
    int create(int size);

    int park(Vehicle vehicle);

    Vehicle leave(int slot);

    String status();

    ArrayList<String> findRegNumbersWithColor(String color);

    ArrayList<Integer> findSlotsWithColor(String color);

    int findSlotWithRegNumber(String regNumber);
}
