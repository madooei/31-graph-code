package graph;

import map.HashMap;
import map.Map;

/**
 * Adjacency-list implementation of the Graph ADT, backed by a map of maps.
 *
 * <p>The outer map keys each vertex to its own inner map, and that inner map
 * keys each out-neighbor to the weight of the edge reaching it. Every vertex in
 * the graph has an inner map, empty until an edge is added, so the edge
 * operations can assume a source vertex already has somewhere to store edges. An
 * edge is stored exactly once, under its source, which makes a single edge cheap
 * to add, look up, and remove, but makes {@link #removeVertex(Object)} cost
 * {@code O(|V| + |E|)}, since the edges pointing into a vertex are unindexed and
 * must be found by scanning every other vertex's inner map.
 *
 * @param <V> the type of the vertices in this graph.
 */
public class AdjacencyListGraph<V> implements Graph<V> {

  private final Map<V, Map<V, Double>> adjacency;  // vertex -> (out-neighbor -> weight)
  private int edgeCount;                            // how many edges the graph holds

  public AdjacencyListGraph() {
    adjacency = new HashMap<>();  // the graph starts empty
    edgeCount = 0;
  }

  @Override
  public boolean addVertex(V vertex) {
    if (vertex == null) {
      throw new IllegalArgumentException("vertex cannot be null");
    }
    if (adjacency.containsKey(vertex)) {
      return false;  // already present
    }
    adjacency.put(vertex, new HashMap<>());  // start it with no edges
    return true;
  }

  @Override
  public boolean removeVertex(V vertex) {
    if (vertex == null) {
      throw new IllegalArgumentException("vertex cannot be null");
    }
    if (!adjacency.containsKey(vertex)) {
      return false;  // not present; nothing changed
    }
    // remove the edges leaving the vertex
    edgeCount -= adjacency.get(vertex).size();
    adjacency.remove(vertex);
    // remove the edges pointing into the vertex, wherever they are
    for (V other : adjacency) {
      Map<V, Double> out = adjacency.get(other);
      if (out.containsKey(vertex)) {
        out.remove(vertex);
        edgeCount--;
      }
    }
    return true;
  }

  @Override
  public boolean containsVertex(V vertex) {
    if (vertex == null) {
      throw new IllegalArgumentException("vertex cannot be null");
    }
    return adjacency.containsKey(vertex);
  }

  @Override
  public boolean addEdge(V from, V to, double weight) {
    if (from == null || to == null) {
      throw new IllegalArgumentException("endpoints cannot be null");
    }
    if (!adjacency.containsKey(from) || !adjacency.containsKey(to)) {
      throw new IllegalArgumentException("both endpoints must be in the graph");
    }
    Map<V, Double> out = adjacency.get(from);
    boolean isNew = !out.containsKey(to);
    out.put(to, weight);  // store the weight, or replace it if the edge existed
    if (isNew) {
      edgeCount++;
    }
    return isNew;
  }

  @Override
  public boolean addEdge(V from, V to) {
    return addEdge(from, to, 1.0);
  }

  @Override
  public boolean removeEdge(V from, V to) {
    if (from == null || to == null) {
      throw new IllegalArgumentException("endpoints cannot be null");
    }
    Map<V, Double> out = adjacency.get(from);
    if (out == null || !out.containsKey(to)) {
      return false;  // no such edge; nothing changed
    }
    out.remove(to);
    edgeCount--;
    return true;
  }

  @Override
  public boolean containsEdge(V from, V to) {
    if (from == null || to == null) {
      throw new IllegalArgumentException("endpoints cannot be null");
    }
    Map<V, Double> out = adjacency.get(from);
    return out != null && out.containsKey(to);
  }

  @Override
  public double weight(V from, V to) {
    if (from == null || to == null) {
      throw new IllegalArgumentException("endpoints cannot be null");
    }
    Map<V, Double> out = adjacency.get(from);
    if (out == null || !out.containsKey(to)) {
      throw new IllegalArgumentException("no such edge");
    }
    return out.get(to);
  }

  @Override
  public Iterable<V> neighbors(V vertex) {
    if (vertex == null) {
      throw new IllegalArgumentException("vertex cannot be null");
    }
    Map<V, Double> out = adjacency.get(vertex);
    if (out == null) {
      throw new IllegalArgumentException("vertex not in the graph");
    }
    return out;  // its keys are the out-neighbors
  }

  @Override
  public Iterable<V> vertices() {
    return adjacency;  // its keys are the vertices
  }

  @Override
  public int numVertices() {
    return adjacency.size();
  }

  @Override
  public int numEdges() {
    return edgeCount;
  }
}
