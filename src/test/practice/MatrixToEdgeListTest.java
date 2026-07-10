package practice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

/** Unit tests for converting an adjacency matrix to an edge list. */
public class MatrixToEdgeListTest {

  @Test
  public void threeVertexUnitWeights() {
    double[][] matrix = {{0, 1, 1}, {0, 0, 1}, {0, 0, 0}};
    List<Edge> edges = MatrixToEdgeList.toEdgeList(matrix);
    Set<Edge> actual = new HashSet<>(edges);
    assertEquals(3, edges.size());
    assertEquals(3, actual.size());
    assertTrue(actual.contains(new Edge(0, 1, 1)));
    assertTrue(actual.contains(new Edge(0, 2, 1)));
    assertTrue(actual.contains(new Edge(1, 2, 1)));
  }

  @Test
  public void nonUnitAndNegativeWeights() {
    double[][] matrix = {{0, 5, 0}, {0, 0, -2}, {0, 0, 0}};
    List<Edge> edges = MatrixToEdgeList.toEdgeList(matrix);
    Set<Edge> actual = new HashSet<>(edges);
    assertEquals(2, edges.size());
    assertEquals(2, actual.size());
    assertTrue(actual.contains(new Edge(0, 1, 5)));
    assertTrue(actual.contains(new Edge(1, 2, -2)));
  }

  @Test
  public void allZeroMatrixHasNoEdges() {
    double[][] matrix = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    List<Edge> edges = MatrixToEdgeList.toEdgeList(matrix);
    assertEquals(0, edges.size());
  }

  @Test
  public void emptyMatrixHasNoEdges() {
    double[][] matrix = new double[0][0];
    List<Edge> edges = MatrixToEdgeList.toEdgeList(matrix);
    assertEquals(0, edges.size());
  }

  @Test
  public void selfLoopOnDiagonalIsRecorded() {
    double[][] matrix = {{3, 0}, {0, 0}};
    List<Edge> edges = MatrixToEdgeList.toEdgeList(matrix);
    Set<Edge> actual = new HashSet<>(edges);
    assertEquals(1, edges.size());
    assertTrue(actual.contains(new Edge(0, 0, 3)));
  }
}
