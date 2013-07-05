import random
import copy
import sys

# Read the input
fin = open("kargerAdj.txt")

G = {}

for line in fin:
    t = []
    t = [int(i) for i in line.split()]
    G[t[0]] = t[1:]


def chooseEdge(G):
    v1 = random.choice(list(G.keys()))
    v2 = random.choice(G[v1])
    return v1,v2


def kargerContract(G):
    if( len(G) > 2 ):
        # Pick an edge
        v1, v2 = chooseEdge(G)

        # All edges of v2 get squashed into v1
        G[v1].extend(G[v2])

        # Replace v2's idendity with v1
        for k in G[v2]:
            G[k] = [v if v != v2 else v1 for v in G[k]]

        # Self-loops are stupid
        G[v1] = [v for v in G[v1] if v != v1]

        # Remove v2's row
        G.pop(v2)

        return kargerContract(G)
    else:
        return G


def printGraph(G):
    keys = list(G.keys())
    keys.sort()
    for k in keys:
        print ( 'Vertex ' + str(k) + ': ' + str(G[k]) )


def karger(G):
    kargerContract(G)
    return len(G[list(G.keys())[0]])


min = 1000000
for i in range(0, 100): # Run a 1000 times!
    Gc = copy.deepcopy(G)
    instance = karger(Gc)
    if instance < min: min = instance

print ( 'Min-cut:', min )