package org.code.parkinglot.core;

/**
 * A simple car object is a vehicle that has two properties: color and registration number.
 *
 * Created by hmistry on 04/09/17.
 */
public class Car implements Vehicle {
    private String color;
    private String regNumber;

    public Car(String color, String regNumber) {
        this.color = color;
        this.regNumber = regNumber;
    }

    public String getColor() {
        return this.color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getRegNumber() {
        return this.regNumber;
    }
    public void setRegNumber() {
        this.regNumber = regNumber;
    }
}
