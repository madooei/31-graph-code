package practice;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts an adjacency matrix into an edge list. A zero entry means "no edge";
 * any non-zero entry is an edge whose weight is the value in that cell.
 */
public final class MatrixToEdgeList {

  private MatrixToEdgeList() {
    // This class should not be instantiated!
  }

  /**
   * Reads every edge out of an adjacency matrix and returns them as an edge
   * list. The vertices are the indices {@code 0} to {@code n - 1}, where
   * {@code n} is the number of rows. A self-loop on the diagonal is recorded
   * like any other cell. Every cell is inspected, so the scan is
   * O(|V|^2) regardless of how few edges the graph actually has.
   * @param matrix the square adjacency matrix.
   * @return the list of edges, one triple per non-zero cell.
   */
  public static List<Edge> toEdgeList(double[][] matrix) {
    List<Edge> edges = new ArrayList<>();
    int n = matrix.length;

    for (int from = 0; from < n; from++) {
      for (int to = 0; to < n; to++) {
        if (matrix[from][to] != 0) {
          edges.add(new Edge(from, to, matrix[from][to]));
        }
      }
    }

    return edges;
  }
}
