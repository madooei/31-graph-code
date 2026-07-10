package practice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import graph.AdjacencyListGraph;
import graph.Graph;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

/** Tests for reversing a directed graph. */
public class GraphReverseTest {

  private Set<String> neighborsOf(Graph<String> graph, String vertex) {
    Set<String> result = new HashSet<>();
    for (String neighbor : graph.neighbors(vertex)) {
      result.add(neighbor);
    }
    return result;
  }

  @Test
  public void flipsEveryEdge() {
    Graph<String> graph = new AdjacencyListGraph<>();
    graph.addVertex("A");
    graph.addVertex("B");
    graph.addVertex("C");
    graph.addEdge("A", "B");
    graph.addEdge("A", "C");
    graph.addEdge("B", "C");

    Graph<String> reversed = GraphReverse.reverse(graph);

    assertEquals(3, reversed.numVertices());
    assertEquals(3, reversed.numEdges());
    assertTrue(reversed.containsEdge("B", "A"));
    assertTrue(reversed.containsEdge("C", "A"));
    assertTrue(reversed.containsEdge("C", "B"));
    assertFalse(reversed.containsEdge("A", "B"));
    assertFalse(reversed.containsEdge("A", "C"));
    assertFalse(reversed.containsEdge("B", "C"));
  }

  @Test
  public void isolatedVerticesSurvive() {
    Graph<String> graph = new AdjacencyListGraph<>();
    graph.addVertex("A");
    graph.addVertex("B");
    graph.addVertex("C");
    graph.addVertex("D");
    graph.addEdge("A", "B");

    Graph<String> reversed = GraphReverse.reverse(graph);

    assertEquals(4, reversed.numVertices());
    assertEquals(1, reversed.numEdges());
    assertTrue(reversed.containsVertex("C"));
    assertTrue(reversed.containsVertex("D"));
    assertTrue(reversed.containsEdge("B", "A"));
  }

  @Test
  public void preservesWeight() {
    Graph<String> graph = new AdjacencyListGraph<>();
    graph.addVertex("A");
    graph.addVertex("B");
    graph.addEdge("A", "B", 5.0);

    Graph<String> reversed = GraphReverse.reverse(graph);

    assertTrue(reversed.containsEdge("B", "A"));
    assertEquals(5.0, reversed.weight("B", "A"));
  }

  @Test
  public void leavesInputUnchanged() {
    Graph<String> graph = new AdjacencyListGraph<>();
    graph.addVertex("A");
    graph.addVertex("B");
    graph.addVertex("C");
    graph.addEdge("A", "B");
    graph.addEdge("A", "C");
    graph.addEdge("B", "C");

    GraphReverse.reverse(graph);

    assertEquals(3, graph.numVertices());
    assertEquals(3, graph.numEdges());
    assertTrue(graph.containsEdge("A", "B"));
    assertTrue(graph.containsEdge("A", "C"));
    assertTrue(graph.containsEdge("B", "C"));
    assertFalse(graph.containsEdge("B", "A"));
    assertFalse(graph.containsEdge("C", "A"));
    assertFalse(graph.containsEdge("C", "B"));
    Set<String> aNeighbors = neighborsOf(graph, "A");
    assertTrue(aNeighbors.contains("B"));
    assertTrue(aNeighbors.contains("C"));
  }
}
