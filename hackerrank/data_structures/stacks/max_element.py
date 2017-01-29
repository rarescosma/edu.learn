#!/bin/python3

import sys

class Stack:
    def __init__(self):
        self.items = []
        self.maxes = []

    def isEmpty(self):
        return self.items == []

    def push(self, item):
        self.items.append(item)
        cur_max = self.getMax()
        self.maxes.append(item if item > cur_max else cur_max)

    def pop(self):
        self.maxes.pop()
        return self.items.pop()

    def getMax(self):
        return self.maxes[-1] if len(self.maxes) else float('-inf')

# Number of queries
n = int(input().strip())

# Read the queries
queries = [[] for i in range(n)]
for idx in range(n):
    queries[idx] = [int(an_int) for an_int in input().strip().split(' ')]

the_stack = Stack()
def dispatch(query_type, *args):
    if 1 == query_type:
        the_stack.push(args[0])
    elif 2 == query_type:
        the_stack.pop()
    elif 3 == query_type:
        print(the_stack.getMax())

[dispatch(*query) for query in queries]
