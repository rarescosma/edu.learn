#!/usr/bin/env python

import sys
#from heapq import heappush, heappop, heapify
from random import sample

def readGraph(filename):
  edges = {}
  nodes = set([])

  with open(filename) as f:
    next(f)
    for line in f:
      t = [int(i) for i in line.split()]

      # Add the edge
      edges[(t[0], t[1])] = t[2]

      # Add the nodes
      nodes.add(t[0])
      nodes.add(t[1])

  return (edges, nodes)


def validEdge(edge, X):
  return (edge[0] in X and edge[1] not in X) or (edge[1] in X and edge[0] not in X)


def primMST(edges, nodes):
  # Dummy implementation
  s = sample(nodes, 1)[0]
  nodes.remove(s)
  X = set([s])
  T = {}

  while len(nodes) > 0:
    # Find the cheapest edge of G with one vertex in X and the other not in X
    candidates = dict((edge, cost) for edge, cost in edges.iteritems() if validEdge(edge, X))
    edge = min(candidates, key=candidates.get)

    # Add e to T
    T[edge] = edges[edge]
    v = edge[0] if edge[1] in X else edge[1]
    X.add(v)
    nodes.remove(v)

  return T


print sum(
  primMST(
    *readGraph('edges.txt')
  ).values()
)

print primMST(*readGraph('small.txt'))
