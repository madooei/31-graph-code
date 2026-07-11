package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for any class implementing the {@link Graph} interface. The tests
 * here specify the interface's behavioral contract; a concrete test class wires
 * an implementation by overriding {@link #createGraph()}, and inherits every
 * test below.
 */
public abstract class GraphTest {

  private Graph<String> graph;

  /**
   * Builds a fresh, empty graph of the implementation under test.
   *
   * @return a new empty graph.
   */
  protected abstract Graph<String> createGraph();

  @BeforeEach
  public void setup() {
    graph = createGraph();  // fresh, empty graph; no vertices or edges here
  }

  // --- Vertices ---------------------------------------------------------

  @Test
  public void newGraphIsEmpty() {
    assertEquals(0, graph.numVertices());
    assertEquals(0, graph.numEdges());
    assertFalse(graph.vertices().iterator().hasNext());
  }

  @Test
  public void addVertexNewReturnsTrue() {
    assertTrue(graph.addVertex("Paris"));
    assertTrue(graph.containsVertex("Paris"));
    assertEquals(1, graph.numVertices());
  }

  @Test
  public void addVertexDuplicateReturnsFalse() {
    graph.addVertex("Paris");
    assertFalse(graph.addVertex("Paris"));
    assertEquals(1, graph.numVertices());
  }

  @Test
  public void addVertexDuplicateDoesNotOverwriteEdges() {
    graph.addVertex("Paris");
    graph.addVertex("Lyon");
    graph.addEdge("Paris", "Lyon", 4.0);
    assertFalse(graph.addVertex("Paris"));
    assertTrue(graph.containsEdge("Paris", "Lyon"));
    assertEquals(4.0, graph.weight("Paris", "Lyon"));
  }

  @Test
  public void containsVertexTrueAndFalse() {
    graph.addVertex("Paris");
    assertTrue(graph.containsVertex("Paris"));
    assertFalse(graph.containsVertex("Lyon"));
  }

  @Test
  public void numVerticesIgnoresDuplicates() {
    graph.addVertex("Paris");
    graph.addVertex("Lyon");
    graph.addVertex("Paris");
    assertEquals(2, graph.numVertices());
  }

  @Test
  public void verticesYieldsEveryAddedVertex() {
    graph.addVertex("Paris");
    graph.addVertex("Lyon");
    graph.addVertex("Nice");
    Set<String> seen = new HashSet<>();
    for (String v : graph.vertices()) {
      seen.add(v);
    }
    Set<String> expected = new HashSet<>();
    expected.add("Paris");
    expected.add("Lyon");
    expected.add("Nice");
    assertEquals(expected, seen);
  }

  // --- Edges ------------------------------------------------------------

  @Test
  public void addEdgeNewReturnsTrueAndCounts() {
    graph.addVertex("Paris");
    graph.addVertex("Lyon");
    assertTrue(graph.addEdge("Paris", "Lyon", 5.0));
    assertTrue(graph.containsEdge("Paris", "Lyon"));
    assertEquals(1, graph.numEdges());
  }

  @Test
  public void addEdgeDuplicateReplacesWeightKeepsCount() {
    graph.addVertex("Paris");
    graph.addVertex("Lyon");
    graph.addEdge("Paris", "Lyon", 5.0);
    assertFalse(graph.addEdge("Paris", "Lyon", 9.0));
    assertEquals(9.0, graph.weight("Paris", "Lyon"));
    assertEquals(1, graph.numEdges());
  }

  @Test
  public void addEdgeUnweightedDefaultsToOne() {
    graph.addVertex("Paris");
    graph.addVertex("Lyon");
    assertTrue(graph.addEdge("Paris", "Lyon"));
    assertEquals(1.0, graph.weight("Paris", "Lyon"));
  }

  @Test
  public void addEdgeMissingEndpointThrows() {
    graph.addVertex("Paris");
    try {
      graph.addEdge("Paris", "Lyon", 5.0);
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void addEdgeSelfLoopThrows() {
    graph.addVertex("Paris");
    try {
      graph.addEdge("Paris", "Paris", 5.0);
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // expected
    }
    try {
      graph.addEdge("Paris", "Paris");
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void removeEdgeReturnsTrueThenFalse() {
    graph.addVertex("Paris");
    graph.addVertex("Lyon");
    graph.addEdge("Paris", "Lyon", 5.0);
    assertTrue(graph.removeEdge("Paris", "Lyon"));
    assertEquals(0, graph.numEdges());
    assertFalse(graph.containsEdge("Paris", "Lyon"));
    assertFalse(graph.removeEdge("Paris", "Lyon"));
  }

  @Test
  public void containsEdgeTrueAndFalse() {
    graph.addVertex("Paris");
    graph.addVertex("Lyon");
    graph.addEdge("Paris", "Lyon", 5.0);
    assertTrue(graph.containsEdge("Paris", "Lyon"));
    assertFalse(graph.containsEdge("Lyon", "Paris"));
  }

  @Test
  public void weightReturnsRecordedValue() {
    graph.addVertex("Paris");
    graph.addVertex("Lyon");
    graph.addEdge("Paris", "Lyon", 5.0);
    assertEquals(5.0, graph.weight("Paris", "Lyon"));
  }

  @Test
  public void weightAbsentEdgeThrows() {
    graph.addVertex("Paris");
    graph.addVertex("Lyon");
    try {
      graph.weight("Paris", "Lyon");
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  // --- Directedness -----------------------------------------------------

  @Test
  public void edgeIsDirected() {
    graph.addVertex("Paris");
    graph.addVertex("Lyon");
    graph.addEdge("Paris", "Lyon");
    assertTrue(graph.containsEdge("Paris", "Lyon"));
    assertFalse(graph.containsEdge("Lyon", "Paris"));
  }

  @Test
  public void undirectedPairIsBothDirections() {
    graph.addVertex("Paris");
    graph.addVertex("Lyon");
    graph.addEdge("Paris", "Lyon");
    graph.addEdge("Lyon", "Paris");
    assertTrue(graph.containsEdge("Paris", "Lyon"));
    assertTrue(graph.containsEdge("Lyon", "Paris"));
    assertEquals(2, graph.numEdges());
  }

  // --- removeVertex -----------------------------------------------------

  @Test
  public void removeVertexRemovesIncidentEdgesBothWays() {
    graph.addVertex("Paris");
    graph.addVertex("Lyon");
    graph.addVertex("Nice");
    graph.addEdge("Paris", "Lyon", 5.0);  // in-edge to Lyon
    graph.addEdge("Lyon", "Nice", 3.0);   // out-edge from Lyon
    assertEquals(2, graph.numEdges());
    assertTrue(graph.removeVertex("Lyon"));
    assertFalse(graph.containsVertex("Lyon"));
    assertTrue(graph.containsVertex("Paris"));
    assertTrue(graph.containsVertex("Nice"));
    assertFalse(graph.containsEdge("Paris", "Lyon"));
    assertFalse(graph.containsEdge("Lyon", "Nice"));
    assertEquals(0, graph.numEdges());
    assertEquals(2, graph.numVertices());
  }

  @Test
  public void removeVertexAbsentReturnsFalse() {
    graph.addVertex("Paris");
    assertFalse(graph.removeVertex("Lyon"));
    assertEquals(1, graph.numVertices());
  }

  // --- neighbors --------------------------------------------------------

  @Test
  public void neighborsAreOutNeighbors() {
    graph.addVertex("Paris");
    graph.addVertex("Lyon");
    graph.addVertex("Nice");
    graph.addEdge("Paris", "Lyon", 5.0);
    graph.addEdge("Paris", "Nice", 2.0);
    Set<String> seen = new HashSet<>();
    for (String n : graph.neighbors("Paris")) {
      seen.add(n);
    }
    Set<String> expected = new HashSet<>();
    expected.add("Lyon");
    expected.add("Nice");
    assertEquals(expected, seen);
  }

  @Test
  public void neighborsEmptyWhenNoOutEdges() {
    graph.addVertex("Paris");
    assertFalse(graph.neighbors("Paris").iterator().hasNext());
  }

  @Test
  public void neighborsMissingVertexThrows() {
    try {
      graph.neighbors("Paris");
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  // --- Null rejection ---------------------------------------------------

  @Test
  public void addVertexNullThrows() {
    try {
      graph.addVertex(null);
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void containsVertexNullThrows() {
    try {
      graph.containsVertex(null);
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void containsEdgeNullThrows() {
    try {
      graph.containsEdge(null, "Lyon");
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void neighborsNullThrows() {
    try {
      graph.neighbors(null);
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  // --- Scenario / trace -------------------------------------------------

  @Test
  public void directedWeightedCityGraph() {
    graph.addVertex("A");
    graph.addVertex("B");
    graph.addVertex("C");
    graph.addEdge("A", "B", 5.0);
    graph.addEdge("A", "C", 2.0);
    graph.addEdge("B", "C", 1.0);
    assertEquals(3, graph.numVertices());
    assertEquals(3, graph.numEdges());
    double total = 0.0;
    for (String n : graph.neighbors("A")) {
      total += graph.weight("A", n);
    }
    assertEquals(7.0, total);
    graph.removeVertex("C");
    assertEquals(1, graph.numEdges());
    assertFalse(graph.containsEdge("A", "C"));
  }
}
