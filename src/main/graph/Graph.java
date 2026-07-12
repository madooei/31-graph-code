package graph;

/**
 * A graph of vertices of type {@code V}.
 *
 * <p>A graph stores a set of vertices and a set of edges that join distinct
 * vertices. This is a directed graph: each edge is directed and carries a
 * {@code double} weight, and {@link #neighbors(Object)} returns the out-neighbors
 * of a vertex, the vertices reachable by following an edge in its direction. A
 * directed graph is the more general object; an undirected graph is represented
 * by adding one directed edge in each direction.</p>
 *
 * <p>There is a single type parameter, {@code V}, for the type of the vertices.
 * A vertex can be anything with an identity: a {@code String} for a city name,
 * an {@code Integer} for a numeric id, or an object of your own. Unlike
 * {@code Map<K, V>}, there is no second type parameter, because the only thing
 * an edge carries beyond its two endpoints is a weight, and a weight is always
 * a {@code double}. The vertex type is the one thing a caller has to choose.</p>
 *
 * @param <V> the type of the vertices in this graph.
 */
public interface Graph<V> {

  /**
   * Adds a vertex to this graph.
   *
   * @param vertex the vertex to add.
   * @return true if the vertex was added; false if it was already present, in
   *     which case the graph is unchanged.
   * @throws IllegalArgumentException if vertex is null.
   */
  boolean addVertex(V vertex);

  /**
   * Removes a vertex from this graph, together with every edge incident on it
   * in either direction.
   *
   * @param vertex the vertex to remove.
   * @return true if the vertex was removed; false if it was not present.
   * @throws IllegalArgumentException if vertex is null.
   */
  boolean removeVertex(V vertex);

  /**
   * Reports whether a vertex is present in this graph. This is a membership
   * query and changes nothing.
   *
   * @param vertex the vertex to look for.
   * @return true if the vertex is present; false otherwise.
   * @throws IllegalArgumentException if vertex is null.
   */
  boolean containsVertex(V vertex);

  /**
   * Adds a directed edge from one vertex to another and records its weight.
   * Both endpoints must already be present in the graph, and they must be
   * distinct. If the edge already exists, its weight is replaced with the new
   * weight and the call returns false, since the edge itself was not new.
   *
   * @param from the vertex the edge leaves.
   * @param to the vertex the edge enters.
   * @param weight the weight to record for the edge.
   * @return true if a new edge was added; false if the edge already existed and
   *     its weight was replaced.
   * @throws IllegalArgumentException if from or to is null, if the endpoints
   *     are equal, or if either endpoint is not already in the graph.
   */
  boolean addEdge(V from, V to, double weight);

  /**
   * Adds a directed edge with a default weight of {@code 1.0}. This is a
   * convenience for edges whose weight you do not care about; it is a default
   * value, not a separate unweighted mode, and the interface enforces nothing
   * about weights. Both endpoints must already be present in the graph, and
   * they must be distinct. If the edge already exists, its weight is replaced
   * with {@code 1.0} and the call returns false.
   *
   * @param from the vertex the edge leaves.
   * @param to the vertex the edge enters.
   * @return true if a new edge was added; false if the edge already existed and
   *     its weight was replaced.
   * @throws IllegalArgumentException if from or to is null, if the endpoints
   *     are equal, or if either endpoint is not already in the graph.
   */
  boolean addEdge(V from, V to);

  /**
   * Removes the directed edge from one vertex to another.
   *
   * @param from the vertex the edge leaves.
   * @param to the vertex the edge enters.
   * @return true if the edge was removed; false if it was not present.
   * @throws IllegalArgumentException if from or to is null.
   */
  boolean removeEdge(V from, V to);

  /**
   * Reports whether a directed edge from one vertex to another is present. This
   * is a membership query and changes nothing.
   *
   * @param from the vertex the edge leaves.
   * @param to the vertex the edge enters.
   * @return true if the edge is present; false otherwise.
   * @throws IllegalArgumentException if from or to is null.
   */
  boolean containsEdge(V from, V to);

  /**
   * Returns the weight recorded for the directed edge from one vertex to
   * another. A weight is a {@code double}, and a {@code double} has no natural
   * "not present" value the way an object reference has {@code null}, so rather
   * than hand back a misleading number this method throws when the edge is
   * absent. In practice you ask for the weight of an edge you already know is
   * present, usually one you just reached by walking a vertex's neighbors.
   *
   * @param from the vertex the edge leaves.
   * @param to the vertex the edge enters.
   * @return the weight recorded for the edge.
   * @throws IllegalArgumentException if from or to is null, or if the edge is
   *     not present.
   */
  double weight(V from, V to);

  /**
   * Returns the out-neighbors of a vertex: the vertices reachable by following
   * one edge out of the given vertex. This is the operation the graph
   * algorithms lean on hardest, since a search makes progress by moving from a
   * vertex to its neighbors and repeating. The result is an {@code Iterable<V>},
   * so you walk it with an ordinary for-each loop.
   *
   * @param vertex the vertex whose out-neighbors are wanted.
   * @return an iterable over the out-neighbors of the vertex.
   * @throws IllegalArgumentException if vertex is null, or if it is not in the
   *     graph.
   */
  Iterable<V> neighbors(V vertex);

  /**
   * Returns all the vertices of this graph, so an algorithm can start from each
   * one or initialize a value per vertex. The result is an {@code Iterable<V>},
   * so you walk it with an ordinary for-each loop.
   *
   * @return an iterable over all vertices in the graph.
   */
  Iterable<V> vertices();

  /**
   * Reports the number of vertices in this graph, the count |V|.
   *
   * @return the number of vertices.
   */
  int numVertices();

  /**
   * Reports the number of edges in this graph, the count |E|.
   *
   * @return the number of edges.
   */
  int numEdges();
}
