package org.example;

import java.util.*;
import java.io.*;



class Graph {
    private static class Edge {
        int destination;
        float weight;

        Edge(int destination, float weight) {
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + destination + ", " + weight + ")";
        }
    }
    private final int vertices;
    private final List<List<Edge>> adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>();
        for (int i = 0; i <= vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination, float weight) {
        adjacencyList.get(source).add(new Edge(destination, weight));
        adjacencyList.get(destination).add(new Edge(source, weight)); // Since the graph is undirected
    }

    public void primsMST() {
        boolean[] inMST = new boolean[vertices + 1];
        MinHeap minHeap = new MinHeap(vertices);
        int[] parent = new int[vertices + 1];
        float[] edgeWeights = new float[vertices + 1]; // Store the weights of the edges in the MST

        for (int i = 2; i <= vertices; i++) {
            minHeap.insert(Float.MAX_VALUE, i);
            parent[i] = -1;
            edgeWeights[i] = Float.MAX_VALUE;
        }
        minHeap.insert(0, 1); // Start with vertex 1

        while (!minHeap.isEmpty()) {
            int u = minHeap.removeMin();
            inMST[u] = true;

            for (Edge edge : adjacencyList.get(u)) {
                int v = edge.destination;
                if (!inMST[v] && minHeap.inHeap(v) && edge.weight < minHeap.key(v)) {
                    parent[v] = u;
                    edgeWeights[v] = edge.weight;
                    minHeap.decreaseKey(v, edge.weight);
                }
            }
        }

        System.out.println("Edges of the Minimum Spanning Tree:");
        for (int i = 2; i <= vertices; i++) {
            if (parent[i] != -1) {
                System.out.println(parent[i] + " - " + i + " --> " + edgeWeights[i]);
            }
        }
    }
}



public class PrimMST {
    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("graph.txt");
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found!");
        }
        Scanner scanner = new Scanner(inputStream);
        int vertices = scanner.nextInt();
        Graph graph = new Graph(vertices);

        while (scanner.hasNextInt()) {
            int source = scanner.nextInt();
            int destination = scanner.nextInt();
            float weight = scanner.nextFloat();
            graph.addEdge(source, destination, weight);
        }

        graph.primsMST();
        scanner.close();
    }
}
