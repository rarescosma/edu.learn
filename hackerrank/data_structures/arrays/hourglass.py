#!/bin/python3

import sys
from functools import reduce

a_size = 6
h_size = 3

mask = [1,1,1] + [0] * (a_size - h_size) +\
       [0,1,0] + [0] * (a_size - h_size) +\
       [1,1,1]
mask_len = len(mask)
mask_range = range(mask_len)

# Grab the input in a one-dimensional array
arr = []
for arr_i in range(a_size):
    arr += [int(arr_temp) for arr_temp in input().strip().split(' ')]

# Find all indexes where a hourglass could start
search_indexes = range(len(arr) - mask_len + 1)

# Eliminate indexes that overlap the next row
valid_indexes = list(filter(lambda x: x % a_size <= h_size, search_indexes))

# Select an hourglass and sum it using the bitmask
def sum_hourglass_at_index(idx):
    hourglass = arr[idx:idx + mask_len]
    return reduce(lambda acc, i: acc + hourglass[i] * mask[i], mask_range, 0)

print(max(map(sum_hourglass_at_index, valid_indexes)))
