package practice;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts an adjacency matrix into an edge list. A zero entry means "no edge";
 * any non-zero off-diagonal entry is an edge whose weight is the value in that
 * cell. A non-zero diagonal entry would be a self-loop, which this graph
 * abstraction rejects.
 */
public final class MatrixToEdgeList {

  private MatrixToEdgeList() {
    // This class should not be instantiated!
  }

  /**
   * Reads every edge out of an adjacency matrix and returns them as an edge
   * list. The vertices are the indices {@code 0} to {@code n - 1}, where
   * {@code n} is the number of rows. A non-zero diagonal entry is rejected
   * because it represents a self-loop. Every cell is inspected, so the scan is
   * O(|V|^2) regardless of how few edges the graph actually has.
   * @param matrix the square adjacency matrix.
   * @return the list of edges, one triple per non-zero off-diagonal cell.
   */
  public static List<Edge> toEdgeList(double[][] matrix) {
    List<Edge> edges = new ArrayList<>();
    int n = matrix.length;

    for (int from = 0; from < n; from++) {
      for (int to = 0; to < n; to++) {
        if (from == to && matrix[from][to] != 0) {
          throw new IllegalArgumentException("self-loops are not allowed");
        }
        if (matrix[from][to] != 0) {
          edges.add(new Edge(from, to, matrix[from][to]));
        }
      }
    }

    return edges;
  }
}
