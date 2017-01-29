#!/bin/python3

import sys

# Read the number of sequences and queries
n, q = map(int, input().strip().split(' '))
sequences = [[] for i in range(n)]
queries = [[] for i in range(q)]
global last_ans
last_ans = 0

# Initialize the queries array
for idx in range(q):
    queries[idx] = [int(an_int) for an_int in input().strip().split(' ')]

def first_query(x, y):
    idx = (x ^ last_ans) % n
    sequences[idx].append(y)

def second_query(x, y):
    global last_ans
    idx = (x ^ last_ans) % n

    sub_idx = y % len(sequences[idx])
    last_ans = sequences[idx][sub_idx]

def second_query_io(x, y):
    second_query(x, y)
    print(last_ans)

def dispatch(query_type, x, y):
    to_call = {
        1: 'first_query',
        2: 'second_query_io'
    }[query_type]
    globals()[to_call](x, y)

[dispatch(*query) for query in queries]
