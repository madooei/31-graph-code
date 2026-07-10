package graph;

/**
 * A small demonstration of the {@link Graph} interface, built on the
 * {@link AdjacencyListGraph} implementation.
 *
 * <p>It models a handful of cities as vertices and one-way roads between them
 * as directed, weighted edges, then exercises the operations a caller reaches
 * for most: counting vertices and edges, walking a vertex's neighbors with the
 * weight of each road, asking whether a given edge is present, and removing a
 * city to watch its roads leave with it.</p>
 */
public class Main {

  /**
   * Runs the demonstration.
   *
   * @param args ignored.
   */
  public static void main(String[] args) {
    Graph<String> g = new AdjacencyListGraph<>();

    // Four cities become the vertices of the graph.
    g.addVertex("Albany");
    g.addVertex("Boston");
    g.addVertex("Chicago");
    g.addVertex("Denver");

    // Directed weighted edges: a one-way road from one city to another, with
    // the road's length as its weight.
    g.addEdge("Albany", "Boston", 170.0);
    g.addEdge("Albany", "Chicago", 800.0);
    g.addEdge("Boston", "Chicago", 980.0);
    g.addEdge("Chicago", "Denver", 1000.0);

    // One unweighted edge: when the weight does not matter, the overload
    // records the default weight of 1.0.
    g.addEdge("Denver", "Albany");

    System.out.println("Cities (vertices): " + g.numVertices());
    System.out.println("Roads (edges): " + g.numEdges());
    System.out.println();

    // The weighted-walk idiom: visit every out-neighbor of a vertex and ask for
    // the weight of the edge that reaches it.
    String start = "Albany";
    System.out.println("Roads leaving " + start + ":");
    for (String neighbor : g.neighbors(start)) {
      double w = g.weight(start, neighbor);
      System.out.println("  " + start + " -> " + neighbor + " (" + w + ")");
    }
    System.out.println();

    // containsEdge respects direction: the road Albany -> Boston is present,
    // but there is no road back from Boston to Albany.
    System.out.println("Is there a road Albany -> Boston? "
        + g.containsEdge("Albany", "Boston"));
    System.out.println("Is there a road Boston -> Albany? "
        + g.containsEdge("Boston", "Albany"));
    System.out.println();

    // Removing a city takes every road into or out of it along with it.
    // Denver has two incident roads: Chicago -> Denver and Denver -> Albany.
    System.out.println("Removing Denver...");
    g.removeVertex("Denver");
    System.out.println("Cities (vertices): " + g.numVertices());
    System.out.println("Roads (edges): " + g.numEdges());
  }
}
