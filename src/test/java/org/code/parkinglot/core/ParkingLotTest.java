package org.code.parkinglot.core;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;

/**
 * Unit test for parking lot core.
 */
public class ParkingLotTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ParkingLotTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ParkingLotTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testCreate()
    {
        ParkingLot pl = new SimpleParkingLot(5);
        assertTrue( pl != null );
    }

    public void testPark() {
        ParkingLot pl = new SimpleParkingLot(3);
        assertTrue( pl != null );

        int slot = pl.park(new Car("White", "KA-01-MP-1111"));
        assertEquals(1, slot);

        slot = pl.park(new Car("White", "KA-01-MP-1112"));
        assertEquals(2, slot);

        slot = pl.park(new Car("White", "KA-01-MP-1113"));
        assertEquals(3, slot);
    }

    public void testStatus() {
        ParkingLot pl = new SimpleParkingLot(3);
        assertTrue( pl != null );

        pl.park(new Car("White", "KA-01-MP-1111"));
        pl.park(new Car("White", "KA-01-MP-1112"));
        pl.park(new Car("White", "KA-01-MP-1113"));

        String status = pl.status();

        String header = "Slot No.\tRegistration No.\tColour\n";
        StringBuffer sb = new StringBuffer(header);
        sb.append(1)
          .append("\t")
          .append("KA-01-MP-1111")
          .append("\t")
          .append("White")
          .append("\n");
        sb.append(2)
          .append("\t")
          .append("KA-01-MP-1112")
          .append("\t")
          .append("White")
          .append("\n");
        sb.append(3)
          .append("\t")
          .append("KA-01-MP-1113")
          .append("\t")
          .append("White")
          .append("\n");

        assertEquals(sb.toString(), status);
    }

    public void testFind() {
        ParkingLot pl = new SimpleParkingLot(5);
        assertTrue( pl != null );

        pl.park(new Car("White", "KA-01-MP-1111"));
        pl.park(new Car("Blue", "KA-01-MP-1112"));
        pl.park(new Car("Red", "KA-01-MP-1113"));
        pl.park(new Car("Blue", "KA-01-MP-1114"));
        pl.park(new Car("Red", "KA-01-MP-1115"));

        ArrayList<String> arrRegNumbers = pl.findRegNumbersWithColor("Blue");
        assertEquals(2, arrRegNumbers.size());
        assertEquals("KA-01-MP-1112", arrRegNumbers.get(0));
        assertEquals("KA-01-MP-1114", arrRegNumbers.get(1));

        arrRegNumbers = pl.findRegNumbersWithColor("Purple");
        assertEquals(null, arrRegNumbers);

        ArrayList<Integer> arrSlots = pl.findSlotsWithColor("Red");
        assertEquals(2, arrSlots.size());
        assertEquals(new Integer(3), arrSlots.get(0));
        assertEquals(new Integer(5), arrSlots.get(1));

        arrSlots = pl.findSlotsWithColor("Purple");
        assertEquals(null, arrSlots);

        int slot = pl.findSlotWithRegNumber("KA-01-MP-1111");
        assertEquals(1, slot);

        slot = pl.findSlotWithRegNumber("KA-01-MP-9999");
        assertEquals(-1, slot);

        pl.leave(3);
        pl.leave(4);

        arrRegNumbers = pl.findRegNumbersWithColor("Blue");
        assertEquals(1, arrRegNumbers.size());
        assertEquals("KA-01-MP-1112", arrRegNumbers.get(0));

        arrSlots = pl.findSlotsWithColor("Red");
        assertEquals(1, arrSlots.size());
        assertEquals(new Integer(5), arrSlots.get(0));
    }

    public void testOverPark() {
        ParkingLot pl = new SimpleParkingLot(3);
        assertTrue( pl != null );

        pl.park(new Car("White", "KA-01-MP-1111"));
        pl.park(new Car("White", "KA-01-MP-1112"));
        pl.park(new Car("White", "KA-01-MP-1113"));

        int slot = pl.park(new Car("Black", "KA-01-MP-1114"));
        assertEquals(-1, slot);
    }

    public void testLeaveBeforeParking() {
        ParkingLot pl = new SimpleParkingLot(3);
        assertTrue( pl != null );

        Vehicle car = pl.leave(1);
        assertEquals(null, car);
    }

    public void testLeave() {
        ParkingLot pl = new SimpleParkingLot(3);
        assertTrue( pl != null );

        pl.park(new Car("White", "KA-01-MP-1111"));
        pl.park(new Car("Blue", "KA-01-MP-1112"));
        pl.park(new Car("Red", "KA-01-MP-1113"));

        Vehicle car = pl.leave(1);
        assertEquals("White", car.getColor());
        assertEquals("KA-01-MP-1111", car.getRegNumber());

        car = pl.leave(2);
        assertEquals("Blue", car.getColor());
        assertEquals("KA-01-MP-1112", car.getRegNumber());

        car = pl.leave(3);
        assertEquals("Red", car.getColor());
        assertEquals("KA-01-MP-1113", car.getRegNumber());
    }

    public void testLeaveReverse() {
        ParkingLot pl = new SimpleParkingLot(3);
        assertTrue( pl != null );

        pl.park(new Car("White", "KA-01-MP-1111"));
        pl.park(new Car("Blue", "KA-01-MP-1112"));
        pl.park(new Car("Red", "KA-01-MP-1113"));

        Vehicle car = pl.leave(3);
        assertEquals("Red", car.getColor());
        assertEquals("KA-01-MP-1113", car.getRegNumber());

        car = pl.leave(2);
        assertEquals("Blue", car.getColor());
        assertEquals("KA-01-MP-1112", car.getRegNumber());

        car = pl.leave(1);
        assertEquals("White", car.getColor());
        assertEquals("KA-01-MP-1111", car.getRegNumber());
    }

    public void testParkAndLeave() {
        ParkingLot pl = new SimpleParkingLot(3);
        assertTrue( pl != null );

        pl.park(new Car("White", "KA-01-MP-1111"));
        pl.park(new Car("Blue", "KA-01-MP-1112"));

        Vehicle car = pl.leave(2);
        assertEquals("Blue", car.getColor());
        assertEquals("KA-01-MP-1112", car.getRegNumber());

        int slot = pl.park(new Car("Red", "KA-01-MP-1113"));
        assertEquals(2, slot);

        car = pl.leave(2);
        assertEquals("Red", car.getColor());
        assertEquals("KA-01-MP-1113", car.getRegNumber());
    }

    public void testParkLeaveFullParkAgain() {
        ParkingLot pl = new SimpleParkingLot(3);
        assertTrue( pl != null );

        pl.park(new Car("White", "KA-01-MP-1111"));
        pl.park(new Car("Blue", "KA-01-MP-1112"));

        Vehicle car = pl.leave(1);
        assertEquals("White", car.getColor());
        assertEquals("KA-01-MP-1111", car.getRegNumber());

        car = pl.leave(2);
        assertEquals("Blue", car.getColor());
        assertEquals("KA-01-MP-1112", car.getRegNumber());

        int slot = pl.park(new Car("White", "KA-01-MP-1111"));
        assertEquals(1, slot);

        slot = pl.park(new Car("Blue", "KA-01-MP-1112"));
        assertEquals(2, slot);

        slot = pl.park(new Car("Red", "KA-01-MP-1113"));
        assertEquals(3, slot);
    }

    public void testParkLeaveParkAgain() {
        ParkingLot pl = new SimpleParkingLot(6);
        assertTrue( pl != null );

        pl.park(new Car("White", "KA-01-HH-1234"));
        pl.park(new Car("White", "KA-01-HH-9999"));
        pl.park(new Car("Black", "KA-01-BB-0001"));
        pl.park(new Car("Red", "KA-01-HH-7777"));
        pl.park(new Car("Blue", "KA-01-HH-2701"));
        pl.park(new Car("Black", "KA-01-HH-3141"));

        pl.leave(4);
        pl.park(new Car("White", "KA-01-P-333"));
        pl.park(new Car("White", "DL-12-AA-9999"));
    }
}
