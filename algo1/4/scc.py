import sys
import gc

sys.setrecursionlimit(300000)

# The Node data structure
class Node:
    def __init__(self, edges):
        self.edges = edges
        self.explored = 0
        self.leader = 0
        self.finish = 0

    def addEdge(self, i):
        self.edges.append(i)


# DFS
zzz = 1
def DFS(G, i):
    global zzz
    zzz = zzz + 1
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
fin = open("scc_med.001")
Grev = {}

for line in fin:
    e = [int(i) for i in line.split()]

    if not e[0] in Grev:
        Grev[e[0]] = Node([])

    if not e[1] in Grev:
        Grev[e[1]] = Node([])

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
fin = open("scc_med.001")
G = {v[1]:Node([]) for v in fT.items()}

for line in fin:
    e = [fT[int(i)] for i in line.split()]
    G[e[1]].addEdge(e[0])

fin.close()

del fin
del fT
gc.collect()


# Second Loop
DFSLoop(G)
lst = sorted([v[1].leader for v in G.items()])

del G
gc.collect()


# Stats
stat = []
pre, N = 0, len(lst)
for i in range(0, N-1):
    if lst[i] != lst[i+1]:
        stat.append( i + 1 - pre )
        pre = i + 1
stat.append( N - pre )
print(sorted(stat, reverse=True)[0:5])
print(zzz)