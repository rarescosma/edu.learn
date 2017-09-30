#!/usr/bin/env python

import sys
import time
import itertools
from heapq import heappush, heappop
from random import sample, choice

values = []
weights = []

with open('knapsack1.txt') as f:
  t = [int(i) for i in next(f).split()]
  W = t[0]
  n = t[1]
  for line in f:
    t = [int(i) for i in line.split()]

    values.append(t[0])
    weights.append(t[1])

m = {}
for w in range(W):
  m[(0, w)] = 0

for j in range(W):
  for i in range(1, n):
    if j >= weights[i]:
      m[(i,j)] = max(m[(i-1, j)], m[i-1, j - weights[i]] + values[i])
    else:
      m[(i,j)] = m[(i-1, j)]

print m[max(m)]
