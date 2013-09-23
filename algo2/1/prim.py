#!/usr/bin/env python

import sys
import time
import itertools
from heapq import heappush, heappop
from random import sample, choice


'''
Reads the graph into a dictionary:
- keys are edges of the form (u, v)
- values are edge weights
'''
def readGraph(filename, duplicates=True):
  edges = {}

  with open(filename) as f:
    next(f)
    for line in f:
      t = [int(i) for i in line.split()]

      # Add the edge
      edges[(t[0], t[1])] = t[2]
      if duplicates:
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
  # Heap with REMOVE :)
  pq = []
  entry_finder = {}
  REMOVED = '<removed-item>'
  counter = itertools.count()

  def add_item(item, priority=0):
    'Add a new item or update the priority of an existing item'
    if item in entry_finder:
      remove_item(item)
    count = next(counter)
    entry = [priority, count, item]
    entry_finder[item] = entry
    heappush(pq, entry)

  def remove_item(item):
    'Mark an existing item as REMOVED.  Raise KeyError if not found.'
    entry = entry_finder.pop(item)
    entry[-1] = REMOVED

  def pop_item():
    'Remove and return the lowest priority item. Raise KeyError if empty.'
    while pq:
      priority, count, item = heappop(pq)
      if item is not REMOVED:
        del entry_finder[item]
        return item
    raise KeyError('Heap is empty')

  # Grab the adjency list
  adj = edges2Adjacency(edges)
  nodes = adj.keys()
  MST = {}

  # Source vertex
  s = nodes.pop()
  add_item(s, 0)

  # Add rest of nodes
  for v in nodes:
    add_item(v, float("inf"))

  while entry_finder:
    u = pop_item()

    for v in adj[u]:
      if v in entry_finder and edges[(u,v)] < entry_finder[v][0]:
        # Update v's key to equal (u,v), and add to MST
        remove_item(v)
        add_item(v, edges[(u,v)])
        MST[v] = u

  # Process MST and get the edge costs
  T = { edge: edges[edge] for edge in MST.iteritems() }

  return T


# '''
# TODO
# '''
# def kruskal(edges):
#   # Sort the edges
#   E = sorted(edges, key = edges.get)
#   # Get the nodes
#   V = edges2Nodes(edges)
#   n_V = len(V)
#   # Simple union-find structure
#   uf = []
#   MST = {}




'''
Simple profiling to show the heap implementation speedup.
'''
def profile(n):
  G = readGraph('edges.txt')

  print "Timing %s runs of Prim's MST, heap implementation..." % (n)
  start = time.clock()
  for x in range(0, n):
    primHeap(G)
  print "Total time: %ss \n" % (time.clock() - start)

  print "Timing %s runs of Prim's MST, naive implementation..." % (n)
  start = time.clock()
  for x in range(0, n):
    primNaive(G)
  print "Total time: %ss \n" % (time.clock() - start)


# Profile a couple of runs of both implementations
profile(5)

# Display the result
print "Total MST cost: %s" % (sum(primHeap(readGraph('edges.txt')).values()))

#kruskal(readGraph('small.txt', False))
