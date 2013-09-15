#!/usr/bin/env python

import sys
import time
from heapq import heappush, heappop, heapify
from random import sample, choice
from copy import deepcopy, copy

'''
Reads the graph into a dictionary:
- keys are edges of the form (u, v)
- values are edge weights
'''
def readGraph(filename):
  edges = {}

  with open(filename) as f:
    next(f)
    for line in f:
      t = [int(i) for i in line.split()]

      # Add the edge
      edges[(t[0], t[1])] = t[2]
      edges[(t[1], t[0])] = t[2]

  return edges


'''
Takes in a dictionary of edges and produces
a list of unique nodes
'''
def edges2Nodes(edges):
  nodes = set([])

  for (u,v) in edges.keys():
    nodes.add(u)
    nodes.add(v)

  return nodes


'''
Takes in a dictionary of edges and produces the
adjacency dictionary:
- keys are source nodes
- values are sets of nodes that the source node has edges with
'''
def edges2Adjacency(edges):
  adj = {}

  for (u,v) in edges:
    if u not in adj:
      adj[u] = set([])
    if v not in adj:
      adj[v] = set([])
    adj[u].add(v)
    adj[v].add(u)

  return adj


'''
Helper method used in the naive implementation.
Checks the validity of an edge, with respect to a list of nodes.
An edge is valid if and only if one node is in X and the other isn't.
'''
def validEdge(edge, X):
  return (edge[0] in X and edge[1] not in X) or (edge[1] in X and edge[0] not in X)


'''
Naive implementation of Prim's algorithm.
'''
def primNaive(edges):
  # Dummy implementation
  nodes = edges2Nodes(edges)

  # Pick a source node
  s = sample(nodes, 1)[0]
  nodes.remove(s)
  X = set([s])
  T = {}

  while nodes:
    # Find the cheapest edge of G with one vertex in X and the other not in X
    candidates = dict((edge, cost) for edge, cost in edges.iteritems() if validEdge(edge, X))

    edge = min(candidates, key=candidates.get)

    # Add e to T
    T[edge] = edges[edge]
    v = edge[0] if edge[1] in X else edge[1]
    X.add(v)
    nodes.remove(v)

  return T


'''
Heap-based implementation of Prim's algorithm.
'''
def primHeap(edges):
  # Grab the nodes list
  nodes = edges2Nodes(edges)

  # Grab the adjency list
  adj = edges2Adjacency(edges)
  nodes = adj.keys()

  # Will hold a linked list of nodes
  L = dict([(n, None) for n in nodes])

  # Set the source node
  s = choice(nodes)

  # Make the heap, make sure the source node has 0 key
  heap = [(float("inf"), u) for u in nodes if u != s]
  heap.append((0, s))
  heapify(heap)

  # Keep a dictionary for heap keys
  heap_d = dict([(v,(k,v)) for (k,v) in heap])

  while heap:
    (key, u) = heappop(heap)
    del heap_d[u]
    for v in adj[u]:
      if v in heap_d and edges[(u,v)] < heap_d[v][0]:
        # Update v's key to equal (u,v), and add to MST
        heap.remove(heap_d[v])
        L[v] = u
        a = edges[(u,v)]
        heap_d[v] = (a,v)
        heap.append((a,v))
    heapify(heap)

  # Process L to get a list of edges
  T = {}
  for (u, v) in L.iteritems():
    if u and v:
      T[(u,v)] = edges[(u,v)]

  return T

'''
Simple profiling to show the heap implementation speedup.
'''
def profile(n):
  G = readGraph('edges.txt')

  print "Timing %s runs of Prim, naive implementation..." % (n)
  start = time.clock()
  for x in range(0, n):
    primNaive(G)
  print "Total time: %ss \n" % (time.clock() - start)

  print "Timing %s runs of Prim, heap implementation..." % (n)
  start = time.clock()
  for x in range(0, n):
    primHeap(G)
  print "Total time: %ss \n" % (time.clock() - start)

# Profile a couple of runs of both implementations
profile(5)

# Display the result
print "Total MST cost: %s" % (sum(primHeap(readGraph('edges.txt')).values()))

