import sys
import os
import time

from random import randrange
from random import randint
from math import floor


def qsort1(list):
    if list == []:
        return []

    global comp
    l = len(list)
    comp += ( l - 1 )

    # Choose pivot
    p = choosepivot(list,l)
    pivot = list[p]

    # Put it on 0th position
    swap(list,0,p)

    # Partition
    i = 1
    for j in range(1,l):
        if list[j] < pivot:
            swap(list,i,j)
            i+=1

    # Put pivot back
    swap(list,0,i-1)

    # Recurse on first half
    lesser = qsort1(list[:i-1])

    # Recurse on 2nd half
    greater = qsort1(list[i:])

    return lesser + greater


def qsort2(list):
    if list == []:
        return []

    global comp
    l = len(list)
    comp += ( l - 1 )

    pivot = list.pop(choosepivot(list,l))

    lesser = qsort2([l for l in list if l < pivot])
    greater = qsort2([l for l in list if l >= pivot])
    return lesser + [pivot] + greater

def choosepivot(list,lng):
    # Random
    # return randrange(lng)

    # Left
    # return 0

    # Right
    # return lng-1


    if lng <= 3:
        return 0
    # Median of three (random)
    m = randrange(1, lng-2)

    # Median of three (middle)
    # m = int(floor((lng-1)/2))

    c = list[m]

    [s,l] = [0,lng-1] if list[0] < list[lng-1] else [lng-1,0]

    if c > list[l]:
        return l
    elif c < list[s]:
        return s
    else:
        return m

def swap(arr,x,y):
    t,arr[x] = arr[x],arr[y]
    arr[y] = t



t0 = time.clock()

comp = 0
arr = [int(line) for line in open('IntegerArray.txt')]
qsort1(arr)
print 'comparisons: ' + str(comp)
print 'time: ' + str(time.clock()-t0)



t0 = time.clock()

comp = 0
arr = [int(line) for line in open('IntegerArray.txt')]
qsort2(arr)
print 'comparisons: ' + str(comp)
print 'time: ' + str(time.clock()-t0)

t0 = time.clock()

arr = [int(line) for line in open('IntegerArray.txt')]
arr.sort()
print 'time: ' + str(time.clock()-t0)