package graph;

/**
 * Runs the full {@link GraphTest} contract suite against {@link
 * AdjacencyListGraph}.
 */
public class AdjacencyListGraphTest extends GraphTest {

  @Override
  protected Graph<String> createGraph() {
    return new AdjacencyListGraph<>();
  }
}
