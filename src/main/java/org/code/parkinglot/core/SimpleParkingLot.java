package org.code.parkinglot.core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple parking lot, where a vehicle can park and leave.
 * It uses a very simple linked list to keep track of empty parking-slots.
 *
 * Created by hmistry on 04/09/17.
 */
public class SimpleParkingLot implements ParkingLot {
    private int firstEmptySlot;
    private int highWaterMark;
    private int capacity;

    private ArrayList<Vehicle> arrSlots;
    private ArrayList<Integer> arrEmptySlots;

    private HashMap<String, ArrayList<String>> mapColor2Numbers;
    private HashMap<String, ArrayList<Integer>> mapColor2Slots;
    private HashMap<String, Integer> mapRegNumber2Slot;

    public SimpleParkingLot(int size) {
        create(size);
    }

    public int create(int size) {
        boolean flagError = false;
        try {
            firstEmptySlot = 1;
            highWaterMark = 1;
            capacity = size;

            arrSlots = new ArrayList<Vehicle>(size + 1);
            arrSlots.add(null);
            arrEmptySlots = new ArrayList<Integer>(size);

            mapColor2Numbers = new HashMap<String, ArrayList<String>>();
            mapColor2Slots = new HashMap<String, ArrayList<Integer>>();
            mapRegNumber2Slot = new HashMap<String, Integer>();
        } catch (Exception e) {
            flagError = true;
        }

        if (flagError) {
            return -1;
        }
        else {
            return size;
        }
    }

    /**
     * Method to park the given vehicle in the first nearest available slot.
     *
     * @param vehicle The vehicle to be parked
     * @return slot number, where the vehicle was parked
     */
    public int park(Vehicle vehicle) {
        if (firstEmptySlot > capacity || vehicle == null) {
            return -1;
        }

        int allottedSlot = firstEmptySlot;
        if (firstEmptySlot == arrSlots.size()) {
            // Need to append
            arrSlots.add(firstEmptySlot, vehicle);

            firstEmptySlot += 1;
            highWaterMark = firstEmptySlot;
            assert(firstEmptySlot <= capacity+1);
        }
        else if (firstEmptySlot < arrSlots.size()) {
            // Need to park in recently emptied slot
            assert(arrSlots.get(firstEmptySlot) == null);
            arrSlots.set(firstEmptySlot, vehicle);

            // Book keeping for available empty slots
            arrEmptySlots.remove(0);
            if (arrEmptySlots.size() > 0) {
                firstEmptySlot = arrEmptySlots.get(0);
            }
            else {
                firstEmptySlot = highWaterMark;
            }
        }

        // Update the dictionaries/maps
        ArrayList<String> arrRegNumbers = mapColor2Numbers.get(vehicle.getColor());
        if (arrRegNumbers == null) {
            arrRegNumbers = new ArrayList<String>();
        }
        arrRegNumbers.add(vehicle.getRegNumber());
        mapColor2Numbers.put(vehicle.getColor(), arrRegNumbers);

        ArrayList<Integer> arrSlotsForColor = mapColor2Slots.get(vehicle.getColor());
        if (arrSlotsForColor == null) {
            arrSlotsForColor = new ArrayList<Integer>();
        }
        arrSlotsForColor.add(allottedSlot);
        mapColor2Slots.put(vehicle.getColor(), arrSlotsForColor);

        Integer slot = mapRegNumber2Slot.get(vehicle.getRegNumber());
        mapRegNumber2Slot.put(vehicle.getRegNumber(), allottedSlot);

        return allottedSlot;
    }

    /**
     * Method for a vehicle to leave the parking slot.
     *
     * @param slot The slot, from where a vehicle will leave
     * @return The vehicle that just left the parking slot
     */
    public Vehicle leave(int slot) {
        if (slot <= 0 || slot >= arrSlots.size()) {
            return null;
        }

        // Just mark the vehicle to be null
        Vehicle vehicle = arrSlots.get(slot);
        assert(vehicle != null);
        arrSlots.set(slot, null);

        // Book keeping for available empty slots ( sorted linked list )
        boolean flagInserted = false;
        for (int i=0; i < arrEmptySlots.size(); i++) {
            int s = arrEmptySlots.get(i);
            if (slot < s) {
                arrEmptySlots.add(i, slot);
                flagInserted = true;
                break;
            }
        }
        if (!flagInserted) {
            arrEmptySlots.add(slot);
        }
        assert(arrEmptySlots.size() <= capacity);

        // Reset the value of first empty slot
        firstEmptySlot = arrEmptySlots.get(0);

        // Update the dictionaries/maps
        ArrayList<String> arrRegNumbers = mapColor2Numbers.get(vehicle.getColor());
        arrRegNumbers.remove(vehicle.getRegNumber());
        mapColor2Numbers.put(vehicle.getColor(), arrRegNumbers);

        ArrayList<Integer> arrSlotsForColor = mapColor2Slots.get(vehicle.getColor());
        arrSlotsForColor.remove(new Integer(slot));
        mapColor2Slots.put(vehicle.getColor(), arrSlotsForColor);

        mapRegNumber2Slot.remove(vehicle.getRegNumber());

        return vehicle;
    }

    public String status() {
        String header = "Slot No.\tRegistration No.\tColour\n";
        StringBuffer sb = new StringBuffer(header);
        for (int i=0; i < arrSlots.size(); i++) {
            Vehicle car = arrSlots.get(i);
            if (car != null) {
                sb.append(i)
                  .append("\t")
                  .append(car.getRegNumber())
                  .append("\t")
                  .append(car.getColor())
                  .append("\n");
            }
        }

        return sb.toString();
    }

    public ArrayList<String> findRegNumbersWithColor(String color) {
        return mapColor2Numbers.get(color);
    }

    public ArrayList<Integer> findSlotsWithColor(String color) {
        return mapColor2Slots.get(color);
    }

    public int findSlotWithRegNumber(String regNumber) {
        Integer slot = mapRegNumber2Slot.get(regNumber);
        if (slot == null) {
            return -1;
        }
        else {
            return slot;
        }
    }
}
