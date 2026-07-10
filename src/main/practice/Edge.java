package practice;

import java.util.Objects;

/**
 * One edge of a directed, weighted graph, recorded as a (from, to, weight)
 * triple. Immutable and compared by value so edges can be collected into sets.
 */
public final class Edge {

  private final int from;
  private final int to;
  private final double weight;

  /**
   * Constructs an edge from one vertex to another with the given weight.
   * @param from the source vertex.
   * @param to the destination vertex.
   * @param weight the weight of the edge.
   */
  public Edge(int from, int to, double weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  /**
   * The source vertex of this edge.
   * @return the from vertex.
   */
  public int from() {
    return from;
  }

  /**
   * The destination vertex of this edge.
   * @return the to vertex.
   */
  public int to() {
    return to;
  }

  /**
   * The weight of this edge.
   * @return the weight.
   */
  public double weight() {
    return weight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Edge edge = (Edge) o;
    return from == edge.from
        && to == edge.to
        && Double.compare(weight, edge.weight) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to, weight);
  }

  @Override
  public String toString() {
    return "(" + from + ", " + to + ", " + weight + ")";
  }
}
