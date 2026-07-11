# Graphs

The code for the graph chapter: the `Graph` ADT, an adjacency-list implementation, and two
practice problems. This is reading material — every class is meant to be studied line by line.

## Layout

```plaintext
src/main/graph/
  Graph.java                 the ADT: the interface every graph is written against
  AdjacencyListGraph.java     a directed graph backed by a map of maps
  Main.java                   a small demo you can run
src/main/map/
  Map.java, HashMap.java      the course Map, reused to store the adjacency (from the hashing unit)
src/main/practice/
  GraphReverse.java           reverse (transpose) a directed graph
  MatrixToEdgeList.java        convert an adjacency matrix to an edge list
  Edge.java                    an immutable (from, to, weight) triple
src/test/graph/               the contract tests for the Graph ADT
src/test/practice/            tests for the practice problems
```

## The Graph ADT

`Graph<V>` has one type parameter, the vertex type; an edge is directed and carries a `double`
weight. Vertices behave like a set (`addVertex`, `removeVertex`, `containsVertex`); edges are added
with `addEdge(from, to, weight)` (or `addEdge(from, to)` for the default weight `1.0`), and both
endpoints must already be present and distinct. You look around the graph with `neighbors(vertex)`
(the out-neighbors) and `vertices()`, both `Iterable<V>` for an ordinary for-each loop.

`AdjacencyListGraph<V>` stores a `Map<V, Map<V, Double>>` — each vertex maps to a map from its
out-neighbors to edge weights — plus a running `edgeCount`. Specific-edge operations are `O(|V|)` in
the worst case and `O(1)` on average; `removeVertex` is `O(|V| + |E|)` in the worst case, `O(|V|)` on
average, because in-edges are not indexed.

## Running

From this directory:

```plaintext
./scripts/run.sh      # compile and run the demo (graph.Main)
./scripts/test.sh     # compile and run all JUnit tests
```

Both scripts compile into `out/` and put the vendored JUnit jar in `lib/` on the classpath, so you
never type the classpath by hand. No build system and no IDE configuration are shipped; open the
folder in any IDE and it will detect `src/main` and `src/test` as source roots.
