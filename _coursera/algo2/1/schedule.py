#!/usr/bin/env python

import sys
import operator

# Read the input
jobs = []

with open("jobs.txt") as f:
  next(f)
  for line in f:
    t = [int(i) for i in line.split()]
    jobs.append((t[0], t[1]))


def schedule(jobs, metric):
  return sorted(jobs, key=lambda j: (metric(*j), j[0]), reverse=True)

def differenceMetric(w, l):
  return w - l

def ratioMetric(w, l):
  return w / float(l)

def completionTimes(jobs):
  acc = 0
  l = []
  for job in jobs:
    c = acc + job[1]
    acc += job[1]
    l.append(c)

  return l

def weighedTime(jobs):
  weights = [j[0] for j in jobs]
  completion = completionTimes(jobs)

  return sumProduct(weights, completion)

def sumProduct(*lists):
    return sum(reduce(operator.mul, data) for data in zip(*lists))


print weighedTime(schedule(jobs, differenceMetric))
print weighedTime(schedule(jobs, ratioMetric))
