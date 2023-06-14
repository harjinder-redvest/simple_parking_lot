package org.code.parkinglot.cli;

import org.code.parkinglot.core.Car;
import org.code.parkinglot.core.ParkingLot;
import org.code.parkinglot.core.SimpleParkingLot;
import org.code.parkinglot.core.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Command Line Interface for Parking Lot.
 *
 * Created by hmistry on 05/09/17.
 */
public class ParkingLotCLI {
    private ParkingLot parkingLot = new SimpleParkingLot(3);

    public String runCommand(String command) {
        String[] arrTokens = command.split(" ");
        assert(arrTokens.length > 0);

        String firstToken = arrTokens[0];
        String output;
        if (firstToken.equals("create_parking_lot")) {
            assert(arrTokens.length <= 2);

            int requested_size = Integer.parseInt(arrTokens[1]);
            int actual_size = parkingLot.create(requested_size);
            if (actual_size == requested_size) {
                output = "Created a parking lot with " + actual_size + " slots";
            }
            else {
                output = "Could not create a parking lot";
            }
        }
        else if (firstToken.equals("park")) {
            assert(arrTokens.length <= 3);

            String color = arrTokens[2];
            String regNumber = arrTokens[1];

            Vehicle vehicle = new Car(color, regNumber);
            int slot = parkingLot.park(vehicle);
            if (slot == -1) {
                output = "Sorry, parking lot is full";
            }
            else {
                output = "Allocated slot number: " + slot;
            }
        }
        else if (firstToken.equals("leave")) {
            assert(arrTokens.length <= 2);
            int slot = Integer.parseInt(arrTokens[1]);
            Vehicle vehicle = parkingLot.leave(slot);
            if (vehicle != null) {
                output = "Slot number " + slot + " is free";
            }
            else {
                output = "Could not leave the given slot " + slot;
            }
        }
        else if (firstToken.equals("status")) {
            output = parkingLot.status();
        }
        else if (firstToken.equals("registration_numbers_for_cars_with_colour")) {
            assert(arrTokens.length <= 2);

            ArrayList<String> arrRegNumbers = parkingLot.findRegNumbersWithColor(arrTokens[1]);
            if (arrRegNumbers == null) {
                output = "Not found";
            }
            else {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < arrRegNumbers.size(); i++) {
                    sb.append(arrRegNumbers.get(i));
                    if (i < arrRegNumbers.size() - 1) {
                        sb.append(", ");
                    }
                }
                output = sb.toString();
            }
        }
        else if (firstToken.equals("slot_numbers_for_cars_with_colour")) {
            assert(arrTokens.length <= 2);

            ArrayList<Integer> arrSlots = parkingLot.findSlotsWithColor(arrTokens[1]);
            if (arrSlots == null) {
                output = "Not found";
            }
            else {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < arrSlots.size(); i++) {
                    sb.append(arrSlots.get(i));
                    if (i < arrSlots.size() - 1) {
                        sb.append(", ");
                    }
                }
                output = sb.toString();
            }
        }
        else if (firstToken.equals("slot_number_for_registration_number")) {
            assert(arrTokens.length <= 2);
            int slot = parkingLot.findSlotWithRegNumber(arrTokens[1]);

            if (slot == -1) {
                output = "Not found";
            }
            else {
                output = "" + slot;
            }
        }
        else {
            output = "Invalid command !\n" +
                    "Supported commands are: \n" +
                    "create_parking_lot\n" +
                    "park\n" +
                    "leave\n" +
                    "status\n" +
                    "registration_numbers_for_cars_with_colour\n" +
                    "slot_numbers_for_cars_with_colour\n" +
                    "slot_number_for_registration_number\n";
        }

        return output + "\n";
    }

    public static void main(String[] args) {
        ParkingLotCLI cli = new ParkingLotCLI();

        if (args.length == 0) {
            try {
                Scanner scanner = new Scanner(new InputStreamReader(System.in));
                while (true) {
                    System.out.println("Input: ");
                    String input = scanner.nextLine();
                    String output = cli.runCommand(input);
                    System.out.println("Output: ");
                    System.out.println(output);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (args.length == 1) {
            String arg = args[0];
            if (arg.equals("--help")) {
                String helpMessage =
                    "This program takes one argument <filename>, that is full name of file with commands.\n" +
                    "If no argument is given, then program runs in the interactive mode.\n" +
                    "Note: this program was built using Maven 3.3.9 with Oracle Java 1.8.0_112";
                System.out.println(helpMessage);
            }
            else {
                String filename = arg;
                try {
                    File file = new File(filename);
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(cli.runCommand(line));
                    }
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
