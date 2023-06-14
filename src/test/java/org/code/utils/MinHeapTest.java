package org.code.utils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for MinHeap.
 */
public class MinHeapTest
    extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MinHeapTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MinHeapTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testMinHeap1() {
        MinHeap minHeap = new MinHeap(5);
        minHeap.insert(4);
        minHeap.minHeap();

        int minVal = minHeap.remove();
        assertEquals(4, minVal);

        minHeap.insert(25);
        minHeap.insert(20);
        minHeap.insert(15);

        minVal = minHeap.remove();
        assertEquals(15, minVal);

        minHeap.insert(40);
        minHeap.insert(11);
        minHeap.insert(45);

        minVal = minHeap.remove();
        assertEquals(11, minVal);
    }

    public void testMinHeap2() {
        MinHeap minHeap = new MinHeap(11);
        minHeap.insert(3);
        minHeap.insert(2);
        minHeap.insert(1);
        minHeap.insert(15);
        minHeap.insert(5);
        minHeap.insert(4);
        minHeap.insert(45);

        int minVal = minHeap.remove();
        assertEquals(1, minVal);

        minVal = minHeap.remove();
        assertEquals(2, minVal);

        minVal = minHeap.remove();
        assertEquals(3, minVal);

        minVal = minHeap.remove();
        assertEquals(4, minVal);

        minVal = minHeap.remove();
        assertEquals(5, minVal);

        minVal = minHeap.remove();
        assertEquals(15, minVal);

        minVal = minHeap.remove();
        assertEquals(45, minVal);
    }

    public void testMinHeap3() {
        MinHeap minHeap = new MinHeap(11);
        minHeap.insert(3);
        minHeap.insert(2);
        minHeap.insert(1);
        minHeap.insert(15);
        minHeap.insert(5);
        minHeap.insert(4);
        minHeap.insert(45);

        int minVal = minHeap.remove();
        assertEquals(1, minVal);

        minVal = minHeap.remove();
        assertEquals(2, minVal);

        minVal = minHeap.remove();
        assertEquals(3, minVal);

        minHeap.insert(25);
        minHeap.insert(20);
        minHeap.insert(30);

        minVal = minHeap.remove();
        assertEquals(4, minVal);

        minVal = minHeap.remove();
        assertEquals(5, minVal);

        minVal = minHeap.remove();
        assertEquals(15, minVal);

        minVal = minHeap.remove();
        assertEquals(20, minVal);

        minVal = minHeap.remove();
        assertEquals(25, minVal);

        minVal = minHeap.remove();
        assertEquals(30, minVal);

        minVal = minHeap.remove();
        assertEquals(45, minVal);
    }

}
