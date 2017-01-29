#!/bin/python3

import sys

(a_size, num_rot) = map(int, input().strip().split(' '))
arr = list(map(int, input().strip().split(' ')))

print(" ".join(map(str, arr[num_rot:] + arr[:num_rot])))
