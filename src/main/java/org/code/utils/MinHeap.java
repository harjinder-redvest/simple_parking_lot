package org.code.utils;

/**
 * Min Heap data structure to quickly find min integer value in the given data set.
 *
 * Created by hmistry on 05/09/17.
 */
public class MinHeap {
    private int[] Heap;
    private int size;
    private int maxsize;

    private static final int FRONT = 1;

    public MinHeap(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;

        Heap = new int[this.maxsize + 1];
        Heap[0] = Integer.MIN_VALUE;
    }

    private int parent(int pos) {
        return pos/2;
    }

    private int leftChild(int pos) {
        return 2*pos;
    }

    private int rightChild(int pos) {
        return (2*pos) + 1;
    }

    private boolean isLeaf(int pos) {
        if (pos > size/2 && pos <= size) {
            return true;
        }
        else {
            return false;
        }
    }

    private void swap(int pos1, int pos2) {
        int tmp = Heap[pos1];
        Heap[pos1] = Heap[pos2];
        Heap[pos2] = tmp;
    }

    private void minHeapify(int pos) {
        if (isLeaf(pos)) {
            return;
        }

        if (Heap[pos] > Heap[leftChild(pos)] || Heap[pos] > Heap[rightChild(pos)]) {
            if (Heap[leftChild(pos)] < Heap[rightChild(pos)]) {
                swap(pos, leftChild(pos));
                minHeapify(leftChild(pos));
            }
            else {
                swap(pos, rightChild(pos));
                minHeapify(rightChild(pos));
            }
        }
    }

    public void insert(int element) {
        assert(size < this.maxsize);

        Heap[++size] = element;
        int current = size;

        while (Heap[current] < Heap[parent(current)]) {
            swap(current,parent(current));
            current = parent(current);
        }
    }

    public void minHeap() {
        for (int pos = (size / 2); pos >= 1 ; pos--) {
            minHeapify(pos);
        }
    }

    public int remove() {
        assert(size > 0);

        int popped = Heap[FRONT];
        Heap[FRONT] = Heap[size--];
        if (size > 1) {
            minHeapify(FRONT);
        }
        return popped;
    }
}
