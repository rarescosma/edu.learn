import sys
import os
import time

inv = 0;

def merge(left, right):
    global inv
    result = []
    i ,j = 0, 0
    bi = len(left)
    bj = len(right)

    for k in range(bi+bj):
        if j >= bj or ( i < bi and left[i] <= right[j] ):
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            j += 1
            inv += (bi - i)

    return result

def mergesort(list):
    l = len(list)
    if l < 2:
        return list
    else:
        middle = l / 2
        left = mergesort(list[:middle])
        right = mergesort(list[middle:])
        return merge(left, right)

t0 = time.clock()

lines = [int(line) for line in open('IntegerArray.txt')]
mergesort(lines)
print 'inversions: ' + str(inv)

print 'time: ' + str(time.clock()-t0)