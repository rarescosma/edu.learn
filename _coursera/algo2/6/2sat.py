#!/usr/bin/env python

import sys
import gc

# SCC algo

# System management
sys.setrecursionlimit(300000)

# Input file
input_file = sys.argv[1]

# The Node data structure
class Node:
    def __init__(self, label, edges):
        self.label = label
        self.edges = edges
        self.explored = 0
        self.leader = 0
        self.finish = 0

    def addEdge(self, i):
        self.edges.append(i)


# DFS
def DFS(G, i):
    global t,s
    G[i].explored = 1
    G[i].leader = s

    for j in G[i].edges:
        if not G[j].explored:
            DFS(G, j)

    t = t + 1
    G[i].finish = t


# DFSLoop
def DFSLoop(G):
    global t,s
    t,s = 0,0

    node_range = sorted(G.keys(), reverse=True)

    for i in node_range:
        if not G[i].explored:
            s = i
            DFS(G, i) # Each DFS is gonna explore the whole SCC -> same leader



# Read the input for Grev
fin = open(input_file)
Grev = {}

for line in fin:
    e = [int(i) for i in line.split()]

    if not e[0] in Grev:
        Grev[e[0]] = Node(e[0],[])

    if not e[1] in Grev:
        Grev[e[1]] = Node(e[1],[])

    Grev[e[0]].addEdge(e[1])

fin.close()

del fin
gc.collect()


# First Loop
DFSLoop(Grev)
fT = {v[0]:v[1].finish for v in Grev.items()}

del Grev
gc.collect()


# Read the input for G
fin = open(input_file)
G = {v[1]:Node(v[0],[]) for v in fT.items()}

for line in fin:
    e = [fT[int(i)] for i in line.split()]
    G[e[1]].addEdge(e[0])

fin.close()

del fin
del fT
gc.collect()


# Second Loop
DFSLoop(G)
scc = dict((v[1].label, v[1].leader) for v in G.items())
for n in scc.keys():
    if -n in scc and scc[-n] == scc[n]:
        print "not satisfiable"
        exit()

print "satisfiable"
