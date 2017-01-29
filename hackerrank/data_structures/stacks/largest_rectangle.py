#!/bin/python3

import sys

# Number of buildings
n = int(input().strip())

# Read the heights
heights = [int(tmp) for tmp in input().strip().split()]

def max_n(a_list):
    l = len(a_list)
    if 0 == l:
        return 0
    if 1 == l:
        return a_list[0]

    (min_v, min_idx) = find_min(a_list)

    return max(min_v * l, max_n(a_list[:min_idx]), max_n(a_list[min_idx+1:]))

def find_min(a_list):
    min_v = min(a_list)
    return (min_v, a_list.index(min_v))

print(max_n(heights))
