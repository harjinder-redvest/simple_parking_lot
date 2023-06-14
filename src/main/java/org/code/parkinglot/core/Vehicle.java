package org.code.parkinglot.core;

/**
 * An interface for simple vehicles that should have two properties: color and registration number.
 *
 * Created by hmistry on 04/09/17.
 */
public interface Vehicle {
    String getColor();
    void setColor(String color);

    String getRegNumber();
    void setRegNumber();
}
