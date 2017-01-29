#!/bin/python3

import sys

# Read the number of sequences and queries
n = int(input().strip())

# Initialize the string dictionary
str_dict = {}
for idx in range(n):
    the_string = str(input().strip())
    cnt = str_dict.get(the_string, 0)
    str_dict[the_string] = cnt + 1

# Read the number of queries
q = int(input().strip())
for idx in range(q):
    the_query = str(input().strip())
    print(str_dict.get(the_query, 0))
