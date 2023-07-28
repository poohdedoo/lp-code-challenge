package com.littlepay.code.challenge;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.Set;

public class FareGraphImpl implements FareGraph {
    private Graph<String, DefaultEdge> graph = null;

    @Override
    public void init() {
        graph = new SimpleWeightedGraph<>(DefaultEdge.class);

        graph.addVertex("Stop1");
        graph.addVertex("Stop2");
        graph.addVertex("Stop3");

        DefaultEdge edge = graph.addEdge("Stop1", "Stop2");
        graph.setEdgeWeight(edge, 3.25);
        edge = graph.addEdge("Stop2", "Stop3");
        graph.setEdgeWeight(edge, 5.50);
        edge = graph.addEdge("Stop1", "Stop3");
        graph.setEdgeWeight(edge, 7.30);
    }

    @Override
    public double getFare(String origin, String destination) {
        DefaultEdge edge = graph.getEdge(origin, destination);
        if (edge == null) {
            throw new IllegalStateException("No fare class defined between '" + origin + "' and '"
                    + destination + "'.");
        }
        return graph.getEdgeWeight(edge);
    }

    @Override
    public double getMaximumFareFromOrigin(String origin) {
        Set<DefaultEdge> edges = graph.edgesOf(origin);
        double maxFare = Double.MIN_VALUE;
        for (DefaultEdge edge: edges) {
            double fare = graph.getEdgeWeight(edge);
            maxFare = Math.max(maxFare, fare);
        }
        return maxFare;
    }
}
