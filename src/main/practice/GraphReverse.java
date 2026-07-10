package practice;

import graph.AdjacencyListGraph;
import graph.Graph;

/**
 * Reverses a directed graph: builds its transpose.
 *
 * <p>The transpose has the same vertices as the input, but every edge runs the
 * other way: an edge {@code u→v} in the input becomes an edge {@code v→u} in the
 * output, carrying the same weight. The input graph is never modified.
 */
public final class GraphReverse {

  private GraphReverse() {
    // This class should not be instantiated!
  }

  /**
   * Returns a new graph on the same vertices as {@code graph} with every edge
   * reversed and its weight preserved. The input graph is left unchanged.
   *
   * <p>The vertices are copied first, before any edge is added, for two reasons.
   * A vertex with no edges never appears as a {@code from} or a {@code to}, so it
   * would vanish from the output if we relied on the edges to pull vertices in.
   * And {@code addEdge} throws when either endpoint is missing, so both endpoints
   * of every flipped edge must already be present when we add it.
   *
   * <p>The flipped edge is added with the three-argument {@code addEdge} so the
   * original weight, read with {@code graph.weight(from, to)}, comes along. The
   * two-argument form would record the default weight of {@code 1.0} and throw
   * the real weight away; that is invisible on an unweighted graph but a bug on a
   * weighted one.
   *
   * @param graph the directed graph to reverse.
   * @param <V> the type of the vertices.
   * @return a new graph that is the transpose of {@code graph}.
   */
  public static <V> Graph<V> reverse(Graph<V> graph) {
    Graph<V> reversed = new AdjacencyListGraph<>();

    for (V vertex : graph.vertices()) {
      reversed.addVertex(vertex);
    }

    for (V from : graph.vertices()) {
      for (V to : graph.neighbors(from)) {
        reversed.addEdge(to, from, graph.weight(from, to));
      }
    }

    return reversed;
  }
}
