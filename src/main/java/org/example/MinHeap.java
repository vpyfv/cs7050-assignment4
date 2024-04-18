package org.example;

import java.util.HashMap;
import java.util.Map;

public class MinHeap {
    private final float[] heap;
    private final int[] ids;
    private int size;
    private final int maxSize;
    private final Map<Integer, Integer> idToIndex;

    public MinHeap(int maxSize) {
        this.maxSize = maxSize;
        this.size = 0;
        heap = new float[maxSize + 1];
        ids = new int[maxSize + 1];
        idToIndex = new HashMap<>();
    }

    public void heap_ini(float[] keys, int n) {
        if (n > maxSize) return;
        this.size = n;
        for (int i = 1; i <= n; i++) {
            this.heap[i] = keys[i - 1];
            this.ids[i] = i;
            this.idToIndex.put(i, i);
        }

        for (int i = size / 2; i > 0; i--) {
            minHeapify(i);
        }
    }

    private int parent(int pos) { return pos / 2; }
    private int leftChild(int pos) { return (2 * pos); }
    private int rightChild(int pos) { return (2 * pos) + 1; }
    private boolean isLeaf(int pos) { return pos > size / 2 && pos <= size; }

    private void swap(int fpos, int spos) {
        float tmp;
        tmp = heap[fpos];
        heap[fpos] = heap[spos];
        heap[spos] = tmp;

        int tmpId = ids[fpos];
        ids[fpos] = ids[spos];
        ids[spos] = tmpId;

        idToIndex.put(ids[fpos], fpos);
        idToIndex.put(ids[spos], spos);
    }

    private void minHeapify(int pos) {
        if (!isLeaf(pos)) {
            if (heap[pos] > heap[leftChild(pos)] || heap[pos] > heap[rightChild(pos)]) {
                if (heap[leftChild(pos)] < heap[rightChild(pos)]) {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                } else {
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }

    public void insert(float element, int id) {
        if (size >= maxSize) {
            return;
        }
        heap[++size] = element;
        ids[size] = id;
        int current = size;

        idToIndex.put(id, current);

        while (heap[current] < heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public void decreaseKey(int id, float newKey) {
        Integer pos = idToIndex.get(id);
        if (pos == null || heap[pos] <= newKey) {
            return;
        }
        heap[pos] = newKey;
        while (heap[pos] < heap[parent(pos)]) {
            swap(pos, parent(pos));
            pos = parent(pos);
        }
    }

    public int removeMin() {
        int popped = ids[1];
        heap[1] = heap[size];
        ids[1] = ids[size];
        idToIndex.put(ids[1], 1);
        idToIndex.remove(popped);
        size--;
        minHeapify(1);
        return popped;
    }

    public float minKey() {
        return heap[1];
    }

    public int minId() {
        return ids[1];
    }

    public boolean inHeap(int id) {
        return idToIndex.containsKey(id);
    }

    public float key(int id) {
        Integer index = idToIndex.get(id);
        if (index != null) {
            return heap[index];
        }
        return Float.MAX_VALUE;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

